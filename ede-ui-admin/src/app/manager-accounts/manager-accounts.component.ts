import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { Genders } from '../models/genders.model';
import { User } from '../models/user.model';
import { ManageAccountsService } from '../Services/manage-accounts/manage-accounts.service';

@Component({
  selector: 'app-manager-accounts',
  templateUrl: './manager-accounts.component.html',
  styleUrls: ['./manager-accounts.component.css'],
})
export class ManagerAccountsComponent implements OnInit {
  // filterTerm: string;
  public manageAccount = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.pattern('^[a-zA-Z0-9_-]{6,50}$'),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,50}$'),
    ]),
    first_name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z\\xC0-\\uFFFF]{0,25}[ \\-\\']{0,}){1,25}$"
      ),
    ]),
    address: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{1,}[ \\-\\' \\.-/,]{0,}){5,}$"
      ),
    ]),
    last_name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z\\xC0-\\uFFFF]{0,25}[ \\-\\']{0,}){1,25}$"
      ),
    ]),
    gender: new FormControl(null, Validators.required),
    photo: new FormControl(null),
    email: new FormControl('', [Validators.required, Validators.email]),
    is_delete: new FormControl(false),
    is_active: new FormControl(false),
    otp: new FormControl(null),
    city: new FormControl(null, Validators.required),
    district: new FormControl(null, Validators.required),
    wards: new FormControl(null, Validators.required),
    phone: new FormControl('', [
      Validators.required,
      Validators.pattern('(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b'),
    ]),
    confirmPassword: new FormControl('', Validators.required),
  });
  constructor(
    private apiAddressService: ApiAddressService,
    private manageAccountService: ManageAccountsService //private term: string,
  ) {}
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;
  public loading: boolean = true;
  ngOnInit(): void {
    this.getApiCity();
    this.genders;
    this.loadUser();
  }
  onPasswordChange() {
    if (this.confirm_password.value == this.password.value) {
      this.confirm_password.setErrors(null);
    } else {
      this.confirm_password.setErrors({ mismatch: true });
    }
  }
  //get password
  get password(): AbstractControl {
    return this.manageAccount.controls['password'];
  }
  //get confirm password
  get confirm_password(): AbstractControl {
    return this.manageAccount.controls['confirmPassword'];
  }
  resetForm() {
    window.location.reload();
  }
  // choose city
  chooseCity() {
    this.isHiddenDistrict = false;
    this.isHiddenWards = true;
    this.isHiddenAddress = true;
    this.getApiDistricts(this.manageAccount.controls['city'].value.id);
  }
  // choose district
  chooseDistrict() {
    this.isHiddenWards = false;
    this.getApiWards(this.manageAccount.controls['district'].value.id);
  }

  /**
   * H??m ch???n ?????a ch??? Ph?????ng / x??
   */
  chooseWards() {
    this.isHiddenAddress = false;
  }

  public getApiCity() {
    this.loading = true;
    this.apiAddressService.getApiCity().subscribe((data) => {
      const listCity = data.map(function (obj: {
        id: any;
        code: any;
        name: any;
      }) {
        return obj;
      });
      this.loading = false;
      this.listCitys = listCity;
    });
  }
  public getApiDistricts(id: any) {
    this.loading = true;
    this.apiAddressService.getApiDistricts(id).subscribe((data) => {
      const listDistrict = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.loading = false;
      this.listDistricts = listDistrict;
    });
  }

  public getApiWards(id: any) {
    this.loading = true;
    this.apiAddressService.getApiWards(id).subscribe((data) => {
      const listWard = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.loading = false;
      this.listWards = listWard;
    });
  }

  // l???y d??? li???u t??? FormControl v???
  private createNewData() {
    const newUser: any = {};
    for (const controlName in this.manageAccount.controls) {
      if (controlName) {
        newUser[controlName] = this.manageAccount.controls[controlName].value;
      }
    }
    return newUser as User;
  }
  // th??m m???i user
  public addNewUser() {
    const oldAddress = this.manageAccount.controls['address'].value;
    const newAddress =
      this.manageAccount.controls['address'].value +
      ', ' +
      this.manageAccount.controls['wards'].value.name +
      ', ' +
      this.manageAccount.controls['district'].value.name +
      ', ' +
      this.manageAccount.controls['city'].value.name;
    this.manageAccount.controls['address'].setValue(newAddress);
    this.manageAccountService.addNewUser(this.createNewData()).subscribe(
      (data) => {
        this.manageAccountService.sendEmail(data.email);
        Swal.fire({
          icon: 'success',
          title: '????ng k?? th??nh c??ng!',
          text: 'M???t li??n k???t ???? g???i t???i email c???a b???n, vui l??ng x??c nh???n n?? ????? k??ch ho???t t??i kho???n',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.manageAccount.reset();
          } else {
            this.manageAccount.reset();
          }
        });
      },
      (err) => {
        this.manageAccount.controls['address'].setValue(oldAddress);
        Swal.fire({
          icon: 'error',
          title: 'L???i',
          text: err.error.message,
        });
      }
    );
  }
  genders = [
    new Genders('M', 'M'),
    new Genders('W', 'W'),
    new Genders('D', 'D'),
  ];

  /**
   * Load user + pagination
   * @author Thanh
   */

  public p: number = 1;
  public items: any = [];
  public loadUser() {
    this.manageAccountService.loadUser().subscribe((data) => {
      const item = data.map(function (obj: {
        id: any;
        username: any;
        password: any;
        first_name: any;
        last_name: any;
        email: any;
        photo: any;
        gender: any;
        address: any;
        phone: any;
        is_delete: any;
        is_active: any;
        role: any;
      }) {
        return obj;
      });
      this.items = item;
      console.log(item);
    });
  }

  /**
   * Search
   * @author Thanh
   */

  public Search(temp: string) {
    // console.log("hihi");
    this.manageAccountService.SearchUser(temp).subscribe((data) => {
      const ite = data.map(function (obj: {
        id: any;
        username: any;
        password: any;
        first_name: any;
        last_name: any;
        email: any;
        photo: any;
        gender: any;
        address: any;
        phone: any;
        is_delete: any;
        is_active: any;
        role: any;
      }) {
        return obj;
      });
      this.items = ite;
    });
  }

  /**
   * deleteUser
   * @author Vi???t
   */
  public deleteUser(username: string) {
    this.manageAccountService.deleteUser(username).subscribe(
      () => {
        Swal.fire({
          icon: 'success',
          title: 'X??a t??i kho???n',
          text: 'X??a th??nh c??ng',
        }).then(() => {
          this.loadUser();
        });
      },
      (err) => {
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'L???i',
          text: err.error,
        });
      }
    );
  }

  /**
   * ????a user l??n form
   * @author vinh
   */
  public editUser(user: any) {
    const newUser: any = {};
    for (const controlName in this.manageAccount.controls) {
      if (controlName) {
        this.manageAccount.controls[controlName].setValue(user[controlName]);
      }
      if (user['address']) {
        this.manageAccount.controls['address'].setValue(user['address']);
        this.changeSelectionAddress(user['address']);
        return;
        // let arrAddress = user['address'].split(', ')
        // this.manageAccount.controls['city'].setValue(arrAddress.pop())
        // this.manageAccount.controls['district'].setValue(arrAddress.pop())
        // this.manageAccount.controls['wards'].setValue(arrAddress.pop())
        // this.manageAccount.controls['address'].setValue(arrAddress.join(', '))
      }
    }
    return newUser as User;
  }

  /**
   * C???p nh???t user
   * @author vinh
   */
  public updateUser() {
    const oldAddress = this.manageAccount.controls['address'].value;
    const newAddress =
      this.manageAccount.controls['address'].value +
      ', ' +
      this.manageAccount.controls['wards'].value.name +
      ', ' +
      this.manageAccount.controls['district'].value.name +
      ', ' +
      this.manageAccount.controls['city'].value.name;
    this.manageAccount.controls['address'].setValue(newAddress);
    this.manageAccountService.updateUser(this.createNewData()).subscribe(
      () => {
        this.loadUser();
        Swal.fire({
          icon: 'success',
          title: 'C???p nh???t th??nh c??ng!',
          text: 'D??? li???u c???a b???n ???? thay ?????i',
          confirmButtonText: `OK`,
        }).then(() => {
          this.manageAccount.reset();
        });
      },
      (err) => {
        this.manageAccount.controls['address'].setValue(oldAddress);
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'L???i',
          text: err,
        });
      }
    );
  }

  /**
   * H??m load l??n ?????a t??? c???t t??? address th??nh city , district , wards
   */
  public changeSelectionAddress(address: any) {
    let idCity = '';
    let idDistrict = '';
    let idWard = '';
    let address_split = address.split(',');

    this.manageAccount.controls['address'].setValue(address_split[0]);

    // L???y ra id c???a th??nh ph??? khi user c?? ?????a ch??? th??nh ph??? tr??ng
    setTimeout(() => {
      for (let i = 0; i < this.listCitys.length; i++) {
        if (this.listCitys[i].name.includes(address_split[3].trim())) {
          idCity = this.listCitys[i];
          this.manageAccount.controls['city'].setValue(idCity);
          this.getApiDistricts(this.manageAccount.controls['city'].value.id);
          this.isHiddenDistrict = false;
        }
      }
    }, 1000);

    setTimeout(() => {
      // L???y ra id c???a qu???n khi user c?? ?????a ch??? qu???n tr??ng
      for (let i = 0; i < this.listDistricts.length; i++) {
        if (this.listDistricts[i].name.includes(address_split[2].trim())) {
          idDistrict = this.listDistricts[i];
          this.manageAccount.controls['district'].setValue(idDistrict);
          this.getApiWards(this.manageAccount.controls['district'].value.id);
          this.isHiddenWards = false;
        }
      }
    }, 3000);
    setTimeout(() => {
      // L???y ra id c???a ph?????ng khi user c?? ?????a ch??? ph?????ng tr??ng
      for (let i = 0; i < this.listWards.length; i++) {
        if (this.listWards[i].name.includes(address_split[1].trim())) {
          idWard = this.listWards[i];
          this.manageAccount.controls['wards'].setValue(idWard);
          this.isHiddenAddress = false;
        }
      }
    }, 5000);
  }
}
