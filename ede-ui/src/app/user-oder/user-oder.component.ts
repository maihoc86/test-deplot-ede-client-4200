import { CookieService } from 'ngx-cookie-service';
import { HeaderService } from './../Services/header/header.service';
import { OrderShopService } from './../Services/order-shop/order-shop.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-oder',
  templateUrl: './user-oder.component.html',
  styleUrls: ['./user-oder.component.css'],
})
export class UserOderComponent implements OnInit {
  constructor(
    private OrderShopService: OrderShopService,
    private headerService: HeaderService,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.getAllOrderUser();
  }

  public login: boolean = false;
  public userAcc: any;
  public listIdShopinOrder: any = [];
  public listOrder: Array<any> = [];
  public listOrderFilter: Array<any> = [];
  public listIdShop: any = [];

  public getAllOrderUser() {
    this.headerService
      .getUserByToken(this.cookieService.get('auth'))
      .toPromise()
      .then((data: any) => {
        this.OrderShopService.getAllOrderUser(data.id).subscribe((data) => {
          this.listOrder = data;
          console.log(data);
          this.loadOrder();
        });
      })
      .catch((err) => {
        console.log(err);
        this.login = false;
      });
  }

  loadOrder() {
    const term: any = [];
    console.log(this.listOrder);
    this.listOrder.forEach((e) => {
      console.log('loadOrder' + e.order_detail.id);
      if (term.indexOf(e.order_detail.productOption.product.shop.id) === -1) {
        term.push(e.order_detail.productOption.product.shop.id);
        this.listIdShopinOrder.push({
          shop: e.order_detail.productOption.product.shop,
        });
      }
    });
    console.log(this.listIdShopinOrder);
  }

  filterOrder(value: any) {
    this.getAllOrderUser();
    this.listOrderFilter = this.listOrder = this.listOrder.filter((e) => {
      // this.listIdShop.push({
      //   shop: e.order_detail[0].productOption.product.shop,
      // });
      return e.status === value;
    });
    console.log(this.listOrderFilter);
  }
}
