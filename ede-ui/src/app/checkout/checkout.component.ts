import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnChanges, SimpleChanges, Input } from '@angular/core';
import { OrderShopService } from '../Services/order-shop/order-shop.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnChanges {

  @Input()
  public productList:any = []
  public totalprice:number = 0

  constructor(
    private orderShopService: OrderShopService,
    private router: Router,
    private route: ActivatedRoute
  ){
    this.productList = JSON.parse("" + localStorage.getItem("cart")) || []
    this.totalprice = this.productList.reduce((last:number, item:any) => (last + (item?.product_option?.price * item?.quantity)),0) 
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.totalprice = this.productList.reduce((last:number, item:any) => (last + (item?.product_option?.price * item?.quantity)),0) 
  }

}
