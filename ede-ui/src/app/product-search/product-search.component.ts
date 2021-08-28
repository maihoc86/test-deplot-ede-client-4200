import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductSearchService } from '../Services/product-search/product-search.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent {

  constructor(
    private productSearchSvc: ProductSearchService,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (JSON.stringify(this.params) != JSON.stringify(params)) {
        this.params = params
        this.init()
      }
    })
  }

  public productSearchPage: any

  public params = {}

  init(): void {
    this.productSearchSvc.getProductByKeySearch(this.params).subscribe(
      response => {
        this.productSearchPage = response
      },
      error => {
        console.log(error)
      }
    )
  }

}
