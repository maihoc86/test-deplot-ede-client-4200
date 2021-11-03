import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HeaderService } from 'src/app/Services/header/header.service';
import { ShipService } from '../Services/ship/ship.service';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
})
export class ShoppingCartComponent implements OnInit {
  constructor(
    private headerService: HeaderService,
    private address_ship: ShipService
  ) {
    this.headerService.myMethod$.subscribe((data) => {
      this.cart = data;
    });
  }
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  public listShippingCompany: any = [];
  public listMethodShip: any = [];
  public feeShip: number = 0; // phí ship sau khi tính toán
  isHiddenCity: boolean = true;
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;
  public cart: Array<any> = [];
  public totalCart: any = 0;
  ngOnInit(): void {
    this.loadCart();
  }

  showInfoAddress() {
    this.getShippingCompany();
    this.getApiCity();
    // this.getApiMethodShip();
  }

  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];
  }

  public ship = new FormGroup({
    company: new FormControl('GHN'), // Đối tác vận chuyển default Giao hàng nhanh
    city: new FormControl(''), // city default Hòa Bình
    from_district: new FormControl(''), // district của shop
    district: new FormControl(''), // to_district Huyện Yên Thủy
    wards: new FormControl(''), // wards default Xã Yên Trị
    method: new FormControl(''), // phương thức vận chuyện [đi bộ, máy bay ...] default là bay
  });

  /**
   * Hàm thay đổi số lượng
   * @param qtyCurrent số lượng hiện tại
   * @param qtyChange số lượng muốn thay đổi
   */
  changeQty(qtyCurrent: any, qtyChange: any) {
    qtyCurrent.qty = qtyChange;
    qtyChange > qtyCurrent.qty
      ? qtyCurrent.qty++
      : qtyCurrent.qty == qtyChange
      ? (qtyCurrent.qty = qtyChange)
      : qtyCurrent.qty--;
    qtyCurrent.qty == 0 ? this.removeItemCart(qtyCurrent) : '';
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.loadTotal();
    this.headerService.myMethod(this.cart);
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
      .subscribe(
        (data) => {
          const listDistrict = data.map(function (obj: { id: any; name: any }) {
            return obj;
          });
          this.listDistricts = listDistrict;
          this.ship.controls['district'].setValue(this.listDistricts[0].id);
          console.log(this.listDistricts);
          this.getApiWards(this.ship.controls['district'].value);
          this.getApiMethodShip();
          // this.getApiMethodShip();
        },
        (err) => {
          console.log(err);
        }
      );
  }
  /**
   * Hàm tính xem với quãng đường đó thì sẽ vận chuyển theo cách nào
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiMethodShip() {
    var split = this.cart[0].product_option.product.shop.address.split(','); // lấy ra địa chỉ của shop

    console.log(split);
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

  /**
   * Hàm lấy ra tất cả các phường theo id của quận
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiWards(id: any) {
    this.address_ship
      .getApiWards_byDisctrict(this.ship.controls['company'].value, id)
      .subscribe((data) => {
        const listWard = data.map(function (obj: { id: any; name: any }) {
          return obj;
        });
        this.listWards = listWard;
        this.ship.controls['wards'].setValue(this.listWards[0].id);
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
    this.getApiMethodShip();
  }

  chooseWards() {
    this.ship.controls['address'].setValue('');
    this.isHiddenAddress = !this.isHiddenAddress;
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
          console.log(this.feeShip);
        },
        (error) => {
          console.log(error);
        }
      );
  }
}
