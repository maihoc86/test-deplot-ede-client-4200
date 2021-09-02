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
    this.loadProductAll(1,5);
  }
  filterEnableFalse() {
    this.loadProductEnable(false, 1);
  }
  filterEnableTrue() {
    this.loadProductEnable(true, 1);
  }
  filterQuantity0() {
    this.loadProductQty0(1);
  }

  public size: number = 5;
  public count: any;
  public countEnableTrue: any;
  public countEnableFalse: any;
  public countQty0: any;
  public page: any = [];
  public pageEnableTrue: any=[];
  public pageEnableFalse: any=[];
  public pageQty0: any=[];
  public listProductOption: any = { };
  public p: number = 1;
  public pEnableTrue: number = 1;
  public pEnableFalse: number = 1;
  public pQty0: number = 1;
  public items: any = [];
  public itemsEnableTrue: any = [];
  public itemsEnableFalse: any = [];
  public itemsQuantity0: any = [];

  public loadProductAll(page: any, size: any) {
    page= page-1;
    this.productService.getAllProductOption(page, size).subscribe((data) => {
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
        console.log("Chưa đăng nhập "+err.error)
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

  public loadProductQty0(page: any) {
    page = page - 1;
    this.productService.getAllProductByQty0(page).subscribe((data) => {
      this.itemsQuantity0 = data.content.map(function (obj: {
        id: any;
        id_product: any;
        display_name: any;
        price: any;
        size: any;
        quantity: any;
      }) {
        return obj;
      });
      this.pageQty0 = data;
      this.countQty0 = this.pageQty0.totalElements;
      console.log(this.countQty0);
    },
      (err) => {
        if (err.status == 404) {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: "Chưa đăng nhập",
          });
          this.router.navigate(['/login'])
        } else {
          console.log(err)
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: err.message,
          });
        }
      });

  }
  public loadProductEnable(value: boolean, page: any) {
    page = page - 1;
    this.productService.getAllProductByEnable(value, page).subscribe((data) => {
      if (value) {
        this.itemsEnableTrue = data.content.map(function (obj: {
          id: any;
          id_product: any;
          display_name: any;
          price: any;
          size: any;
          quantity: any;
        }) {
          return obj;
        });
        this.pageEnableTrue = data;
        console.log(this.pageEnableTrue);
        this.countEnableTrue = this.pageEnableTrue.totalElements;
        console.log(this.countEnableTrue);
      } else {
        this.itemsEnableFalse = data.content.map(function (obj: {
          id: any;
          id_product: any;
          display_name: any;
          price: any;
          size: any;
          quantity: any;
        }) {
          return obj;
        });
        this.pageEnableFalse = data;
        this.countEnableFalse = this.pageEnableFalse.totalElements;
        console.log(this.countEnableFalse);
      }
    }, error => {
      console.log(error);
      if (error.status == 404) {
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
          text: error.error.message,
        });
      }
    })
  }
  
  public handlePageChange(event:number){
    this.p = event;
    //this.router.navigate(["/shop/product/all?page=" + this.p+"&size="+this]);
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: this.getRequestParams("",this.p,this.size),
        queryParamsHandling: 'merge', // remove to replace all query params by provided
      });
      console.log("handlePageChange nè")
    this.loadProductAll(this.p,this.size);
  }

  public handlePageChangeEnableTrue(event: number) {
    console.log(event);
    this.pEnableTrue = event;
    this.loadProductEnable(true, this.pEnableTrue);
  }
  public handlePageChangeEnableFalse(event: number) {
    this.pEnableFalse = event;
    this.loadProductEnable(false, this.pEnableFalse);
  }
  public handlePageChangeQty0(event: number) {
    this.pQty0 = event;
    this.loadProductQty0(this.pQty0);
  }

  getRequestParams(searchKeyword: string, page: number, pageSize: number): any {
    let params: any = {};

    if (searchKeyword) {
      params[`keyword`] = searchKeyword;
    }

    if (page) {
      params[`page`] = page;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  public changeSize(event: any){
    this.p = 1;
    this.size = event.target.value;
    console.log("size nè: "+event.target.value);
    this.loadProductAll(this.p, this.size);

  }

  public editProduct(id: string) {
    //routerLink="[`/shop/product/manager`,e.product.id,'id']"
    this.router.navigate(['shop/product/manager', id]);
  }

  public countOrder: any = "";
  public countProductOder(id: string) {
    this.productService.countProductOrder(id).subscribe((data) => {
      this.countOrder = data;
    })

  }
}
