import { ProductSearchService } from './../Services/product-search/product-search.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../Services/header/header.service';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { error } from '@angular/compiler/src/util';
import { ProductMeta } from '../models/product-meta.models';
import { ShipService } from '../Services/ship/ship.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent implements OnInit {
  public HOST_ORIGIN = 'http://localhost:8080';

  constructor(
    private activatedRoute: ActivatedRoute,
    private productService: AddProductService,
    private router: Router,
    private productSearchSrv: ProductSearchService,
    private headerService: HeaderService,
    private cookieService: CookieService,
    private address_ship: ShipService
  ) {
    this.activatedRoute.params.subscribe(({ idProduct }) => {
      this.productSearchSrv
        .getProductSearchById(idProduct)
        .subscribe((result) => {
          this.product = result;
          this.ShowAllComment(idProduct);
          this.loadListProductRelatedShop(
            result.shop.id,
            result.childCategory.id
          );
          this.loadListProductRelatedCategory(result.childCategory.id);
          this.getShippingCompany();
        });
    });
  }
  public ship = new FormGroup({
    company: new FormControl(''),
    city: new FormControl(''),
    district: new FormControl(''),
    wards: new FormControl(''),
    address: new FormControl(''),
  });

  public fromComment = new FormGroup({
    id: new FormControl(''),
    rate: new FormControl('5'),
    content: new FormControl('', [Validators.required]),
    date: new FormControl(new Date()),
    id_user: new FormControl(''),
    id_product: new FormControl(''),
  });

  public userLogin: any = null;
  public qty: number = 1;
  public product: any = {};
  public cart: Array<any> = [];
  public listProductRelatedShop: any = [];
  public listProductRelatedProduct: any = [];
  public listProductRelatedCategory: any = [];
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  public listShippingCompany: any = [];
  public listMethodShip: any = [];
  public listComment: Array<any> = [];
  isHiddenCity: boolean = true;
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;
  public fakeArrayRate: any = [];
  public nameOption: any = 'nameOption';
  ngOnInit(): void {
    this.getUserLogin();
    this.addHistoryView();
  }
  addToCart(product: any, qty: any) {
    var json = localStorage.getItem('cart');
    this.cart = json ? JSON.parse(json) : [];
    var item: any;
    this.cart.forEach((e) => {
      if (e.product_option.id == product.optionDef.id) {
        item = e;
      }
    });
    if (item) {
      item.quantity += qty as number;
    } else {
      this.cart.push({
        quantity: 1,
        product_option: product.optionDef
      });
    }

    console.log(this.cart);
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart);
  }
  public loadListProductRelatedShop(idShop: string, idcate: string) {
    this.productSearchSrv
      .loadListProductRelatedShop(idShop, idcate)
      .subscribe((data) => {
        console.log(data);
        this.listProductRelatedShop = data.content;
      });
  }
  public loadListProductRelatedCategory(idCategory: string) {
    this.productService
      .getAllProductByCategory(idCategory)
      .subscribe((data) => {
        this.listProductRelatedCategory = data.content;
      });
  }

  public openDetailByRelated(id: string) {
    document.location.href = 'http://localhost:4200/product/detail/' + id;
  }
  public toInterfaceShop(id: any) {
    this.router.navigate(['/shop/interface/' + id]);
  }

  public ShowAllComment(id: any) {
    this.productService.getAllCommentByIdPeoduct(id).subscribe((data) => {
      this.listComment = data;
      for (let index = 0; index < this.listComment.length; index++) {
        console.log('element.user.id: ' + this.listComment[index].user.id);
        console.log(
          'element.product.id: ' + this.listComment[index].product.id
        );
        this.productService
          .getOptionProduct(
            this.listComment[index].user.id,
            this.listComment[index].product.id
          )
          .subscribe((data2) => {
            this.listComment[index].product.name = data2[0].display_name;
            console.log('nameOption: ' + data2[0].display_name);
            console.log(data2);
          });
      }

      console.log(this.listComment);
    });
  }

  public fakeArray(num: any) {
    this.fakeArrayRate = [];
    for (let index = 0; index < num; index++) {
      this.fakeArrayRate.push(index);
    }
    // console.log('rate: ' + this.fakeArrayRate)
    return this.fakeArrayRate;
  }

  public showNameOption(idUser: any, id: any) {
    this.productService.getOptionProduct(idUser, id).subscribe((data) => {
      return this.nameOption;
    });
  }

  public CreateComment() {
    console.log(this.fromComment);
    console.log(this.product);
    this.headerService.getUserByToken(this.cookieService.get('auth')).subscribe(
      (data) => {
        if (!this.fromComment.valid) {
          alert('Bạn chưa nhập bình luận');
        } else {
          this.fromComment.controls['id_product'] = this.product.idProduct;
          this.productService
            .createCommentProductByIdUser(
              this.userLogin.id,
              this.product.idProduct,
              this.fromComment
            )
            .subscribe(
              (data) => {
                this.ShowAllComment(this.product.idProduct);
              },
              (error) => {
                // console.log(error)
                if (error.status == 403) {
                  Swal.fire({
                    title: 'Thông báo',
                    icon: 'warning',
                    text: error.error.message,
                    showConfirmButton: true,
                    showCancelButton: true,
                    confirmButtonText: 'Cập nhật lại bình luận trước?',
                    cancelButtonText: 'Hủy',
                  }).then((rs) => {
                    if (rs.isConfirmed) {
                      this.productService
                        .updateCommentProductByIdUser(
                          this.userLogin.id,
                          this.product.idProduct,
                          this.fromComment
                        )
                        .subscribe(() => {
                          this.ShowAllComment(this.product.idProduct);
                        });
                    }
                  });
                } else {
                  Swal.fire({
                    title: 'Thông báo',
                    icon: 'error',
                    text: error.error.message,
                  });
                }
              }
            );
          //console.log(this.product)
        }
      },
      (error) => {
        Swal.fire({
          title: 'Thông báo',
          icon: 'warning',
          text: 'Bạn chưa đăng nhập',
          showConfirmButton: true,
          showCancelButton: true,
          confirmButtonText: 'Đăng nhập ngay?',
          cancelButtonText: 'Để sau',
        }).then((rs) => {
          if (rs.isConfirmed) {
            this.router.navigate(['/login']);
          }
        });
      }
    );
  }

  public getUserLogin() {
    this.headerService
      .getUserByToken(this.cookieService.get('auth'))
      .subscribe((data) => {
        this.userLogin = data;
      });
  }

  /** Hàm thêm lịch sử view của người dùng khi xem product
   *
   */
  public addHistoryView() {
    let ProductMetaList: any = {};

    this.headerService
      .getUserByToken(this.cookieService.get('auth'))
      .subscribe((user) => {
        this.productService
          .getProduct(this.product.idProduct)
          .subscribe((product) => {
            ProductMetaList = {
              date_view: new Date(),
              cookie: this.cookieService.get('auth'),
              user: user,
              product: product,
            };

            this.productService
              .getProductMetaView(user.id, product.id)
              .subscribe((dataGet: any) => {
                if (dataGet == null) {
                  this.productService
                    .addProductMetaView(ProductMetaList)
                    .subscribe(
                      (data: any) => {},
                      (error: any) => {
                        console.log(error);
                      }
                    );
                }
              });
          });
      });
  }

  public getShippingCompany() {
    this.address_ship.getShippingCompany().subscribe((data) => {
      const listShippingCompany = data.map(function (obj: {
        id: any;
        name: any;
      }) {
        return obj;
      });
      this.listShippingCompany = listShippingCompany;
    });
  }

  /**
   * Hàm lấy ra tất cả các thành phố
   * @param method phương thức vận chuyển đến tp
   * @returns {obj} danh sách thành phố
   */
  public getApiCity() {
    this.address_ship
      .getApiCity(this.ship.controls['company'].value.id)
      .subscribe((data) => {
        const listCity = data.map(function (obj: {
          id: any;
          code: any;
          name: any;
        }) {
          return obj;
        });
        this.listCitys = listCity;
      });
  }

  /**
   * Hàm lấy ra tất cả các quận theo id của thành phố
   * @param {string} id id của thành phố
   * @returns {obj} danh sách quận
   */
  public getApiDistricts(id: any) {
    this.address_ship
      .getApiDistricts_byCity(this.ship.controls['company'].value.id, id)
      .subscribe((data) => {
        const listDistrict = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listDistricts = listDistrict;
      });
  }

  /**
   * Hàm lấy ra tất cả các phường theo id của quận
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiWards(id: any) {
    this.address_ship
      .getApiWards_byDisctrict(this.ship.controls['company'].value.id, id)
      .subscribe((data) => {
        const listWard = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listWards = listWard;
        this.getApiMethodShip();
      });
  }

  /**
   * Hàm tính xem với quãng đường đó thì sẽ vận chuyển theo cách nào
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiMethodShip() {
    const split = this.product.shop.address.split(',');
    console.log(split); // TODO cắt lấy quận huyện, so sánh vs api quận huyện lấy về r lấy ra id truyền vào get API
    this.address_ship
      .getApiMethodShip(
        this.ship.controls['company'].value.id,
        'test',
        this.ship.controls['district'].value.id
      )
      .subscribe((data) => {
        const listMethodShip = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listMethodShip = listMethodShip;
      });
  }

  chooseShippingCompany() {
    this.isHiddenCity = !this.isHiddenCity;
    this.getApiCity();
  }
  showSelectionAddress() {
    this.ship.controls['address'].setValue('');
    this.isHiddenAddress = !this.isHiddenAddress;
  }

  getDistricts() {
    this.isHiddenDistrict = false;
    this.getApiDistricts(this.ship.controls['city'].value.id);
  }
  getWards() {
    this.isHiddenWards = false;
    this.getApiWards(this.ship.controls['district'].value.id);
  }
}
