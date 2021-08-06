import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { CustomerServiceService } from '../Services/register-service';
@Component({
  selector: 'app-register-account',
  templateUrl: './register-account.component.html',
  styleUrls: ['./register-account.component.css'],
})
export class RegisterAccountComponent implements OnInit {
  genders = [
    new Genders('N', 'Nam'),
    new Genders('NU', 'Nữ'),
    new Genders('K', 'Khác'),
  ];
  public register = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9_-]{6,50}$")]),
    password: new FormControl('', [Validators.required, Validators.pattern("^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,50}$")]),
    first_name: new FormControl('', [Validators.required, Validators.pattern("^\\S([a-zA-Z\\xC0-\\uFFFF]{0,}[ \\-\\']{0,}){1,}$")]),
    address: new FormControl('', [Validators.required, Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{0,}[ \\-\\' \\.-]{0,}){1,}$")]),
    last_name: new FormControl('', [Validators.required, Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{0,}[ \\-\\' \\.-]{0,}){1,}$")]),
    gender: new FormControl('', Validators.required),
    photo: new FormControl(null),
    email: new FormControl('', [Validators.required, Validators.email]),
    is_delete: new FormControl(false),
    role: new FormControl('US'),
    otp: new FormControl(null),
    phone: new FormControl('', [Validators.required, Validators.pattern("(84|0[3|5|7|8|9])+([0-9]{8})\\b")]),
    confirmPassword: new FormControl('', Validators.required),
  });
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private customerservice: CustomerServiceService
  ) {}
  get f(): { [key: string]: AbstractControl } {
    return this.register.controls;
  }
  // All is this method
  onPasswordChange() {
    if (this.confirm_password.value == this.password.value) {
      this.confirm_password.setErrors(null);
    } else {
      this.confirm_password.setErrors({ mismatch: true });
    }
  }

  // getting the form control elements
  get password(): AbstractControl {
    return this.register.controls['password'];
  }

  get confirm_password(): AbstractControl {
    return this.register.controls['confirmPassword'];
  }
  ngOnInit(): void {
    this.genders;
    console.log(this.genders);
  }
  private createNewData() {
    this.register.addControl('is_active', new FormControl(false));
    const newUser: any = {};
    for (const controlName in this.register.controls) {
      if (controlName) {
        newUser[controlName] = this.register.controls[controlName].value;
      }
    }
    return newUser as User;
  }
  public registerUser() {
    this.customerservice.addUser(this.createNewData()).subscribe((data) => {
      console.log(data);
      console.log('vào đây');
      this.router.navigate(['register']);
    });
  }
}
class Genders {
  constructor(public gdID: string, public gdName: string) {}
}
