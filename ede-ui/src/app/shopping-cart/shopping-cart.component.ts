import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HeaderService } from 'src/app/Services/header/header.service';
import { ApiAddressService } from '../Services/api-address/api-address.service';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
})
export class ShoppingCartComponent implements OnInit {
  constructor(
    private headerService: HeaderService,
    private apiAddressService: ApiAddressService
  ) {
    this.headerService.myMethod$.subscribe((data) => {
      this.cart = data;
    });
  }
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;
  public cart: Array<any> = [];
  public totalCart: any = 0;
  ngOnInit(): void {
    this.getApiCity();
    this.loadCart();
  }
  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];
  }

  public address = new FormGroup({
    city: new FormControl(''),
    district: new FormControl(''),
    wards: new FormControl(''),
    address: new FormControl(''),
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

  /**
   * Hàm lấy ra tất cả các thành phố
   * @returns {obj} danh sách thành phố
   */
  public getApiCity() {
    this.apiAddressService.getApiCity().subscribe((data) => {
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
    this.apiAddressService.getApiDistricts(id).subscribe((data) => {
      const listDistrict = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      console.log(listDistrict);
      this.listDistricts = listDistrict;
    });
  }

  /**
   * Hàm lấy ra tất cả các phường theo id của quận
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiWards(id: any) {
    this.apiAddressService.getApiWards(id).subscribe((data) => {
      const listWard = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.listWards = listWard;
    });
  }

  showSelectionAddress() {
    this.address.controls['address'].setValue('');
    this.isHiddenAddress = !this.isHiddenAddress;
  }

  getDistricts() {
    this.isHiddenDistrict = false;
    console.log(this.address.controls['city'].value);
    this.getApiDistricts(this.address.controls['city'].value.id);
  }
  getWards() {
    this.isHiddenWards = false;

    this.getApiWards(this.address.controls['district'].value.id);
  }
}
