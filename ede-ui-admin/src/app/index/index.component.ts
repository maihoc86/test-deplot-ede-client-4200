import { Component, OnInit } from '@angular/core';
import { StatisticalService } from '../Services/statistical/statistical.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
  constructor(private statistical: StatisticalService) {}

  public totalViewPageNew: number = 0;
  public totalViewPage: number = 0;
  public totalUserNew: number = 0;
  public totalUsers: number = 0;
  public totalProductNew: number = 0;
  public totalProduct: number = 0;
  public totalShopNew: number = 0;
  public totalShop: number = 0;
  public totalProductSellMonth: number = 0;
  public totalProductSell: number = 0;
  ngOnInit(): void {
    this.selectViewPageNew();
    this.selectViewPage();

    this.selecttotalUserNew();
    this.selecttotalUsers();

    this.selectTotalProduct();
    this.selectTotalProductNew();

    this.selectTotalShop();
    this.selectTotalShopNew();

    this.selectSellProductMonth();
    this.selectSellProduct();
  }

  selectViewPageNew() {
    this.statistical.selectTotalViewPageNew().subscribe(
      (data: any) => {
        this.totalViewPageNew = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectViewPage() {
    this.statistical.selectTotalViewPage().subscribe(
      (data: any) => {
        this.totalViewPage = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selecttotalUserNew() {
    this.statistical.selectTotalUserNew().subscribe(
      (data: any) => {
        this.totalUserNew = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }
  selecttotalUsers() {
    this.statistical.selectTotalUsers().subscribe(
      (data: any) => {
        this.totalUsers = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectTotalProduct() {
    this.statistical.selectTotalProduct().subscribe(
      (data: any) => {
        this.totalProduct = data.length;
        console.log(this.totalProduct);
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectTotalProductNew() {
    this.statistical.selectTotalProductNew().subscribe(
      (data: any) => {
        this.totalProductNew = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectTotalShop() {
    this.statistical.selectTotalShop().subscribe(
      (data: any) => {
        this.totalShop = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectTotalShopNew() {
    this.statistical.selectTotalShopNew().subscribe(
      (data: any) => {
        this.totalShopNew = data.length;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectSellProductMonth() {
    this.statistical.selectSellProductCurrentMonth().subscribe(
      (data: any) => {
        data.forEach((element: any) => {
          this.totalProductSellMonth += element.quantity;
        });
        console.log(this.totalProductSellMonth);
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }

  selectSellProduct() {
    this.statistical.selectSellProduct().subscribe(
      (data: any) => {
        data.forEach((element: any) => {
          this.totalProductSell += element.quantity;
        });
        console.log(this.totalProductSell);
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: error.error.errors[0].defaultMessage,
          text: error.error.message,
        });
      }
    );
  }
}
