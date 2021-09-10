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
  constructor(
    private orderShopService: OrderShopService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.loadOrderAll(this.status, this.pAll, this.size);
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
        this.page = data;
        status == 'Đã giao'
          ? (this.itemsDaGiao = item)
          : status == ''
          ? (this.itemsAll = item)
          : (this.itemsDaHuy = item);
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
    status === 'Đã hủy'
      ? this.loadOrderAll(this.status, this.pDaHuy, this.size)
      : status === 'Đã giao'
      ? this.loadOrderAll(this.status, this.pDaGiao, this.size)
      : this.loadOrderAll(this.status, this.pAll, this.size);
  }
  showOrderDetail(id: string) {
    this.idDetail = id;
    this.getOrderDetail(this.idDetail, this.pDetail, this.size);
    // TODO: show order detail
  }
  public handlePageChange(event: number, status: string) {
    if (status === 'dahuy') {
      this.showParamsURL(this.pDaHuy, this.size);
      this.pDaHuy = event;
    } else if (status === 'dagiao') {
      this.showParamsURL(this.pDaGiao, this.size);
      this.pDaGiao = event;
    } else {
      this.showParamsURL(this.pAll, this.size);
      this.pAll = event;
    }
    console.log(event);
    this.loadOrderAll(this.status, event, this.size);
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
    this.getOrderDetail(this.idDetail, this.pDetail, this.size);
  }
}
