import { Component, OnInit } from '@angular/core';
import { ManageProductService } from '../Services/manage-product/manage-product.service';

@Component({
  selector: 'app-manager-product',
  templateUrl: './manager-product.component.html',
  styleUrls: ['./manager-product.component.css'],
})
export class ManagerProductComponent implements OnInit {
  constructor(private service: ManageProductService) {}
  public items: any = [];
  public p: any;
  ngOnInit(): void {
    this.loadAllData();
  }
  public loadAllData() {
    this.service.getAll().subscribe(
      (data) => {
        this.items = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
