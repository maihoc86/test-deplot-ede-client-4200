import { ProductSearchService } from './../Services/product-search/product-search.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../Services/header/header.service';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
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
          this.productShow = result.optionDef;
          console.log(result)
          this.product = result;
          this.ShowAllComment(idProduct);
          console.log(this.product);
          this.loadListProductRelatedShop(
            result.shop.id,
            result.childCategory.id
          );
          this.loadListProductRelatedCategory(result.childCategory.id);
          this.getShippingCompany();
          this.getApiCity();
          // this.getApiMethodShip();
        });
    });
  }
  public ship = new FormGroup({
    company: new FormControl('GHN'), // Đối tác vận chuyển default Giao hàng nhanh
    city: new FormControl(''), // city default Hòa Bình
    from_district: new FormControl(''), // district của shop
    district: new FormControl(''), // to_district Huyện Yên Thủy
    wards: new FormControl(''), // wards default Xã Yên Trị
    method: new FormControl(''), // phương thức vận chuyện [đi bộ, máy bay ...] default là bay
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
  public feeShip: number = 0; // phí ship sau khi tính toán
  public fakeArrayRate: any = [];
  public nameOption: any = 'nameOption';
  public productShow: any = [];
  ngOnInit(): void {
    this.getUserLogin();
    this.addHistoryView();
  }
  addToCart( qty: any) {
    var json = localStorage.getItem('cart');
    this.cart = json ? JSON.parse(json) : [];
    var item: any;
    this.cart.forEach((e) => {
      if (e.product_option.id == this.productShow.id) {
        item = e;
      }
    });
    if (item) {
      item.quantity=Number( item.quantity)+Number(qty);
    } else {
      this.cart.push({
        quantity: qty,
        feeShip:0,
        product_option: this.productShow,
      });
    }
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart);
  }
  public loadListProductRelatedShop(idShop: string, idcate: string) {
    this.productSearchSrv
      .loadListProductRelatedShop(idShop, idcate)
      .subscribe((data) => {
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

  /**
   * Lấy ra đơn vị vận chuyển
   */
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
   * @param company đơn vị vận chuyển
   * @returns {obj} danh sách thành phố
   */
  public getApiCity() {
    this.address_ship
      .getApiCity(this.ship.controls['company'].value)
      .subscribe((data) => {
        const listCity = data.map(function (obj: {
          id: any;
          code: any;
          name: any;
        }) {
          return obj;
        });
        this.listCitys = listCity;
        this.ship.controls['city'].setValue(this.listCitys[0].id);
        this.getApiDistricts(this.listCitys[0].id);
      });
  }

  /**
   * Hàm lấy ra tất cả các quận theo id của thành phố
   * @param company đơn vị vận chuyển
   * @param {string} id id của thành phố
   * @returns {obj} danh sách quận
   */
  public getApiDistricts(idCity: any) {
    this.address_ship
      .getApiDistricts_byCity(this.ship.controls['company'].value, idCity)
      .subscribe((data) => {
        const listDistrict = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listDistricts = listDistrict;
        this.ship.controls['district'].setValue(this.listDistricts[0].id);
        this.getApiMethodShip();
      });
  }

  /**
   * Hàm tính xem với quãng đường đó thì sẽ vận chuyển theo cách nào
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiMethodShip() {
    var split = this.product.shop.address.split(','); // lấy ra địa chỉ của shop
    let idCity = '';

    // Lấy ra id của thành phố khi shop có địa chỉ thành phố trùng
    for (let i = 0; i < this.listCitys.length; i++) {
      if (this.listCitys[i].name.includes(split[3].trim())) {
        idCity = this.listCitys[i].id;
      }
    }

    // Lấy ra danh sách quận huyện theo id của thành phố
    this.address_ship
      .getApiDistricts_byCity(this.ship.controls['company'].value, idCity)
      .subscribe(
        (data) => {
          const listDistrict = data.map(function (obj: { id: any; name: any }) {
            return obj;
          });

          // Lấy ra id của quận huyện khi shop có địa chỉ quận huyện trùng
          for (let i = 0; i < listDistrict.length; i++) {
            if (listDistrict[i].name.includes(split[2].trim())) {
              this.ship.controls['from_district'].setValue(listDistrict[i].id);
              // Bắt đầu tính phương tiện vận chuyển từ quận huyện shop tới địa chỉ đã chọn
              this.address_ship
                .getApiMethodShip(
                  this.ship.controls['company'].value,
                  this.ship.controls['from_district'].value,
                  this.ship.controls['district'].value
                )
                .subscribe(
                  (data) => {
                    const listMethodShip = data.map(function (obj: {
                      id: any;
                      name: any;
                    }) {
                      return obj;
                    });
                    this.listMethodShip = listMethodShip;
                    this.ship.controls['method'].setValue(
                      this.listMethodShip[0].id
                    );
                    this.getFeeShip();
                  },
                  (error) => {
                    console.log(error);
                  }
                );
            }
          }
        },
        (error) => {
          console.log(error);
        }
      );
  }

  // Chọn đơn vị vận chuyển sau đó hiển thị lên danh sách thành phố
  chooseShippingCompany() {
    if (
      this.ship.controls['city'].value != '' &&
      this.ship.controls['district'].value != '' &&
      this.ship.controls['method'].value != ''
    ) {
      this.getFeeShip();
    } else {
      this.getApiCity();
    }
  }

  // Chọn thành phố, sau đó hiển thị danh sách quận huyện
  chooseCity() {
    this.getApiDistricts(this.ship.controls['city'].value);
  }

  // Tính toán phí vận chuyển
  getFeeShip() {
    this.address_ship
      .getFeeShip(
        this.ship.controls['company'].value,
        this.ship.controls['from_district'].value,
        this.ship.controls['district'].value,
        this.ship.controls['method'].value,
        300 // Cân nặng mẫu, ch có cân nặng
      )
      .subscribe(
        (data: any) => {
          this.feeShip = data.total;
        },
        (error) => {
          console.log(error);
        }
      );
  }

  changeOptione(option: any){
    this.productShow=option
  }
}
