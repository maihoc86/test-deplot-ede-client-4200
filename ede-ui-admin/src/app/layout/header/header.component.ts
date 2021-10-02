import { Component, OnInit } from '@angular/core';
import { ManageAccountsService } from '../../Services/manage-accounts/manage-accounts.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [
    './header.component.css',
  ]
})
export class HeaderComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private manageAccountService: ManageAccountsService) {  }
  public userLogin:any="";
  ngOnInit(): void {
    this.getUserLogin()
  }
  public async getUserLogin() {
    await this.manageAccountService
      .getUserByToken()
      .toPromise()
      .then((data) => {
        this.userLogin=data.id;
      })
      .catch((err) => {
        console.log(err);
        this.erros(err);
      });
  }
  erros(err:any){
    if(err.status==401){
      // Swal.fire({
      //   icon: 'error',
      //   title: 'Lỗi xác thực đăng nhập',
      //   text: err.error.message,
      // }).then(()=>{
      //   document.location.href='http://localhost:4200/login'
      // });
      document.location.href='http://localhost:4200/login'
     }else if(err.status==403){
      // Swal.fire({
      //   icon: 'error',
      //   title: 'Không có quyền admin',
      //   text: err.error.message,
      // }).then(()=>{
      //   document.location.href='http://localhost:4200'
      // });
      document.location.href='http://localhost:4200'
     }else{
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: err.error.message,
      });
     }
  }
}
