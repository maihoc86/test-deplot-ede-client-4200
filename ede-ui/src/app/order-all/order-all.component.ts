import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  NavigationEnd,
  NavigationError,
  NavigationStart,
  Router,
  Event,
} from '@angular/router';
import { OrderShopService } from '../Services/order-shop/order-shop.service';
import Swal from 'sweetalert2';
import { dateInputsHaveChanged } from '@angular/material/datepicker/datepicker-input-base';
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
  public pChoXacNhan: number = 1;
  public pDangGiao: number = 1;

  public pDetail: number = 1;
  public itemsAll: any = [];
  public staticItemsAll: any = [];
  public itemsDaGiao: any = [];
  public itemsDaHuy: any = [];
  public itemsChoXacNhan: any = [];
  public itemsDangGiao: any = [];
  public itemsDetail: any = [];
  public idDetail: any;
  public count: any;
  public countitemsDaHuy: any = 0;
  public countitemsDangGiao: any = 0;
  public countitemsChoXacNhan: any = 0;
  public total: any = 0;
  public total7Day: any = 0;
  public totalMonth: any = 0;
  public toMonth: any = 1;
  public countDetail: any;
  public size: number = 5;
  public status: string = '';

  public keywordAll: string = '';
  public keywordTrue: string = '';
  public keywordFalse: string = '';
  public keywordDetail: string = '';
  public activeTab = 'all';
  constructor(
    private orderShopService: OrderShopService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationStart) {
        // Show loading indicator
      }
      if (event instanceof NavigationEnd) {
        this.route.queryParams.subscribe((params) => {
          let getSize = params['size'];
          let getPage = params['page'];
          let getKeyword = params['keyword'];
          let getStatus = params['status'];
          if (getKeyword !== undefined) {
            this.keywordAll = getKeyword;
          }
          if (getPage !== undefined) {
            this.pAll = getPage;
          }
          if (getSize !== undefined) {
            this.size = getSize;
          }
          if (getStatus !== undefined && getStatus !== '') {
            this.status = getStatus;
            this.activeTab = getStatus;
          } else {
            this.status = '';
            this.activeTab = 'all';
          }
        });
      }

      if (event instanceof NavigationError) {
        // Hide loading indicator

        // Present error to user
        console.log(event.error);
      }
    });
  }
  ngOnInit(): void {
    this.getAllOrder();
    this.loadOrderAll(this.keywordAll, this.status, this.pAll, this.size);
  }
  public getAllOrder() {
    this.orderShopService.getAllOrderShop().subscribe((data) => {
      this.staticItemsAll = data;

      this.staticItemsAll.forEach((e: any) => {
        var dateE2: Date = new Date(e.create_date);
        e.status == 'Đã hủy'
          ? this.countitemsDaHuy++
          : ((this.total += e.total_amount),
            dateE2.getMonth() == new Date().getMonth()
              ? (this.totalMonth += e.total_amount)
              : '');

        var dateE: any = new Date();

        if (
          Date.parse(e.create_date) -
            (Date.parse(dateE) - 1000 * 60 * 60 * 24 * 7) >=
          0
        ) {
          this.total7Day += e.total_amount;
        }
      });
    });
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
          : status == 'Chờ xác nhận'
          ? (this.itemsChoXacNhan = item)
          : status == 'Đang giao'
          ? (this.itemsDangGiao = item)
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
          console.log(err);
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
      : status == 'Chờ xác nhận'
      ? this.loadOrderAll(
          this.keywordTrue,
          this.status,
          this.pChoXacNhan,
          this.size
        )
      : status == 'Đang giao'
      ? this.loadOrderAll(
          this.keywordTrue,
          this.status,
          this.pDangGiao,
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
    } else if (status === 'choxacnhan') {
      this.status = 'Chờ xác nhận';
      this.pChoXacNhan = event;
      this.showParamsURL(this.pChoXacNhan, this.size, this.status);
    } else if (status === 'danggiao') {
      this.status = '';
      this.pDangGiao = event;
      this.showParamsURL(this.pDangGiao, this.size, this.status);
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
