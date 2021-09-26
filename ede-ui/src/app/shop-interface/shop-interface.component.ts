import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-shop-interface',
  templateUrl: './shop-interface.component.html',
  styleUrls: ['./shop-interface.component.css']
})
export class ShopInterfaceComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
 public array: any = [7,6,5,4,3];


}
