import { Component, OnInit } from '@angular/core';
import { HeaderService } from 'src/app/Services/header/header.service';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css'],
})
export class ShoppingCartComponent implements OnInit {
  constructor(private headerService: HeaderService) {
    this.headerService.myMethod$.subscribe((data) => {
      this.cart = data;
    });
  }
  public cart: Array<any> = [];
  public totalCart: any = 0;
  ngOnInit(): void {
    this.loadCart();
  }
  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];
  }

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
}
