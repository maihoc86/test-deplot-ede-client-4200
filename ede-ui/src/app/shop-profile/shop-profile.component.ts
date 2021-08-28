import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
import { MyShopService } from '../Services/my-shop/my-shop.service';

@Component({
  selector: 'app-shop-profile',
  templateUrl: './shop-profile.component.html',
  styleUrls: ['./shop-profile.component.css']
})
export class ShopProfileComponent implements OnInit {
  @ViewChild('InputImage', { static: false })
  InputImage!: ElementRef;

  constructor(private headerService: HeaderService, private cookieService: CookieService, private router: Router, private shopService: MyShopService) {
    this.shop = { } as Shop;
  }
  urlImageLoad = "/assets/img/ba.jpg";
  ImageUrl = "";
  onselectFile(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.ImageUrl = e.target.files[0].name;
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlImageLoad = e.target.result;
      }
    }
  }
  getUrl() {
    return "url(" + this.urlImageLoad + ")";
  }
  public formShop = new FormGroup({
    id: new FormControl(''),
    name: new FormControl('',Validators.required),
    image: new FormControl(''),
    address: new FormControl('',Validators.required),
    description: new FormControl('', Validators.required),
    user: new FormControl(''),
    create_date: new FormControl('')
  })
  ngOnInit(): void {
    this.loadProfileShopByUserLogin();
  }
  private createNewDataSop() {
    const newShop: any = { };
    for (const controlName in this.formShop.controls) {
      if (controlName) {
        newShop[controlName] = this.formShop.controls[controlName].value;
      }
    }
    return newShop as Shop;
  }
  updateInfoShop() {
    this.headerService.getShopByToken(this.cookieService.get("auth")).subscribe(data => {
      this.shop = data;
      this.formShop.controls['user'].setValue(data.user);
      this.formShop.controls['image'].setValue(this.ImageUrl);
      this.shopService.updateInfoShop(this.createNewDataSop()).subscribe(data => {
        Swal.fire({
          icon: 'success',
          title: 'Thành công',
          text: 'Cập nhật thành công'
        })
        this.router.navigate(['/shop-profile'])
      })
    })

  }
  public shop: Shop;
  public loadProfileShopByUserLogin() {
    console.log(this.cookieService.get("auth"));
    this.headerService.getShopByToken(this.cookieService.get("auth")).subscribe(data => {
      this.shop = data;
      for (const controlName in this.formShop.controls) {
        for (const node in data) {
          if (controlName && controlName == node) {
            this.formShop.controls[controlName].setValue(data[node]);
          }
        }
      }
    },err=>{

      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: 'Lỗi đăng nhập'
      })
      this.router.navigate(['/login'])
    })

  }
}
