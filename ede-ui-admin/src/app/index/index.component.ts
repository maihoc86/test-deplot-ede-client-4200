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

  public totalUserNew: number = 0;
  public totalUsers: number = 0;
  public totalProductNew: number = 0;
  public totalProduct: number = 0;
  public totalShopNew: number = 0;
  public totalShop: number = 0;
  public totalProductSellMonth: number = 0;

  ngOnInit(): void {
    this.selecttotalUserNew();
    this.selecttotalUsers();

    this.selectTotalProduct();
    this.selectTotalProductNew();

    this.selectTotalShop();
    this.selectTotalShopNew();
  }

  selectViewPage() {}

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
        this.totalProduct = data.content.length;
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

  selectSellProductMonth() {}
}
