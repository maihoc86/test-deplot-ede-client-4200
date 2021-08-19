import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { Product } from '../models/product.model';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-product-shop',
  templateUrl: './product-shop.component.html',
  styleUrls: ['./product-shop.component.css']
})
export class ProductShopComponent implements OnInit {
  public product = new FormGroup({
    origin: new FormControl(''),
    description: new FormControl(''),
    enable: new FormControl('true'),
    delete: new FormControl('false'),
    brand: new FormControl('', Validators.required),
    child_category: new FormControl('', Validators.required),
  });
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private Addservice: AddProductService,
    private AddresseService: ApiAddressService,
    private cookieService:CookieService
  ) { }

  ngOnInit(): void {
    this.listBrands;
    this.listCategories;
    this.listCountry;
    this.getBrands();
    this.getCategories();
    this.getCountry();
  }
  public listCategories: any = [];
  public listBrands: any = [];
  public listCountry: any = [];
  private createNewData() {
    const newProduct: any = {};
    for (const controlName in this.product.controls) {
      if (controlName) {
        newProduct[controlName] = this.product.controls[controlName].value;
      }
    }
    return newProduct as Product;
  }
  public registerUser() {
    this.product.controls['delete'].setValue('false');
    this.Addservice.addProductShop(this.createNewData()).subscribe(
      (data) => {
        Swal.fire({
          title: 'Thêm sản phẩm thành công !!',
          text: "Bạn có muốn đăng bán sản phẩm luôn không!",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Đăng bán!'
        }).then((result) => {
          if (result.isConfirmed) {
            console.log(data)
            this.Addservice.enableProductShop(data.id).subscribe((data)=>{
              Swal.fire({
                title:  'Thông báo!',
                text: 'Sản phẩm đã được đăng bán',
                icon: 'success'}
              ).then((result)=>{
                console.log(data)
              })
            })
           
          }
        })
      },
      (err) => {
        console.log(err.error)
       if(err.status==404){
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: "Chưa đăng nhập",
        });
        this.router.navigate(['/login'])
       }else{
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
       }
      }
    );
  }
  public getCountry() {
    this.AddresseService.getCountry().subscribe((data) => {
      const listCountry = data.map(function (obj: { id: any; name: any; }) {
        return obj;
      });
      this.listCountry = listCountry;
      console.log(this.listCountry);
    });
  }
  public getBrands() {
    this.Addservice.getBrand().subscribe(
      (data) => {
        const listBrands = data.map(function (obj: { id: any; name: any; avatar: any; }) {
          return obj;
        });
        this.listBrands = listBrands;
      }
    );
  }
  public getCategories() {
    this.Addservice.getCategories().subscribe((data) => {
      const listCategories = data.map(function (obj: { id: any; name: any; image_url: any; is_enable: boolean; is_deleted: boolean; child_parentCategory: any; }) {
        return obj;
      });
      this.listCategories = listCategories;
    });
  }
}
