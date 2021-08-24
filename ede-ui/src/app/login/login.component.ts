import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Router, ActivatedRoute } from '@angular/router';
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
  constructor(private loginService:LoginServiceService,private cookieService:CookieService, private router: Router) { }

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

  public doLogin2(){
    this.loginService.doLogin(this.form.controls['username'].value,this.form.controls['password'].value)
    .subscribe(data=>{
      console.log(data);
        Swal.fire({
          icon: 'success',
          title: 'Thông báo',
          text: 'Đăng nhập thành công!',
          confirmButtonText: `OK`,
        }).then((result) => {
          this.cookieService.set('auth',data.token);
          document.location.href='';
        })
    },
    (err) => {
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: "Sai thông tin đăng nhập",
      });
    })

  }
}
