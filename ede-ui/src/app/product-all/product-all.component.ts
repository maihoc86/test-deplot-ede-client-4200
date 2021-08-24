import { Component, OnInit } from '@angular/core';
import { ProductOptions } from '../models/product-options.model';
import { Product } from '../models/product.model';
import { AddProductService } from '../Services/product-shop/add-product.service';

@Component({
  selector: 'app-product-all',
  templateUrl: './product-all.component.html',
  styleUrls: ['./product-all.component.css']
})
export class ProductAllComponent implements OnInit {

  constructor(private productService: AddProductService) { }

  ngOnInit(): void {
    this.loadProductAll();
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

  public listProductOption: any = {};
  // public loadProductAll(){
  //   const newProduct: any = {};
  //   this.productService.getAllOption().toPromise().then(
  //     (data)=> {
  //       console.log(data)
  //       for (const controlName in data) {
  //         if (controlName) {
  //           newProduct[controlName] = data[controlName];
  //         }

  //       }
  //       console.log(newProduct)
  //       this.listProductOption = newProduct;
  //       return newProduct as ProductOptions;

  //       // this.listProductOption = newProduct as ProductOptions;
  //     }

  //   )

  // }
  public items: any = [];
  public itemsEnableTrue: any = [];
  public itemsEnableFalse: any = [];
  public itemsQuantity0: any = [];
  public loadProductAll() {
    this.productService.getAllProductOption().subscribe((data) => {
      const item = data.map(function (obj: {
        id: any;
        id_product: any;
        display_name: any;
        price: any;
        size: any;
        quantity: any;
      }) {
        return obj;
      });
      this.items = item;
      this.itemsEnableTrue = item;
      this.itemsEnableFalse = item;
      this.itemsQuantity0 = item;
    });
  }
}
