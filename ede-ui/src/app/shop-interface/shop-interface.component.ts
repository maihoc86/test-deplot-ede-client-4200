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
    private shopService:MyShopService,
    private headerService: HeaderService,private cookieService:CookieService) { }

  ngOnInit(): void {
    this.loadInterfaceShop(this.getParam());
    this.loadProductSale();
  }
  public toBot(){
    document.getElementById('table')?.scrollIntoView({behavior:"smooth"});
  }
  public fakeArrayDiscount5: any = []
  public cart: Array<any> = [];
  public load:any='sale';
  public count: number = 0;
  public page:number=0;
  public listProduct:any=[];
  public listNew:any=[];
  public listSelling:any=[];

  public shopInfor: any = {};
  public array: any = [7, 6, 5, 4, 3];
  public getParam() {
    var id = '';
    this.route.params.subscribe((params) => {
      id = params['id'];
    });
    return id;
  }
  loadInterfaceShop(idShop:string) {
    this.shopService.getShopInfo(idShop).subscribe((data) => {
      this.shopInfor=data;
      console.log(data)
      this.load5ProductDiscount(idShop);
    },err=>{
      this.router.navigate([''])
    });
  }
  public ToAllProductInterfaceShop(){
    document.location.href='/shop/showall/interface?idShop='+this.getParam();
  }
  public LoadProductTable(){

    if(this.load=='sale'){
      this.loadProductSale();
    }else if(this.load=='Selling'){

    }else if(this.load=='new'){
      
    }
  }
  public loadProductSale(){
    this.route.queryParams.subscribe((params) => {
    this.page = params['page'];
    })
      this.page==undefined?this.page=1:0
      this.shopService.getProductSale(this.getParam(),this.page-1).subscribe(data=>{
        this.listProduct=data.content;
        console.log(data)
        this.count=0;
        this.count=data.content.length;
      },err=>{
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
        this.page
      ),
      queryParamsHandling: 'merge', // remove to replace all query params by provided
    });
    this.loadProductSale();
  }
  getRequestParams(
    page: number
  ): any {
    let params: any = {};
    if (page) {
      params[`page`] = page;
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
  public load5ProductDiscount(idshop:any){
    this.shopService.get5ProductDiscount(idshop).subscribe(data=>{
      this.fakeArrayDiscount5=data;
    })
  }
}
