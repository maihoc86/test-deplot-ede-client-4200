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
  styleUrls: ['./manager-accounts.component.css']
})
export class ManagerAccountsComponent implements OnInit {



  // filterTerm: string;



  public manageAccount = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.pattern('^[a-z0-9_-]{6,50}$'),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern('^(?=.*d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,50}$'),
    ]),
    first_name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){1,25}$"),
    ]),
    address: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{1,}[ \\-\\' \\.-/,]{0,}){5,}$"
      ),
    ]),
    last_name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){1,25}$"),
    ]),
    gender: new FormControl('', Validators.required),
    photo: new FormControl(null),
    email: new FormControl('', [Validators.required, Validators.email]),
    is_delete: new FormControl(false),
    is_active: new FormControl(false),
    otp: new FormControl(null),
    city: new FormControl(''),
    district: new FormControl(''),
    wards: new FormControl(''),
    phone: new FormControl('', [
      Validators.required,
      Validators.pattern('(84|0[3|5|7|8|9])+([0-9]{8})\\b'),
    ]),
    confirmPassword: new FormControl('', Validators.required),
  });
  constructor(private router: Router,
    private route: ActivatedRoute,
    private apiAddressService: ApiAddressService,
    private manageAccountService: ManageAccountsService,
   //private term: string,
    ) {
  }
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  ngOnInit(): void {
    this.getApiCity();
    this.listCitys;
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
  // get city
  getDistricts() {
    this.getApiDistricts(this.manageAccount.controls["city"].value.id);
  }
  // get wards
  getWards() {
    this.getApiWards(this.manageAccount.controls["district"].value.id);
  }
  public getApiCity() {
    this.apiAddressService.getApiCity().subscribe(
      (data) => {
        const listCity = data.map(function (obj: { id: any; code: any; name: any; }) {
          return obj;
        });
        this.listCitys = listCity;
      }
    );
  }
  public getApiDistricts(id: any) {
    this.apiAddressService.getApiDistricts(id).subscribe((data) => {
      const listDistrict = data.map(function (obj: { id: any; name: any; }) {
        return obj;
      });
      this.listDistricts = listDistrict;
    });
  }

  public getApiWards(id: any) {
    this.apiAddressService.getApiWards(id).subscribe((data) => {
      const listWard = data.map(function (obj: { id: any; name: any; }) {
        return obj;
      });
      this.listWards = listWard;
    });
  }

  // lấy dữ liệu từ FormControl về
  private createNewData() {
    const newUser: any = {};
    for (const controlName in this.manageAccount.controls) {
      if (controlName) {
        newUser[controlName] = this.manageAccount.controls[controlName].value;
      }
    }
    return newUser as User;
  }
  // thêm mới user
  public addNewUser() {
    const oldAddress = this.manageAccount.controls['address'].value;
    const newAddress = (this.manageAccount.controls['address'].value + ", " + this.manageAccount.controls['wards'].value.name + ', ' + this.manageAccount.controls['district'].value.name + ', ' + this.manageAccount.controls['city'].value.name);
    this.manageAccount.controls['address'].setValue(newAddress);
    this.manageAccountService.addNewUser(this.createNewData()).subscribe(
      (data) => {
        this.manageAccountService.sendEmail(data.email);
        Swal.fire({
          icon: 'success',
          title: 'Đăng ký thành công!',
          text: 'Một liên kết đã gửi tới email của bạn, vui lòng xác nhận nó để kích hoạt tài khoản',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.manageAccount.reset()
          } else {
            this.manageAccount.reset()
          }
        })
      },
      (err) => {
        this.manageAccount.controls['address'].setValue(oldAddress);
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
  }
  genders = [
    new Genders('N', 'M'),
    new Genders('NU', 'W'),
    new Genders('K', 'D'),
  ];




public items: any = [];
public loadUser() {
  this.manageAccountService.loadUser().subscribe((data) => {
    const item = data.map(function (obj: {
      id: any; username: any;
      password: any; first_name: any;
      last_name: any; email: any;
      photo: any; gender: any;
      address: any; phone: any;
      is_delete: any; is_active: any;
      role: any


    }) {
      return obj;
    });
    this.items = item;
  });
}

public term:string ="";

public deleteUser(username:string){
 
  this.manageAccountService.deleteUser(username).subscribe(data=>{
    Swal.fire({
      icon:'success',
      title:'Xóa tài khoản',
      text: 'Xóa thành công'
    }).then(respone=>{
      const item = respone;
      this.items.forEach((any:any,index:number) => {
       console.log(index)
       delete this.items[index]
      }); 
    })

  } ,(err) => {
    console.log(err)
    Swal.fire({
      icon: 'error',
      title: 'Lỗi',
      text: err.error,
    });
  })
}
}


