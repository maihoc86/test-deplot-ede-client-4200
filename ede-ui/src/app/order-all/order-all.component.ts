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
  public pAll: number = 1;
  public pDaHuy: number = 1;
  public pDaGiao: number = 1;
  public pDetail: number = 1;
  public itemsAll: any = [];
  public itemsDaGiao: any = [];
  public itemsDaHuy: any = [];
  public itemsDetail: any = [];
  public idDetail: any;
  public count: any;
  public countDetail: any;
  public size: number = 5;
  public status: string = '';

  public keywordAll: string = '';
  public keywordTrue: string = '';
  public keywordFalse: string = '';
  public keywordDetail: string = '';

  constructor(
    private orderShopService: OrderShopService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.loadOrderAll(this.keywordAll, this.status, this.pAll, this.size);
  }
  public loadOrderAll(
    keyword: string,
    status: string,
    page: number,
    size: number
  ) {
    page = page - 1;
    this.orderShopService.getOrderShop(keyword, status, page, size).subscribe(
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
        this.page = data;
        status == 'Đã giao'
          ? (this.itemsDaGiao = item)
          : status == 'Đã hủy'
          ? (this.itemsDaHuy = item)
          : (this.itemsAll = item);
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
    this.showParamsURL(this.pDaHuy, this.size, this.status);
    status === 'Đã hủy'
      ? this.loadOrderAll(
          this.keywordFalse,
          this.status,
          this.pDaHuy,
          this.size
        )
      : status === 'Đã giao'
      ? this.loadOrderAll(
          this.keywordTrue,
          this.status,
          this.pDaGiao,
          this.size
        )
      : this.loadOrderAll(this.keywordAll, this.status, this.pAll, this.size);
  }
  showOrderDetail(id: string) {
    this.idDetail = id;
    this.getOrderDetail(
      this.idDetail,
      this.keywordDetail,
      this.pDetail,
      this.size
    );
    // TODO: show order detail
  }
  public handlePageChange(event: number, status: string) {
    if (status === 'dahuy') {
      this.status = 'Đã hủy';
      this.pDaHuy = event;
      this.showParamsURL(this.pDaHuy, this.size, this.status);
    } else if (status === 'dagiao') {
      this.status = 'Đã giao';
      this.pDaGiao = event;
      this.showParamsURL(this.pDaGiao, this.size, this.status);
    } else {
      this.status = '';
      this.pAll = event;
      this.showParamsURL(this.pAll, this.size, this.status);
    }
    this.loadOrderAll(this.keywordAll, this.status, event, this.size);
  }

  getRequestParams(page: number, pageSize: number, status: string): any {
    let params: any = {};
    if (page) {
      params[`page`] = page;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    if (status != null || status != '') {
      params[`status`] = status;
    }
    return params;
  }

  public showParamsURL(page: number, size: number, status: string) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: this.getRequestParams(page, size, status),
      queryParamsHandling: 'merge', // remove to replace all query params by provided
    });
  }
  public getOrderDetail(
    id: string,
    keyword: string,
    page: number,
    size: number
  ) {
    page = page - 1;
    this.orderShopService
      .getOrderDetailShop(id, keyword, page, size)
      .subscribe((data) => {
        console.log(data);
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
    this.getOrderDetail(
      this.idDetail,
      this.keywordDetail,
      this.pDetail,
      this.size
    );
  }

  public searchAllOrder(keywordAll: string) {
    this.keywordAll = keywordAll;
    this.loadOrderAll(this.keywordAll, this.status, this.pAll, this.size);
  }

  public searchOrderTrue(keywordTrue: string) {
    this.keywordTrue = keywordTrue;
    this.loadOrderAll(this.keywordTrue, this.status, this.pDaGiao, this.size);
  }

  public searchOrderFalse(keywordFalse: string) {
    this.keywordFalse = keywordFalse;
    this.loadOrderAll(this.keywordFalse, this.status, this.pDaHuy, this.size);
  }

  public searchOrderDetail(keywordDetail: string) {
    this.keywordDetail = keywordDetail;
    this.getOrderDetail(
      this.idDetail,
      this.keywordDetail,
      this.pDetail,
      this.size
    );
  }
}
