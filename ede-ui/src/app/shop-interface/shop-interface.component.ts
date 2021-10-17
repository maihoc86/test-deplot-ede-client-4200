import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HeaderService } from '../Services/header/header.service';
import { CookieService } from 'ngx-cookie-service';
import { MyShopService } from '../Services/my-shop/my-shop.service';

@Component({
  selector: 'app-shop-interface',
  templateUrl: './shop-interface.component.html',
  styleUrls: ['./shop-interface.component.css']
})
export class ShopInterfaceComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private router: Router,
    private shopService: MyShopService,
    private headerService: HeaderService, private cookieService: CookieService) { }

  ngOnInit(): void {
    this.loadInterfaceShop(this.getParam());
    this.loadProductNew();
  }
  public toBot() {
    document.getElementById('table')?.scrollIntoView({ behavior: "smooth" });
  }
  public fakeArrayNew5: any = []
  public fakeArrayDiscount5: any = []
  public cart: Array<any> = [];
  public location: any = 'new';
  public count: number = 0;
  public page: number = 0;
  public listProduct: any = [];
  public listNew: any = [];
  public listSelling: any = [];

  public shopInfor: any = {};
  public array: any = [7, 6, 5, 4, 3];
  public getParam() {
    var id = '';
    this.route.params.subscribe((params) => {
      id = params['id'];
    });
    return id;
  }
  loadInterfaceShop(idShop: string) {
    this.shopService.getShopInfo(idShop).subscribe((data) => {
      this.shopInfor = data;
      console.log(data)
      this.load5ProductDiscount(idShop);
      this.load5ProductNew(idShop);
    }, err => {
      this.router.navigate([''])
    });
  }
  public ToAllProductInterfaceShop() {
    document.location.href = '/shop/showall/interface?idShop=' + this.getParam();
  }
  public LoadProductTable() {
    if (this.location == 'discount') {
      this.loadProductSale();
    } else if (this.location == 'Selling') {

    } else if (this.location == 'new') {
      this.loadProductNew();
    }
  }
  public loadProductSale() {
    this.route.queryParams.subscribe((params) => {
      this.page = params['page'];
    })
    this.page == undefined ? this.page = 1 : 0
    this.shopService.getProductSale(this.getParam(), this.page - 1).subscribe(data => {
      this.listProduct = data.content;
      this.count = 0;
      this.count = data.content.length;
    }, err => {
      console.log(err)
    })
  }
  public loadProductNew() {
    this.route.queryParams.subscribe((params) => {
      this.page = params['page'];
    })
    this.page == undefined ? this.page = 1 : 0
    this.shopService.getProductNew(this.getParam(), this.page - 1).subscribe(data => {
      this.listProduct = data.content;
      console.log(data)
      this.count = 0;
      this.count = data.content.length;
    }, err => {
      console.log(err)
    })
  }
  public handlePageChange(event: number) {
    this.page = event;
    this.routeParams();
  }
  routeParams() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: this.getRequestParams(
        this.location,
        this.page,
      ),
      queryParamsHandling: 'merge', // remove to replace all query params by provided
    });
    this.LoadProductTable();
  }
  getRequestParams(
    location: any,
    page: number
  ): any {
    let params: any = {};
    if (page) {
      params[`page`] = page;
    }
    if (page) {
      params[`location`] = location;
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
        discount: product.productDiscount[0]
          ? product.productDiscount[0]?.discount
          : 0,
      });
    }
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart);
  }
  public load5ProductDiscount(idshop: any) {
    this.shopService.get5ProductDiscount(idshop).subscribe(data => {
      this.fakeArrayDiscount5 = data;
    })
  }
  public load5ProductNew(idshop: any) {
    this.shopService.get5ProductNew(idshop).subscribe(data => {
      this.fakeArrayNew5 = data.content;
      console.log(data)
    })
  }
  public ClickProductNew() {
    if (this.location!='new') {
    this.location = 'new';
    this.page = 1;
    this.routeParams();
  }
  }
  public ClickProductDiscount() {
    if (this.location!='discount') {
      this.location = 'discount';
      this.page = 1;
      this.routeParams();
    }
  }
}
