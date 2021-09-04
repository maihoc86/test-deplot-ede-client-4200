import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { AddProductService } from '../Services/product-shop/add-product.service';

@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css']
})
export class ShowAllProductsShopInterfaceComponent implements OnInit {
  constructor(private AddresseService: ApiAddressService, private router: Router, private ProductService: AddProductService, private route: ActivatedRoute) { }

  public listCities: any = [];
  public listBrands: any = [];
  public listCategories: any = [];
  public listAllProducts: any = [];
  public listAllProductsDiscount: any = [];
  public location: any = [];
  public brand: any = [];
  public category: any;
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
    this.listProduct();
  }
  public listProduct() {
    this.route.queryParams.subscribe(params => {
      this.category = params['category'] ? params['category'] : '';
      this.location = params['location'] ? params['location'].split(',') : [];
      this.brand = params['brand'] ? params['brand'].split(',') : [];
      let page = params['page'];
      if (page != undefined) {
        page = page - 1;
        if (this.category != "" && this.location == "" && this.brand == "") {
          // CATEGORY
          alert(1);
          this.filter(this.category, "", "", page);
        } else if (this.category == "" && this.location != "" && this.brand == "") {
          // LOCATION
          alert(2);
          this.filter("", this.location, "", page);
        } else if (this.brand != "" && this.location == "" && this.category == "") {
          // BRAND
          alert(3);
          this.filter("", "", this.brand, page);
        } else if (this.category != "" && this.location != "") {
          // CATEGORY AND LOCATION
          alert(4);
          this.filter(this.category, this.location, "", page);
        } else if (this.category != "" && this.brand != "") {
          // CATEGORY AND BRAND
          alert(5);
          this.filter(this.category, "", this.brand, page);
        } else if (this.location != "" && this.brand != "") {
          // LOCATION AND BRAND
          alert(6);
          this.filter("", this.location, this.brand, page);
        } else if (this.category != "" && this.location != "" && this.brand != "") {
          // CATEGORY AND LOCATION AND BRAND
          alert(7);
          this.filter(this.category, this.location, this.brand, page);
        } else {
          // DEFAULT
          alert(8);
          this.getAllProductDefault(page);
        }
      } else {
        this.getAllProductDefault(0);
      }

    })
  }
  public getAllProductDefault(page: any) {
    this.ProductService.getAllProductShopByCustomer(page).subscribe(
      (data) => {
        this.listAllProducts = data.content.map(function (obj: { idProduct: any; name: any; }) {
          return obj;
        });
        this.page = data;
        this.count = this.page.totalElements;
      }, error => {
      })
  }
  public filter(category: any, location: any, brand: any, page: any) {
    this.ProductService.getAllProductShowInterfaceFilter(category, location, brand, page).subscribe(
      (data) => {
        console.log(data);
        this.listAllProducts = data.content.map(function (obj: { idProduct: any; name: any; }) {
          return obj;
        });
        this.page = data;
        this.count = this.page.totalElements;
      })
  }
  public getAllDiscountProduct() {
    this.ProductService.getAllProductDiscountShopByCustomer().subscribe(
      (data) => {
        this.listAllProductsDiscount = data
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
    this.category = category;
    this.listProduct();
  }
  clickFilterLocation(event: any, location: any) {
    if (event.currentTarget.checked) {
      this.location.push(location);
    } else {
      this.location.splice(this.location.indexOf(location), 1);
    }
    this.routeParams();
    this.listProduct();
  }
  clickFilterBrand(event: any, brand: any) {
    if (event.currentTarget.checked) {
      this.brand.push(brand);
    } else {
      this.brand.splice(this.brand.indexOf(brand), 1);
    }
    this.routeParams();
    this.listProduct();
  }
  public handlePageChange(event: number) {
    this.p = event;
    this.routeParams();
  }
  routeParams() {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: this.getRequestParams(this.category, this.location, this.brand, this.p),
        queryParamsHandling: 'merge', // remove to replace all query params by provided
      })
  }
  getRequestParams(category: string, location: [] = [], brand: [] = [], page: number): any {
    let params: any = {};
    if (category) {
      params[`category`] = category;
    }
    if (page) {
      params[`page`] = page;
    }
    if (location != undefined && location.length > 0) {
      params[`location`] = location.toString();
    }
    if (brand != undefined && brand.length > 0) {
      params[`brand`] = brand.toString();
    }
    return params;
  }
}
