import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HeaderService } from 'src/app/Services/header/header.service';
import { ShipService } from '../Services/ship/ship.service';
import { AddressUserService } from '../Services/address-user/address-user.service';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
})
export class ShoppingCartComponent implements OnInit {
  public loadingFeeShip: boolean = true;
  public loadingAddress: boolean = true;
  constructor(
    private headerService: HeaderService,
    private address_ship: ShipService,
    private address_user: AddressUserService,
    private cookieService: CookieService
  ) {
    this.headerService.myMethod$.subscribe((data) => {
      this.cart = data;
    });
  }
  public listIdShopinCart: any = [];
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  public listShippingCompany: any = [];
  public listMethodShip: any = [];
  public listAddressUser: any = [];
  public feeShip: number = 0; // phí ship sau khi tính toán
  isHiddenCity: boolean = true;
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;
  public cart: Array<any> = [];
  public totalCart: any = 0;
  public user: any = {};

  public toBot() {
    document.getElementById('table')?.scrollIntoView({ behavior: 'smooth' });
  }

  ngOnInit(): void {
    this.loadCart();
    this.showInfoAddress();
    this.getAddressMainUser();
  }

  getAddressMainUser() {
    if (this.cookieService.get('auth') != '') {
      this.headerService
        .getUserByToken(this.cookieService.get('auth'))
        .subscribe(
          (data) => {
            this.user = data;
            this.changeAddressUser(this.user.address);
          },
          (err) => {
            console.log(err);
          }
        );
    } else {
      this.user = {};
    }
  }
  getAllAddressUser() {
    this.address_user.getAllAdressByUser().subscribe((data) => {
      const listAddressUser = data.map(function (obj: {
        id: any;
        address: any;
      }) {
        return obj;
      });
      this.listAddressUser = listAddressUser;
    });
  }

