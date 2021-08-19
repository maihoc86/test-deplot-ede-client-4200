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
    private Addservice: AddProductService
  ) { }

  ngOnInit(): void {
    this.listBrands;
    this.listCategories;
    this.getBrands();
    this.getCategories();
  }
  public listCategories: any = [];
  public listBrands: any = [];
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
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm sản phẩm thành công !',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.router.navigate(['login']);
          } else {
            this.router.navigate(['login']);
          }
        })
      },
      (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
  }

  public getBrands() {
    this.Addservice.getBrand().subscribe(
      (data) => {
        const listBrands = data.map(function (obj: { id: any; name: any; avatar: any; }) {
          return obj;
        });
        this.listBrands = listBrands;
        console.log(this.listBrands);
      }
    );
  }
  public getCategories() {
    this.Addservice.getCategories().subscribe((data) => {
      const listCategories = data.map(function (obj: { id: any; name: any; image_url: any; is_enable: boolean; is_deleted: boolean; child_parentCategory: any; }) {
        return obj;
      });
      this.listCategories = listCategories;
      console.log(this.listCategories);
    });
  }
}
