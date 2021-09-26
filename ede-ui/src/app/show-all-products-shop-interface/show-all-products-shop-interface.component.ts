import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { HeaderService } from '../Services/header/header.service';
import Swal from 'sweetalert2';
import { ProductDiscount } from '../models/product-discount.model';
@Component({
  selector: 'app-show-all-products-shop-interface',
  templateUrl: './show-all-products-shop-interface.component.html',
  styleUrls: ['./show-all-products-shop-interface.component.css'],
})
export class ShowAllProductsShopInterfaceComponent implements OnInit {
  constructor(
    private AddressService: ApiAddressService,
    private router: Router,
    private ProductService: AddProductService,
    private route: ActivatedRoute,
    private headerService: HeaderService
  ) {}
  public totalCart: any = 0;
  public cart: Array<any> = [];
  public listCities: any = [];
  public listBrands: any = [];
  public listCategories: any = [];
  public listAllProducts: any = [];
  public listAllProductsDiscount: any = [];
  public location: any = [];
  public brand: any = [];
  public category: any;
  public page: any = [];
  public p: number = 1;
  public count: any;
  public hiddenLocation: boolean = true;
  public hiddenShowLocationMore: boolean = true;
  ngOnInit(): void {
    this.getCities();
    this.getBrands();
    this.getChildCategory();
    this.getAllDiscountProduct();
    this.listProduct();
  }

  /**
   * Hàm thực hiện load sản phẩm theo param trên url
   * @param page trang số
   * @param category loại sản phẩm
   * @param location địa chỉ sản phẩm []
   * @param brand nhãn hàng []
   */
  public listProduct() {
    this.route.queryParams.subscribe((params) => {
      this.category = params['category'] ? params['category'] : '';
      this.location = params['location'] ? params['location'].split(',') : [];
      this.brand = params['brand'] ? params['brand'].split(',') : [];
      this.page = params['page'];
      // ! FIX ME
      if (this.page != undefined) {
        this.page = this.page - 1;
        if (this.category != '' && this.location == '' && this.brand == '') {
          // CATEGORY
          this.filter(this.category, '', '', this.page);
        } else if (
          this.category == '' &&
          this.location != '' &&
          this.brand == ''
        ) {
          // LOCATION
          this.filter('', this.location, '', this.page);
        } else if (
          this.brand != '' &&
          this.location == '' &&
          this.category == ''
        ) {
          // BRAND
          this.filter('', '', this.brand, this.page);
        } else if (
          this.category != '' &&
          this.location != '' &&
          this.brand == ''
        ) {
          // CATEGORY AND LOCATION
          this.filter(this.category, this.location, '', this.page);
        } else if (
          this.category != '' &&
          this.brand != '' &&
          this.location == ''
        ) {
          // CATEGORY AND BRAND
          this.filter(this.category, '', this.brand, this.page);
        } else if (
          this.location != '' &&
          this.brand != '' &&
          this.category == ''
        ) {
          // LOCATION AND BRAND
          this.filter('', this.location, this.brand, this.page);
        } else if (
          this.category != '' &&
          this.location != '' &&
          this.brand != ''
        ) {
          // CATEGORY AND LOCATION AND BRAND
          this.filter(this.category, this.location, this.brand, this.page);
        } else {
          // DEFAULT IF PRESENT PAGE
          this.getAllProductDefault(this.page);
        }
      } else {
        // DEFAULT IF NO PRESENT PAGE
        this.getAllProductDefault(0);
      }
    });
  }