  showInfoAddress() {
    this.getShippingCompany();
    this.getApiCity();
    // this.getApiMethodShip();
  }

  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];

    const term: any = [];
    this.cart.forEach((e) => {
      if (term.indexOf(e.product_option.product.shop.id) === -1) {
        term.push(e.product_option.product.shop.id);
        this.listIdShopinCart.push({ shop: e.product_option.product.shop });
      }
    });
  }

  public ship = new FormGroup({
    company: new FormControl('GHN'), // Đối tác vận chuyển default Giao hàng nhanh
    city: new FormControl(''), // city default Hòa Bình
    from_district: new FormControl(''), // district của shop
    district: new FormControl(''), // to_district Huyện Yên Thủy
    wards: new FormControl(''), // wards default Xã Yên Trị
    method: new FormControl(''), // phương thức vận chuyện [đi bộ, máy bay ...] default là bay
    address: new FormControl(''), // địa chỉ nhận hàng
  });

  /**
   * Hàm thay đổi số lượng
   * @param qtyCurrent số lượng hiện tại
   * @param qtyChange số lượng muốn thay đổi
   */
  changeQty(qtyCurrent: any, qtyChange: any) {
    qtyCurrent.quantity = qtyChange;
    qtyChange > qtyCurrent.quantity
      ? qtyCurrent.quantity++
      : qtyCurrent.quantity == qtyChange
      ? (qtyCurrent.quantity = qtyChange)
      : qtyCurrent.quantity--;
    qtyCurrent.quantity == 0 ? this.removeItemCart(qtyCurrent) : '';
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.loadTotal();
    this.headerService.myMethod(this.cart);
    this.changeFeeShipCart(
      qtyCurrent,
      this.cart.findIndex((x) => x === qtyCurrent)
    );
  }

  changeFeeShipCart(cartItem: any, index: number) {
    var address_product_cart =
      cartItem.product_option.product.shop.address.split(','); // lấy ra địa chỉ của shop
    let idCity = '';

    // Lấy ra id của thành phố khi shop có địa chỉ thành phố trùng
    for (let i = 0; i < this.listCitys.length; i++) {
      if (this.listCitys[i].name.includes(address_product_cart[3].trim())) {
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
            if (listDistrict[i].name.includes(address_product_cart[2].trim())) {
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
                      this.listMethodShip[0].id // TODO xem phí nào rẻ nhất lấy phương thức đó
                    );
                    let total_weight = 300 * cartItem.quantity;
                    this.address_ship
                      .getFeeShip(
                        this.ship.controls['company'].value,
                        this.ship.controls['from_district'].value,
                        this.ship.controls['district'].value,
                        this.ship.controls['method'].value,
                        total_weight // Cân nặng mẫu, ch có cân nặng
                      )
                      .subscribe(
                        (data: any) => {
                          //TODO Phí vận chuyển cho từng sp trong cart
                          this.cart[index].feeShip = data.total;
                          localStorage.setItem(
                            'cart',
                            JSON.stringify(this.cart)
                          );
                          this.loadTotal();
                          this.headerService.myMethod(this.cart);
                          this.loadingFeeShip = false;
                        },
                        (error) => {
                          console.log(error);
                        }
                      );
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

  /**
   * Hàm xóa sản phẩm khỏi giỏ hàng
   * @param e sản phẩm muốn xóa
   */
  public removeItemCart(e: any) {
    this.cart.splice(
      this.cart.findIndex((es) => es.id == e.id),
      1
    );
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.loadTotal();
    this.headerService.myMethod(this.cart);
  }

  // Tính tổng tiền
  loadTotal() {
    this.totalCart = 0;
    this.cart.forEach((e) => {
      this.totalCart +=
        e.qty *
        (e.discount == 0 ? e.price : e.price - e.price * (e.discount / 100));
    });
  }

  /**
   * Hàm chỉ được dùng số
   * @param event sự kiện change input
   * @param qtyCurrent số lượng hiện tại
   * @param qtyChange số lượng muốn thay đổi
   */
  numberOnly(event: any, qtyCurrent: any, qtyChange: any) {
    const charCode = event.which ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    } else {
      qtyCurrent.qty = qtyChange;
      localStorage.setItem('cart', JSON.stringify(this.cart));
      this.loadTotal();
      return true;
    }
  }

  public getShippingCompany() {
    this.loadingAddress = true;
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
    this.loadingAddress = true;
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
    this.loadingAddress = true;
    this.address_ship
      .getApiDistricts_byCity(this.ship.controls['company'].value, idCity)
      .subscribe(
        (data) => {
          const listDistrict = data.map(function (obj: { id: any; name: any }) {
            return obj;
          });
          this.listDistricts = listDistrict;
          this.ship.controls['district'].setValue(this.listDistricts[0].id);
          this.getApiWards(this.ship.controls['district'].value);
          this.getFeeShip();
        },
        (err) => {
          console.log(err);
        }
      );
  }

  /**
   * Hàm lấy ra tất cả các phường theo id của quận
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiWards(id: any) {
    this.loadingAddress = true;
    this.address_ship
      .getApiWardsByDisctrict(this.ship.controls['company'].value, id)
      .subscribe((data) => {
        const listWard = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listWards = listWard;
        this.ship.controls['wards'].setValue(this.listWards[0].id);
        this.getFeeShip();
        this.loadingAddress = false;
      });
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

  chooseDistrict() {
    this.getApiWards(this.ship.controls['district'].value);
  }

  chooseWards() {
    this.ship.controls['address'].setValue('');
    this.isHiddenAddress = !this.isHiddenAddress;
  }

  // Tính toán phí vận chuyển
  getFeeShip() {
    this.loadingFeeShip = true;
    for (let iCart = 0; iCart < this.cart.length; iCart++) {
      var address_product_cart =
        this.cart[iCart].product_option.product.shop.address.split(','); // lấy ra địa chỉ của shop
      let idCity = '';

      // Lấy ra id của thành phố khi shop có địa chỉ thành phố trùng
      for (let i = 0; i < this.listCitys.length; i++) {
        if (this.listCitys[i].name.includes(address_product_cart[3].trim())) {
          idCity = this.listCitys[i].id;
        }
      }

      // Lấy ra danh sách quận huyện theo id của thành phố
      this.address_ship
        .getApiDistricts_byCity(this.ship.controls['company'].value, idCity)
        .subscribe(
          (data) => {
            const listDistrict = data.map(function (obj: {
              id: any;
              name: any;
            }) {
              return obj;
            });

            // Lấy ra id của quận huyện khi shop có địa chỉ quận huyện trùng
            for (let i = 0; i < listDistrict.length; i++) {
              if (
                listDistrict[i].name.includes(address_product_cart[2].trim())
              ) {
                this.ship.controls['from_district'].setValue(
                  listDistrict[i].id
                );
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
                        this.listMethodShip[0].id // TODO xem phí nào rẻ nhất lấy phương thức đó
                      );
                      let total_weight = 300 * this.cart[iCart].quantity;
                      this.address_ship
                        .getFeeShip(
                          this.ship.controls['company'].value,
                          this.ship.controls['from_district'].value,
                          this.ship.controls['district'].value,
                          this.ship.controls['method'].value,
                          total_weight // Cân nặng mẫu, ch có cân nặng
                        )
                        .subscribe(
                          (data: any) => {
                            //TODO Phí vận chuyển cho từng sp trong cart
                            this.cart[iCart].feeShip = data.total;
                            localStorage.setItem(
                              'cart',
                              JSON.stringify(this.cart)
                            );
                            this.loadTotal();
                            this.headerService.myMethod(this.cart);
                            this.loadingFeeShip = false;
                          },
                          (error) => {
                            console.log(error);
                          }
                        );
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
  }

  changeAddressUser(address: string) {
    let idCity = '';
    let idDistrict = '';
    let idWard = '';
    let address_split = address.split(',');
    this.ship.controls['address'].setValue(address_split[0]);

    // Lấy ra id của thành phố khi user có địa chỉ thành phố trùng
    setTimeout(() => {
      for (let i = 0; i < this.listCitys.length; i++) {
        if (this.listCitys[i].name.includes(address_split[3].trim())) {
          idCity = this.listCitys[i].id;
          this.ship.controls['city'].setValue(idCity);
          this.getApiDistricts(idCity);
        }
      }
    }, 1000);

    setTimeout(() => {
      // Lấy ra id của quận khi user có địa chỉ quận trùng
      for (let i = 0; i < this.listDistricts.length; i++) {
        if (this.listDistricts[i].name.includes(address_split[2].trim())) {
          idDistrict = this.listDistricts[i].id;
          this.ship.controls['district'].setValue(idDistrict);
          this.getApiWards(this.ship.controls['district'].value);
        }
      }
    }, 2500);
    setTimeout(() => {
      // Lấy ra id của phường khi user có địa chỉ phường trùng
      for (let i = 0; i < this.listWards.length; i++) {
        if (this.listWards[i].name.includes(address_split[1].trim())) {
          idWard = this.listWards[i].id;
          this.ship.controls['wards'].setValue(idWard);
        }
      }
    }, 4500);
  }
}
