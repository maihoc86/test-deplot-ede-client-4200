import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Product } from '../models/product.model';
import { HeaderService } from '../Services/header/header.service';
import { IndexService } from '../Services/index/index.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private headerService: HeaderService,
    private cookieService: CookieService,
    private indexService: IndexService
  ) {}

  public productValue: Product = {} as Product;

  ngOnInit(): void {
    this.loadProductSalling();
  }
  public loadingProductSelling = true;
  public listProductSelling: any = [];
  public count: number = 0;
  public page: number = 0;
  public keySearch: any = '';

  public loadProductSalling() {
    this.loadingProductSelling = true;
    this.route.queryParams.subscribe((params) => {
      this.page = params['page'];
    });
    this.page == undefined ? (this.page = 1) : 0;
    this.indexService.getProductSelling(this.page - 1).subscribe(
      (data) => {
        console.log(data);
        this.listProductSelling = data;
        this.loadingProductSelling = false;
      },
      (err) => {
        this.loadingProductSelling = false;
        console.log(err);
      }
    );
  }

  /**
   * Hàm xem chi tiết sản phẩm từ cửa hàng
   * @param product id product truyền vào
   */
  showDetailProduct(product: any) {
    this.router.navigate([`/product/detail` + '/' + product]);
  }

}
