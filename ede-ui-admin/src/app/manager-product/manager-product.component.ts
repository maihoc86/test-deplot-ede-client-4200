import { Component, OnInit } from '@angular/core';
import { ManageProductService } from '../Services/manage-product/manage-product.service';

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
        console.log(data);
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
        console.log(this.items.content.content);
      },
      (err) => {
        console.log(err);
      }
    );
  }
  public deleteById(id: string) {
    this.service.deleteById(id).subscribe(
      (data) => {
        this.loadAllData(this.p, this.size);
      },
      (err) => {
        console.log(err);
      }
    );
  }
  public handlePageChange(event: number) {
    this.p = event;
    this.loadAllData(this.p, this.size);
  }
}
