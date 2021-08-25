import { Component, OnInit } from '@angular/core';
import { ProductOptions } from '../models/product-options.model';
import { Product } from '../models/product.model';
import { AddProductService} from '../Services/product-shop/add-product.service';

@Component({
  selector: 'app-product-all',
  templateUrl: './product-all.component.html',
  styleUrls: ['./product-all.component.css']
})
export class ProductAllComponent implements OnInit {

  constructor( private productService: AddProductService) { }

  ngOnInit(): void {
   this.loadProductAll();

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
  public p: number = 1;
  public items: any = [];
  public loadProductAll(){
    this.productService.getAllProductOption().subscribe((data)=>{
      const item = data.map(function (obj: {
        id: any;
        id_product: any;
        display_name: any;
        price: any;
        size: any;
        quantity: any;
      }){
        return obj;
      });
      console.log(item)
      this.items = item;
    });
  }



}
