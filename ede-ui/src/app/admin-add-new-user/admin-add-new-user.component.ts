import { Component, OnInit } from '@angular/core';
import { RegisterAccountComponent } from '../register-account/register-account.component';
import Swal from 'sweetalert2';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { RegisterService } from '../Services/register-service';
import { ApiAddressService } from '../Services/api-address.service';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-admin-add-new-user',
  templateUrl: './admin-add-new-user.component.html',
  styleUrls: ['./admin-add-new-user.component.css']
})
export class ADMINAddNewUserComponent implements OnInit {
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
  constructor(private router: Router,
    private route: ActivatedRoute,
    private registerService: RegisterService,
    private apiAddressService: ApiAddressService,) {
  }
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  ngOnInit(): void {
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

}
