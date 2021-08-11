import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { LoginServiceService } from '../Services/login/login-service.service';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
  })
  constructor(private loginService:LoginServiceService,private cookieService:CookieService) { }

  ngOnInit(): void {
  }
  doLogin(){
      this.loginService.doLogin(this.form.controls['username'].value,this.form.controls['password'].value)
      .toPromise().then(data=>{
        this.cookieService.set('auth',data.token);
        console.log(data)
      }).catch(error=>{
        this.cookieService.set('auth','');
        console.log(error)
      })
  } 
}
