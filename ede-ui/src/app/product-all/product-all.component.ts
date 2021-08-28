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

  constructor(private productService: AddProductService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadProductAll(1);
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



  public count: any ;
  public page: any = [];
  public listProductOption: any = {};
  public p: number = 1;
  public items: any = [];
  public itemsEnableTrue: any = [];
  public itemsEnableFalse: any = [];
  public itemsQuantity0: any = [];
  public loadProductAll(page: any) {
    page= page-1;
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
     // this.arrays = [];
      this.count = this.page.totalElements;
      //this.arrays = Array(this.page.totalPages).fill(0).map((x, i) => ({ id: (i + 1), name: `Item ${i + 1}`}));
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


  public handlePageChange(event:number){
    this.p = event;
    this.router.navigate(["/shop/product/all/" + this.p]);
    this.loadProductAll(this.p);
  }

  public countProductPresent(){
   return this.p * 5 ;
  }

  // public ToPage() {
  //   var page = '';
  //   this.route.params.subscribe(params => { console.log(params['page']), page = params['page']; });
  //   this.loadProductAll(page);
  // }

  // public ToPageNext() {
  //   var page: number = 0;
  //   this.route.params.subscribe(params => { console.log(params['page']), page = params['page']; });
  //   page++;
  //   this.router.navigate(["/shop/product/all/" + page]);
  //   this.loadProductAll(page);
  // }

  // public ToPagePrev() {
  //   var page: number = 0;
  //   this.route.params.subscribe(params => { console.log(params['page']), page = params['page']; });
  //   page--;
  //   this.router.navigate(["/shop/product/all/" + page]);
  //   this.loadProductAll(page);
  // }

  // public routerToPage(number: any) {
  //   this.router.navigate(["/shop/product/all/" + number]);
  //   this.ToPage();
  // }




  public editProduct(id: string) {
    //routerLink="[`/shop/product/manager`,e.product.id,'id']"
    this.router.navigate(['shop/product/manager', id]);
  }

  public countOrder: any = "";
  public countProductOder(id: string) {
    this.productService.countProductOrder(id).subscribe((data) => {
      this.countOrder = data;
      console.log("helloooooooooooooooooooooooooo: " + data);
    })

  }
}
