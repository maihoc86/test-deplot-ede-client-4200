import { Component, OnInit } from '@angular/core';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { AddProductService } from '../Services/product-shop/add-product.service';

@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css']
})
export class ShowAllProductsShopInterfaceComponent implements OnInit {
  constructor(private AddresseService: ApiAddressService, private Addservice: AddProductService) { }
  public listCities: any = [];
  public listBrands: any = [];
  public listCategories: any = [];
  hiddenLocation: boolean = true;
  hiddenShowLocationMore: boolean = true;
  ngOnInit(): void {
    this.getCities();
    this.getBrands();
    this.getChildCategory();
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
    console.log("asdasd")
    this.Addservice.getBrand().subscribe(
      (data) => {
        const listBrands = data.map(function (obj: { id: any; name: any; avatar: any; }) {
          return obj;
        });
        this.listBrands = listBrands;
        console.log(this.listBrands);
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
      },error=>{
        console.log(error);
      });
  }
  showHiddenLocation() {
    this.hiddenShowLocationMore = !this.hiddenShowLocationMore;
    this.hiddenLocation = !this.hiddenLocation;
  }
}
