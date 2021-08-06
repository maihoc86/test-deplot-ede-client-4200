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
  public register = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    first_name: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    last_name: new FormControl('', Validators.required),
    gender: new FormControl('', Validators.required),
    photo: new FormControl(null),
    email: new FormControl('', [Validators.required,Validators.email]),
    is_delete: new FormControl(false),
    role: new FormControl('US'),
    otp: new FormControl(null),
    phone: new FormControl('', Validators.required),
  });
  genders = [
    new Genders('N', 'M'),
    new Genders('NU', 'W'),
    new Genders('K', 'D'),
  ];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private customerservice: CustomerServiceService
  ) {}
  get f(): { [key: string]: AbstractControl } {
    return this.register.controls;
  }

  ngOnInit(): void {}
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
