import { Component, OnInit } from '@angular/core';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../models/product.model';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css']
})
export class ShowAllProductsShopInterfaceComponent implements OnInit {

  constructor(
    private productService: AddProductService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.showAllProductShop(1);
  }

  public page: any = [];
  public items: any = [];
  public count: any;
  public showAllProductShop(page: any) {
    page= page-1;
    this.productService.getAllProductShowInterface(page).subscribe((data) => {
      console.log("showAllProductShop: "+data)
      const item = data.content.map(function (obj: { 
        id: any;
        id_product: any;
        display_name: any;
        price: any;
        size: any;
        quantity: any;
      }
      ) {
        return obj;
      }
      );
      console.log(item)
      this.items = item;
      this.page = data;
      this.count = this.page.totalElements;

    },
      (err) => {
        console.log("Chưa đăng nhập "+err.error)
        if (err.status == 404) {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: "Chưa đăng nhập",
          });
          this.router.navigate(['/login'])
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: err.error.message,
          });
        }
      });

    }


}
