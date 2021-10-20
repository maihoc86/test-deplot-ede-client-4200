import { Component, OnInit } from '@angular/core';
import { HeaderService } from 'src/app/Services/header/header.service';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor( private headerService: HeaderService) { this.headerService.myMethod$.subscribe((data) => {
    this.cart = data;
  }); }
  public cart: Array<any> = [];
  ngOnInit(): void {
    this.loadCart()
  }
  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];
    
  }
}
