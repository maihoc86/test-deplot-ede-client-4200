import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { HeaderService } from '../Services/header/header.service';
import { CookieService } from 'ngx-cookie-service';
import Swal from 'sweetalert2';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { Shop } from '../models/shop.model';

@Component({
  selector: 'app-shop-profile',
  templateUrl: './shop-profile.component.html',
  styleUrls: ['./shop-profile.component.css']
})
export class ShopProfileComponent implements OnInit {
  public formShop = new FormGroup({
    id: new FormControl(''),
    name: new FormControl(''),
    image :new FormControl(''),
    address: new FormControl(''),
    description: new FormControl(''),
    create_date: new FormControl('')
  })
  constructor(private headerService:HeaderService,private cookieService:CookieService,private router:Router) {
    this.shop={} as Shop;
   }

  ngOnInit(): void {
    this.loadProfileShopByUserLogin();
  }
  
  public shop:Shop;
  public loadProfileShopByUserLogin(){
    this.headerService.getShopByToken(this.cookieService.get("auth")).subscribe(data=>{
      this.shop=data;
      for(const controlName in this.formShop.controls){
        for(const node in data){
          if(controlName && controlName == node){
            this.formShop.controls[controlName].setValue(data[node]);
          }
        }
      }
    },err=>{
      Swal.fire({
        icon:'error',
        title:'Lỗi',
        text:'Lỗi đăng nhập'
      })
   this.router.navigate(['/login'])
    })
      
  }
}
