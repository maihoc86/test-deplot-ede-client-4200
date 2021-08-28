import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product.model';
import { ProductSearchService } from '../Services/product-search/product-search.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  constructor() { }

  public productValue: Product = {} as Product;

  ngOnInit(): void {
  }

}
