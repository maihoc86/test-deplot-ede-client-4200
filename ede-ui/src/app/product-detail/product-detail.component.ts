import { ProductSearchService } from './../Services/product-search/product-search.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  public HOST_ORIGIN = "http://localhost:8080"

  constructor(
    private activatedRoute: ActivatedRoute,
    private productSearchSrv: ProductSearchService,
  ) { 
    this.activatedRoute.params.subscribe(({idProduct}) => {
      this.productSearchSrv.getProductSearchById(idProduct).subscribe(result => {
        this.product = result
        console.log(result)
      })
    })
  }

  public product: any = {}

  ngOnInit(): void {
    
  }

}