  /**
   * Hàm lấy ra tất cả sản phẩm của shop
   * @param page trang số
   */
  public getAllProductDefault(page: any) {
    this.ProductService.getAllProductShopByCustomer(page).subscribe(
      (data) => {
        this.listAllProducts = data.content.map(function (obj: {
          idProduct: any;
          name: any;
          productDiscount: any;
        }) {
          return obj;
        });
        this.page = data;
        this.count = this.page.totalElements;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: 'Không thể truy vấn sản phẩm!',
        });
      }
    );
  }

  /**
   *
   * @param category loại sản phẩm
   * @param location địa chỉ sản phẩm []
   * @param brand nhãn hàng []
   * @param page trang số
   */
  public filter(category: any, location: any, brand: any, page: any) {
    this.ProductService.getAllProductShowInterfaceFilter(
      category,
      location,
      brand,
      page
    ).subscribe((data) => {
      this.listAllProducts = data.content.map(function (obj: {
        idProduct: any;
        name: any;
      }) {
        return obj;
      });
      this.page = data;
      this.count = this.page.totalElements;
    });
  }

  /**
   * Hàm lấy ra sản phẩm được giảm giá
   */
  public getAllDiscountProduct() {
    this.ProductService.getAllProductDiscountShopByCustomer().subscribe(
      (data) => {
        this.listAllProductsDiscount = data;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: 'Không thể truy vấn giảm giá!',
        });
      }
    );
  }

  /**
   * Hàm lấy ra địa điểm
   */
  public getCities() {
    this.AddressService.getApiCity().subscribe((data) => {
      const listCities = data.map(function (obj: { name: any }) {
        return obj;
      });
      this.listCities = listCities;
    });
  }

  /**
   * Hàm lấy ra nhãn hàng của shop
   */
  public getBrands() {
    this.ProductService.getBrand().subscribe((data) => {
      const listBrands = data.map(function (obj: {
        id: any;
        name: any;
        avatar: any;
      }) {
        return obj;
      });
      this.listBrands = listBrands;
    });
  }

  /**
   * Hàm lấy ra loại sản phẩm của shop (child_category)
   */
  public getChildCategory() {
    this.ProductService.getChildCategoriesShop().subscribe(
      (data) => {
        const listCategories = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listCategories = listCategories;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  /**
   * Hàm hiển thị các địa chỉ còn lại bị ẩn
   */
  showHiddenLocation() {
    this.hiddenShowLocationMore = !this.hiddenShowLocationMore;
    this.hiddenLocation = !this.hiddenLocation;
  }

  /**
   * Hàm lọc sản phẩm theo loại
   * @param category loại sản phẩm
   */
  clickFilterCategory(category: any) {
    this.category = category;
    this.routeParams();
  }

  /**
   * Hàm lọc sản phẩm theo địa chỉ
   * @param event sự kiện click vào location
   * @param location tên location
   */
  clickFilterLocation(event: any, location: any) {
    if (event.currentTarget.checked) {
      this.location.push(location);
    } else {
      this.location.splice(this.location.indexOf(location), 1);
      if (this.location.length == 0) {
        this.location = [];
      }
    }
    this.routeParams();
  }

  /**
   * Hàm lọc sản phẩm theo nhãn hàng
   * @param event sự kiện click vào checkbox brand
   * @param brand tên nhãn hàng được check
   */
  clickFilterBrand(event: any, brand: any) {
    if (event.currentTarget.checked) {
      this.brand.push(brand);
    } else {
      this.brand.splice(this.brand.indexOf(brand), 1);
    }
    this.routeParams();
  }

  /**
   * Hàm chuyển trang
   * @param event số trang cần đến
   */
  public handlePageChange(event: number) {
    this.p = event;
    this.routeParams();
  }

  /**
   * Hàm đưa dữ liệu lên param url
   */
  routeParams() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: this.getRequestParams(
        this.category,
        this.location,
        this.brand,
        this.p
      ),
      queryParamsHandling: 'merge', // remove to replace all query params by provided
    });
    this.listProduct();
  }

  /**
   * Hàm lấy các tham số truyền vào
   * @param category loại sản phẩm
   * @param location mảng địa chỉ sản phẩm
   * @param brand mảng nhãn hàng
   * @param page trang số ?
   * @returns
   */
  getRequestParams(
    category: string,
    location: [] = [],
    brand: [] = [],
    page: number
  ): any {
    let params: any = {};
    if (category) {
      params[`category`] = category;
    }
    if (page) {
      params[`page`] = page;
    }
    if (location != undefined && location.length > 0) {
      params[`location`] = location.toString();
    } else {
      params[`location`] = undefined;
    }
    if (brand != undefined && brand.length > 0) {
      params[`brand`] = brand.toString();
    } else {
      params[`brand`] = undefined;
    }
    return params;
  }

  addToCart(product: any) {
    var json = localStorage.getItem('cart');
    this.cart = json ? JSON.parse(json) : [];
    var item: any;
    this.cart.forEach((e) => {
      if (e.id == product.optionDef.id) {
        item = e;
      }
    });
    if (item) {
      item.qty++;
    } else {
      this.cart.push({
        qty: 1,
        name: product.name,
        id: product.optionDef.id,
        price: product.optionDef.price,
        discount: product.productDiscount[0]?product.productDiscount[0]?.discount:0,
      });
    }
    console.log(product);
    console.log(this.cart);
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart);
  }

  /**
   * Hàm xem chi tiết sản phẩm từ cửa hàng
   * @param product id product truyền vào
   */
  showDetailProduct(product: any) {
    console.log(product);
    this.router.navigate([`/product/detail/${product}`]);
  }
}
