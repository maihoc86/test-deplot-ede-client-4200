import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor() { }
  public cart: Array<any> = [];
  ngOnInit(): void {
    this.loadCart()
  }
  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];
    
  }
}
