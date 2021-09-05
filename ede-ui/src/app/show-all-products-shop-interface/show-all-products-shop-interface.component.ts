import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { HeaderService  } from '../Services/header/header.service';
@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css']
})

export class ShowAllProductsShopInterfaceComponent implements OnInit {

  constructor(private AddresseService: ApiAddressService, private router: Router,
     private ProductService: AddProductService, private route: ActivatedRoute
     ,private headerService:HeaderService
 ) { }
  public totalCart:any=0;
  public cart: Array<any> = [];
  public listCities: any = [];
  public listBrands: any = [];
  public listCategories: any = [];
  public listAllProducts: any = [];
  public listAllProductsDiscount: any = [];
  public page: any = [];
  public p: number = 1;
  public count: any;
  hiddenLocation: boolean = true;
  hiddenShowLocationMore: boolean = true;
  ngOnInit(): void {
    this.getCities();
    this.getBrands();
    this.getChildCategory();
    this.getAllDiscountProduct();
    this.getAllProduct(1);
  }
  public getAllProduct(page: any) {
    page = page - 1;
    this.ProductService.getAllProductShopByCustomer(page).subscribe(
      (data) => {
        this.listAllProducts = data.content.map(function (obj: { idProduct: any; name: any; }) {
          return obj;
        });
        console.log(this.listAllProducts);
        this.page = data;
        this.count = this.page.totalElements;
      }, error => {
      })
  }
  public getAllDiscountProduct() {
    this.ProductService.getAllProductDiscountShopByCustomer().subscribe(
      (data) => {
        this.listAllProductsDiscount = data
        console.log(this.listAllProductsDiscount[0].productdiscount.id);
      }, error => {
        console.log(error);
      })
  }
  public getCities() {
    this.AddresseService.getApiCity().subscribe((data) => {
      const listCities = data.map(function (obj: { name: any; }) {
        return obj;
      });
      this.listCities = listCities;
    });
  }
  public getBrands() {
    this.ProductService.getBrand().subscribe(
      (data) => {
        const listBrands = data.map(function (obj: { id: any; name: any; avatar: any; }) {
          return obj;
        });
        this.listBrands = listBrands;
      }
    );
  }
  public getChildCategory() {
    this.ProductService.getChildCategoriesShop().subscribe(
      (data) => {
        const listCategories = data.map(function (obj: { id: any; name: any; }) {
          return obj;
        });
        this.listCategories = listCategories;

      }, error => {
        console.log(error);
      });
  }
  showHiddenLocation() {
    this.hiddenShowLocationMore = !this.hiddenShowLocationMore;
    this.hiddenLocation = !this.hiddenLocation;

  }
  clickFilterCategory(category: any) {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: this.getRequestParams("", category, 1),
        queryParamsHandling: 'merge', // remove to replace all query params by provided
      });
    this.filterProductByCategory(category, 1);
  }
  // LỌC SÀN PHẨM THEO LOẠI DANH MỤC CÓ TRÊN CỬA HÀNG
  public filterProductByCategory(category: any, page: any) {
    page = page - 1;
    this.ProductService.getAllProductShowInterfaceFilterByCategory(category, page).subscribe(
      (data) => {
        console.log(data);
        this.listAllProducts = data.content.map(function (obj: { idProduct: any; name: any; }) {
          return obj;
        });
        console.log(this.listAllProducts);
        this.page = data;
        this.count = this.page.totalElements;
      })
  }
  public handlePageChange(event: number) {
    this.p = event;
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: this.getRequestParams("", "", this.p),
        queryParamsHandling: 'merge', // remove to replace all query params by provided
      });
    this.getAllProduct(this.p);
  }
  getRequestParams(searchKeyword: string, category: string, page: number): any {
    let params: any = {};

    if (category) {
      params[`category`] = category;
    }
    if (searchKeyword) {
      params[`keyword`] = searchKeyword;
    }

    if (page) {
      params[`page`] = page;
    }
    return params;
  }

  addToCart(product: any) {
    var json = localStorage.getItem('cart');
    this.cart = json ? JSON.parse(json) : [];
    var item: any;
    this.cart.forEach(e => {
      if (e.id == product.optionDef.id) {
        item = e;
      }
    })
    if (item) {
      item.qty++;
    } else {
      this.cart.push({ 'qty': 1,'name': product.name, 'id': product.optionDef.id, 'price': product.optionDef.price })
    }
    console.log(this.cart)
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart)
  }
}
