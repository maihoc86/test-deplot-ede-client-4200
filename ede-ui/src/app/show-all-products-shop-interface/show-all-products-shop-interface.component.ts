import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { HeaderService } from '../Services/header/header.service';
@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css'],
})
export class ShowAllProductsShopInterfaceComponent implements OnInit {
  constructor(
    private AddressService: ApiAddressService,
    private router: Router,
    private ProductService: AddProductService,
    private route: ActivatedRoute,
    private headerService: HeaderService
  ) {}
  public totalCart: any = 0;
  public cart: Array<any> = [];
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
    this.route.queryParams.subscribe((params) => {
      this.category = params['category'] ? params['category'] : '';
      this.location = params['location'] ? params['location'].split(',') : [];
      this.brand = params['brand'] ? params['brand'].split(',') : [];
      this.page = params['page'];
      if (this.page != undefined) {
        this.page = this.page - 1;
        if (this.category != '' && this.location == '' && this.brand == '') {
          // CATEGORY
          this.filter(this.category, '', '', this.page);
        } else if (
          this.category == '' &&
          this.location != '' &&
          this.brand == ''
        ) {
          // LOCATION
          this.filter('', this.location, '', this.page);
        } else if (
          this.brand != '' &&
          this.location == '' &&
          this.category == ''
        ) {
          // BRAND
          this.filter('', '', this.brand, this.page);
        } else if (
          this.category != '' &&
          this.location != '' &&
          this.brand == ''
        ) {
          // CATEGORY AND LOCATION
          this.filter(this.category, this.location, '', this.page);
        } else if (
          this.category != '' &&
          this.brand != '' &&
          this.location == ''
        ) {
          // CATEGORY AND BRAND
          this.filter(this.category, '', this.brand, this.page);
        } else if (
          this.location != '' &&
          this.brand != '' &&
          this.category == ''
        ) {
          // LOCATION AND BRAND
          this.filter('', this.location, this.brand, this.page);
        } else if (
          this.category != '' &&
          this.location != '' &&
          this.brand != ''
        ) {
          // CATEGORY AND LOCATION AND BRAND
          this.filter(this.category, this.location, this.brand, this.page);
        } else {
          // DEFAULT IF PRESENT PAGE
          this.getAllProductDefault(this.page);
        }
      } else {
        // DEFAULT IF NO PRESENT PAGE
        this.getAllProductDefault(0);
      }
    });
  }
  public getAllProductDefault(page: any) {
    this.ProductService.getAllProductShopByCustomer(page).subscribe(
      (data) => {
        this.listAllProducts = data.content.map(function (obj: {
          idProduct: any;
          name: any;
        }) {
          return obj;
        });
        console.log(this.listAllProducts);
        this.page = data;
        this.count = this.page.totalElements;
      },
      (error) => {}
    );
  }
  public filter(category: any, location: any, brand: any, page: any) {
    this.ProductService.getAllProductShowInterfaceFilter(
      category,
      location,
      brand,
      page
    ).subscribe((data) => {
      this.listAllProducts = data.content.map(function (obj: {
        idProduct: any;
        name: any;
      }) {
        return obj;
      });
      this.page = data;
      this.count = this.page.totalElements;
    });
  }
  public getAllDiscountProduct() {
    this.ProductService.getAllProductDiscountShopByCustomer().subscribe(
      (data) => {
        this.listAllProductsDiscount = data;
        console.log(this.listAllProductsDiscount);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  public getCities() {
    this.AddressService.getApiCity().subscribe((data) => {
      const listCities = data.map(function (obj: { name: any }) {
        return obj;
      });
      this.listCities = listCities;
    });
  }
  public getBrands() {
    this.ProductService.getBrand().subscribe((data) => {
      const listBrands = data.map(function (obj: {
        id: any;
        name: any;
        avatar: any;
      }) {
        return obj;
      });
      this.listBrands = listBrands;
    });
  }
  public getChildCategory() {
    this.ProductService.getChildCategoriesShop().subscribe(
      (data) => {
        const listCategories = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listCategories = listCategories;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  showHiddenLocation() {
    this.hiddenShowLocationMore = !this.hiddenShowLocationMore;
    this.hiddenLocation = !this.hiddenLocation;
  }
  clickFilterCategory(category: any) {
    this.category = category;
    this.routeParams();
  }
  clickFilterLocation(event: any, location: any) {
    console.log(location);
    if (event.currentTarget.checked) {
      this.location.push(location);
    } else {
      this.location.splice(this.location.indexOf(location), 1);
      if (this.location.length == 0) {
        this.location = [];
      }
      console.log(this.location);
    }
    this.routeParams();
  }
  clickFilterBrand(event: any, brand: any) {
    if (event.currentTarget.checked) {
      this.brand.push(brand);
    } else {
      this.brand.splice(this.brand.indexOf(brand), 1);
    }
    this.routeParams();
  }
  public handlePageChange(event: number) {
    this.p = event;
    this.routeParams();
  }
  routeParams() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: this.getRequestParams(
        this.category,
        this.location,
        this.brand,
        this.p
      ),
      queryParamsHandling: 'merge', // remove to replace all query params by provided
    });
    this.listProduct();
  }
  getRequestParams(
    category: string,
    location: [] = [],
    brand: [] = [],
    page: number
  ): any {
    let params: any = {};
    if (category) {
      params[`category`] = category;
    }
    if (page) {
      params[`page`] = page;
    }
    if (location != undefined && location.length > 0) {
      params[`location`] = location.toString();
    } else {
      params[`location`] = undefined;
    }
    if (brand != undefined && brand.length > 0) {
      params[`brand`] = brand.toString();
    } else {
      params[`brand`] = undefined;
    }
    return params;
  }

  addToCart(product: any) {
    var json = localStorage.getItem('cart');
    this.cart = json ? JSON.parse(json) : [];
    var item: any;
    this.cart.forEach((e) => {
      if (e.id == product.optionDef.id) {
        item = e;
      }
    });
    if (item) {
      item.qty++;
    } else {
      this.cart.push({
        qty: 1,
        name: product.name,
        id: product.optionDef.id,
        price: product.optionDef.price,
      });
    }
    console.log(this.cart);
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart);
  }
}
