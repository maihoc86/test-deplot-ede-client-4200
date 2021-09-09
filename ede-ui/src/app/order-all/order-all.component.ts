import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderShopService } from '../Services/order-shop/order-shop.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-order-all',
  templateUrl: './order-all.component.html',
  styleUrls: ['./order-all.component.css'],
})
export class OrderAllComponent implements OnInit {
  public page: any = [];
  public pageDetail: any = [];
  public p: number = 1;
  public pDetail: number = 1;
  public items: any = [];
  public itemsDetail: any = [];
  public idDetail: any;
  public count: any;
  public countDetail: any;
  public size: number = 5;
  public status: string = '';
  constructor(
    private orderShopService: OrderShopService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.loadOrderAll(this.status, this.p, this.size);
  }

  public loadOrderAll(status: string, page: number, size: number) {
    page = page - 1;
    this.orderShopService.getOrderShop(status, page, size).subscribe(
      (data) => {
        const item = data.content.map(function (obj: {
          id: string;
          phone: string;
          status: string;
          create_date: Date;
          discount_code: string;
          total_amount: number;
          note: string;
          user: string;
        }) {
          return obj;
        });
        console.log(data);
        this.page = data;
        this.items = item;
        this.count = this.page.totalElements;
      },
      (err) => {
        if (err.status == 401) {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Chưa đăng nhập',
          });
          this.router.navigate(['/login']);
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: err.error.message,
          });
        }
      }
    );
  }
  filterOrderByStatus(status: string) {
    this.status = status;
    this.loadOrderAll(this.status, this.p, this.size);
  }
  showOrderDetail(id: string) {
    this.idDetail = id;
    this.getOrderDetail(this.idDetail, this.pDetail, this.size);
    // TODO: show order detail
  }
  public handlePageChange(event: number) {
    this.showParamsURL(this.p, this.size);
    this.p = event;
    this.loadOrderAll(this.status, this.p, this.size);
  }

  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};
    if (page) {
      params[`page`] = page;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  public showParamsURL(page: number, size: number) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: this.getRequestParams(page, size),
      queryParamsHandling: 'merge', // remove to replace all query params by provided
    });
  }
  public getOrderDetail(id: string, page: number, size: number) {
    page = page - 1;
    this.orderShopService
      .getOrderDetailShop(id, page, size)
      .subscribe((data) => {
        const item = data.content.map(function (obj: {
          id: string;
          productOption: string;
          price: number;
          quantity: number;
          order: string;
        }) {
          return obj;
        });
        console.log(data);
        this.pageDetail = data;
        this.itemsDetail = item;
        this.countDetail = this.pageDetail.totalElements;
      });
  }
  public handlePageChangeDetail(event: number) {
    this.pDetail = event;
    this.getOrderDetail(this.idDetail, this.pDetail, this.size);
  }
}
