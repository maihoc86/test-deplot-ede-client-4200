import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { RegisterService } from '../Services/register-service';
import { ApiAddressService } from '../Services/api-address.service';

@Component({
  selector: 'app-register-account',
  templateUrl: './register-account.component.html',
  styleUrls: ['./register-account.component.css'],
})
export class RegisterAccountComponent implements OnInit {
  public register = new FormGroup({
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
      Validators.pattern("^\\S([a-zA-Z\\xC0-\\uFFFF]{0,}[ \\-\\']{0,}){2,50}$"),
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
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{0,}[ \\-\\' \\.-]{0,}){2,50}$"
      ),
    ]),
    gender: new FormControl('', Validators.required),
    photo: new FormControl(null),
    email: new FormControl('', [Validators.required, Validators.email]),
    is_delete: new FormControl(false),
    is_active: new FormControl(false),
    role: new FormControl('US'),
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
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private registerService: RegisterService,
    private apiAddressService: ApiAddressService,
  ) { }
  get f(): { [key: string]: AbstractControl } {
    return this.register.controls;
  }
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  // All is this method
  onPasswordChange() {
    if (this.confirm_password.value == this.password.value) {
      this.confirm_password.setErrors(null);
    } else {
      this.confirm_password.setErrors({ mismatch: true });
    }
  }
  // get city
  getDistricts() {
    this.getApiDistricts(this.register.controls["city"].value.id);
  }
  // get wards
  getWards() {
    this.getApiWards(this.register.controls["district"].value.id);
  }
  //get password
  get password(): AbstractControl {
    return this.register.controls['password'];
  }
  //get confirm password
  get confirm_password(): AbstractControl {
    return this.register.controls['confirmPassword'];
  }
  ngOnInit(): void {
    this.getApiCity();
    this.listCitys;
    this.genders;
  }
  private createNewData() {
    const newUser: any = {};
    for (const controlName in this.register.controls) {
      if (controlName) {
        newUser[controlName] = this.register.controls[controlName].value;
      }
    }
    return newUser as User;
  }
  public registerUser() {
    const oldAddress = this.register.controls['address'].value;
    const newAddress = (this.register.controls['address'].value + ", " + this.register.controls['wards'].value.name + ', ' + this.register.controls['district'].value.name + ', ' + this.register.controls['city'].value.name);
    this.register.controls['address'].setValue(newAddress);
    this.registerService.addUser(this.createNewData()).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Đăng ký thành công!',
          text: 'Nhấp Ok để hoàn thành!',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.router.navigate(['login']);
          } else {
            this.router.navigate(['login']);
          }
        })
      },
      (err) => {
        this.register.controls['address'].setValue(oldAddress);
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
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
  genders = [
    new Genders('N', 'M'),
    new Genders('NU', 'W'),
    new Genders('K', 'D'),
  ];

}
class Genders {
  constructor(public gdID: string, public gdName: string) { }
}
