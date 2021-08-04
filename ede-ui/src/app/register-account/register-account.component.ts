import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { CustomerServiceService } from '../Services/customer-service.service';
@Component({
  selector: 'app-register-account',
  templateUrl: './register-account.component.html',
  styleUrls: ['./register-account.component.css'],
})

export class RegisterAccountComponent implements OnInit {
  public register = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    firstname: new FormControl(''),
    address: new FormControl(''),
    lastname: new FormControl(''),
    gender: new FormControl(''),
    email: new FormControl(''),
    is_deleted: new FormControl(false),
    role: new FormControl('USER'),
    otp: new FormControl(''),
    phone: new FormControl(''),
  });
   
  genders = [
    new Genders('N', 'Nam'),
    new Genders('NU', 'Nữ'),
    new Genders('K', 'Khác'),
  ];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private customerservice: CustomerServiceService
  ) {}

  ngOnInit(): void {}

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