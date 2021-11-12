import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { AddressUserService } from '../Services/address-user/address-user.service';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { HeaderService } from '../Services/header/header.service';
import { UserAddress } from '../models/user-address.model';
import Swal from 'sweetalert2';
import { C } from '@angular/cdk/keycodes';
@Component({
  selector: 'app-user-address',
  templateUrl: './user-address.component.html',
  styleUrls: ['./user-address.component.css'],
})
export class UserAddressComponent implements OnInit {
  constructor(
    private headerService: HeaderService,
    private address_user: AddressUserService,
    private cookieService: CookieService,
    private apiAddressService: ApiAddressService
  ) {}

  ngOnInit(): void {
    this.getUserLogin();
    this.getAllAddressUser();
    this.getApiCity();
  }
  public address = new FormGroup({
    first_name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z\\xC0-\\uFFFF]{0,25}[ \\-\\']{0,}){1,25}$"
      ),
    ]),
    last_name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z\\xC0-\\uFFFF]{0,25}[ \\-\\']{0,}){1,25}$"
      ),
    ]),
    user: new FormControl(''),
    phone: new FormControl('', [
      Validators.required,
      Validators.pattern('(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b'),
    ]),
    city: new FormControl(null, Validators.required),
    district: new FormControl(null, Validators.required),
    wards: new FormControl(null, Validators.required),
    address: new FormControl(null, [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{1,}[ \\-\\' \\.-/,]{0,}){5,}$"
      ),
    ]),
  });
  public listAddressUser: any = {};
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;

  public clearAddresModal() {
    this.address.reset();
  }

  public getUserLogin() {
    this.headerService
      .getUserByToken(this.cookieService.get('auth'))
      .toPromise()
      .then((data) => {
        this.address.controls['user'].setValue(data);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  private createDataAddress() {
    const newAddress: any = {};
    for (const controlName in this.address.controls) {
      if (controlName) {
        newAddress[controlName] = this.address.controls[controlName].value;
      }
    }
    return newAddress as UserAddress;
  }

  public getAllAddressUser() {
    this.address_user.getAllAdressByUser().subscribe(
      (data) => {
        this.listAddressUser = data;
        console.log(this.listAddressUser);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  /**
   * Hàm lấy ra tất cả các thành phố
   * @returns {obj} danh sách thành phố
   */
  public getApiCity() {
    this.apiAddressService.getApiCity().subscribe((data) => {
      const listCity = data.map(function (obj: {
        id: any;
        code: any;
        name: any;
      }) {
        return obj;
      });
      this.listCitys = listCity;
    });
  }

  /**
   * Hàm lấy ra tất cả các quận theo id của thành phố
   * @param {string} id id của thành phố
   * @returns {obj} danh sách quận
   */
  public getApiDistricts(id: any) {
    this.apiAddressService.getApiDistricts(id).subscribe((data) => {
      const listDistrict = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      console.log(this.address.controls['district'].value);
      this.listDistricts = listDistrict;
    });
  }

  /**
   * Hàm lấy ra tất cả các phường theo id của quận
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiWards(id: any) {
    this.apiAddressService.getApiWards(id).subscribe((data) => {
      const listWard = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.listWards = listWard;
    });
  }

  chooseCity() {
    this.isHiddenDistrict = false;
    this.getApiDistricts(this.address.controls['city'].value);
  }

  chooseDistrict() {
    this.isHiddenWards = false;
    this.getApiWards(this.address.controls['district'].value);
  }

  chooseWards() {
    this.isHiddenAddress = false;
  }

  public async updateAddressUser() {
    await this.headerService
      .getUserByToken(this.cookieService.get('auth'))
      .toPromise()
      .then((data) => {
        this.address.controls['user'].setValue(data);
        window.location.reload();
      })
      .catch((err) => {
        console.log(err);
      });

    this.address_user.addAddress(this.createDataAddress()).subscribe(
      (data) => {
        this.getAllAddressUser();
        Swal.fire({
          title: 'Thông báo!',
          text: 'Thành công !',
          icon: 'success',
        });
      },
      (error) => {
        Swal.fire({
          title: 'Thông báo!',
          text: error.error.errors[0].defaultMessage,
          icon: 'error',
        });
      }
    );
  }

  public showAddressUserModal(user: any, address_user: any) {
    if (user) {
      this.address.controls['first_name'].setValue(user.first_name);
      this.address.controls['last_name'].setValue(user.last_name);
      this.address.controls['phone'].setValue(user.phone);
      this.changeSelectionAddress(user.address);
    } else if (address_user) {
      this.address.controls['first_name'].setValue(address_user.first_name);
      this.address.controls['last_name'].setValue(address_user.last_name);
      this.address.controls['phone'].setValue(address_user.phone);
      this.changeSelectionAddress(address_user.address);
    }
  }
  public deleteAddress(id: any) {
    this.address_user.deleteAddress(id).subscribe(
      (data) => {
        this.getAllAddressUser();
      },
      (error) => {
        Swal.fire({
          title: 'Thông báo!',
          text: error.error.errors[0].defaultMessage,
          icon: 'error',
        });
      }
    );
  }

  public changeSelectionAddress(address: any) {
    let idCity = '';
    let idDistrict = '';
    let idWard = '';
    let address_split = address.split(',');

    this.address.controls['address'].setValue(address_split[0]);

    // Lấy ra id của thành phố khi user có địa chỉ thành phố trùng
    setTimeout(() => {
      for (let i = 0; i < this.listCitys.length; i++) {
        if (this.listCitys[i].name.includes(address_split[3].trim())) {
          idCity = this.listCitys[i].id;
          this.address.controls['city'].setValue(idCity);
          this.getApiDistricts(idCity);
          this.isHiddenDistrict = false;
        }
      }
    }, 1000);

    setTimeout(() => {
      // Lấy ra id của quận khi user có địa chỉ quận trùng
      for (let i = 0; i < this.listDistricts.length; i++) {
        if (this.listDistricts[i].name.includes(address_split[2].trim())) {
          idDistrict = this.listDistricts[i].id;
          this.address.controls['district'].setValue(idDistrict);
          this.getApiWards(this.address.controls['district'].value);
          this.isHiddenWards = false;
        }
      }
    }, 2500);
    setTimeout(() => {
      // Lấy ra id của phường khi user có địa chỉ phường trùng
      for (let i = 0; i < this.listWards.length; i++) {
        if (this.listWards[i].name.includes(address_split[1].trim())) {
          idWard = this.listWards[i].id;
          this.address.controls['wards'].setValue(idWard);
          this.isHiddenAddress = false;
        }
      }
    }, 4500);
  }
}
