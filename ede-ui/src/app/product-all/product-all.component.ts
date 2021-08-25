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
  public p: number = 1;
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
      console.log(item)
      this.items = item;
      this.itemsEnableTrue = item;
      this.itemsEnableFalse = item;
      this.itemsQuantity0 = item;
    });
  }

  public countOrder: any = "";
  public countProductOder(id:string){
    this.productService.countProductOrder(id).subscribe((data) =>{


      this.countOrder = data;
      console.log("helloooooooooooooooooooooooooo: "+data);
    })

  }
}
