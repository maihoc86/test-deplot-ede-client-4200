import { Component, OnInit } from '@angular/core';
import { ProductOptions } from '../models/product-options.model';
import { Product } from '../models/product.model';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-product-all',
  templateUrl: './product-all.component.html',
  styleUrls: ['./product-all.component.css']
})
export class ProductAllComponent implements OnInit {

  constructor(private productService: AddProductService, private router:Router) { }

  ngOnInit(): void {
    this.loadProductAll(0);
  }
  filterEnableFalse() {
    this.itemsEnableFalse = this.itemsEnableFalse.filter(function (obj: {
      id: any;
      product: any;
      display_name: any;
      price: any;
      size: any;
      quantity: any;
    }) {
      return obj.product.enable == false;
    });
    console.log(this.items)
  }
  filterEnableTrue() {
    this.itemsEnableTrue = this.itemsEnableTrue.filter(function (obj: {
      id: any;
      product: any;
      display_name: any;
      price: any;
      size: any;
      quantity: any;
    }) {
      return obj.product.enable == true;
    });
    console.log(this.items)
  }
  filterQuantity0() {
    this.itemsQuantity0 = this.itemsQuantity0.filter(function (obj: {
      id: any;
      id_product: any;
      display_name: any;
      price: any;
      size: any;
      quantity: any;
    }) {
      return obj.quantity == 0;
    });
  }



  public arrays: Array<number> =[] ;
  public page: any =[];
  public listProductOption: any = {};
  public p: number = 1;
  public items: any = [];
  public itemsEnableTrue: any = [];
  public itemsEnableFalse: any = [];
  public itemsQuantity0: any = [];
  public loadProductAll(page: any) {
    this.productService.getAllProductOption(page).subscribe((data) => {
      console.log(data)
    const item = data.content.map(function (obj: {
        id: any;
        id_product: any;
        display_name: any;
        price: any;
        size: any;
        quantity: any;
      }
      ) {
        return obj;
      }
      );

      console.log(item)
      this.items = item;
      this.page = data;
      for(let i = 0; i< this.page.totalPages; i++){
          this.arrays.push(i);
          console.log("page nè Thanh: "+this.arrays);
      }

      this.itemsEnableTrue = item;
      this.itemsEnableFalse = item;
      this.itemsQuantity0 = item;

    },
    (err) => {
      console.log(err.error)
      if (err.status == 404) {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: "Chưa đăng nhập",
        });
        this.router.navigate(['/login'])
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    });
  }





  public editProduct(id:string){
    //routerLink="[`/shop/product/manager`,e.product.id,'id']"
    this.router.navigate(['shop/product/manager', id]);
  }

  public countOrder: any = "";
  public countProductOder(id:string){
    this.productService.countProductOrder(id).subscribe((data) =>{
    this.countOrder = data;
      console.log("helloooooooooooooooooooooooooo: "+data);
    })

  }
}
