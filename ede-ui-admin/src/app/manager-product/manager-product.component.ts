import { Component, OnInit } from '@angular/core';
import { ManageProductService } from '../Services/manage-product/manage-product.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-manager-product',
  templateUrl: './manager-product.component.html',
  styleUrls: ['./manager-product.component.css'],
})
export class ManagerProductComponent implements OnInit {
  constructor(private service: ManageProductService) {}
  public p: number = 1;
  public size: number = 5;
  public count: any;
  public items: any = [];
  public page: any = [];
  ngOnInit(): void {
    this.loadAllData(this.p, this.size);
  }
  public loadAllData(page: any, size: any) {
    page = page - 1;
    this.service.getAll(page, size).subscribe(
      (data) => {
        const item = data.content.map(function (obj: {
          idProduct: any;
          name: any;
          description: any;
          enable: any;
          deleted: any;
          keysearch: any;
          location: any;
        }) {
          return obj;
        });
        this.items = item;
        // this.arrays = [];
        this.page = data;
        this.count = this.page.totalElements;
      },
      (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
  }
  public deleteById(id: string) {
    this.service.deleteById(id).subscribe(
      (data) => {
        this.loadAllData(this.p, this.size);
      },
      (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
  }
  public handlePageChange(event: number) {
    this.p = event;
    this.loadAllData(this.p, this.size);
  }
}
