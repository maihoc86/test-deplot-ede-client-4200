import { Component, OnInit } from '@angular/core';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { AddProductService } from '../Services/product-shop/add-product.service';

@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css']
})
export class ShowAllProductsShopInterfaceComponent implements OnInit {
  constructor(private AddresseService: ApiAddressService, private Addservice: AddProductService, private ProductService: AddProductService) { }
  public listCities: any = [];
  public listBrands: any = [];
  public listCategories: any = [];
  public listAllProducts: any = [];
  public page: any = [];
  public p: number = 1;
  public count: any;
  hiddenLocation: boolean = true;
  hiddenShowLocationMore: boolean = true;
  ngOnInit(): void {
    this.getCities();
    this.getBrands();
    this.getChildCategory();
    this.getAllProduct(1);
  }
  public getAllProduct(page: any) {
    page = page - 1;
    this.ProductService.getAllProductShopByCustomer(page).subscribe(
      (data) => {
        this.listAllProducts = data.content.map(function (obj: { id: any; name: any; }) {
          return obj;
        });
        this.page = data;
        this.count = this.page.totalElements;
        console.log(data.content);
        // this.listAllProducts = listAllProducts;
      }, error => {
      })
  }
  public getCities() {
    this.AddresseService.getApiCity().subscribe((data) => {
      const listCities = data.map(function (obj: { name: any; }) {
        return obj;
      });
      this.listCities = listCities;
    });
  }
  public getBrands() {
    this.Addservice.getBrand().subscribe(
      (data) => {
        const listBrands = data.map(function (obj: { id: any; name: any; avatar: any; }) {
          return obj;
        });
        this.listBrands = listBrands;
      }
    );
  }
  public getChildCategory() {
    this.Addservice.getChildCategoriesShop().subscribe(
      (data) => {
        console.log("Vào đây ch")
        const listCategories = data.map(function (obj: { id: any; name: any; }) {
          return obj;
        });
        this.listCategories = listCategories;
      }, error => {
        console.log(error);
      });
  }
  showHiddenLocation() {
    this.hiddenShowLocationMore = !this.hiddenShowLocationMore;
    this.hiddenLocation = !this.hiddenLocation;
  }
  public handlePageChange(event: number) {
    this.p = event;
    this.getAllProduct(this.p);
  }
}
