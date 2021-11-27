import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnChanges, SimpleChanges, Input } from '@angular/core';
import { UserOrderService } from '../Services/user-order/user-order.service';
import { OrderShopService } from '../Services/order-shop/order-shop.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnChanges {
  @Input()
  public productList: any = [];
  public totalPriceBeforeDiscount: number = 0;
  public totalprice: number = 0;
  public discountPrice: number = 0;

  public listDiscountOrder: any = [];
  public discountOrder: any = null; // Mã giảm giá sau khi chọn

  constructor(
    private userOrderService: UserOrderService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productList = JSON.parse('' + localStorage.getItem('cart')) || [];
    this.totalprice = this.totalPriceBeforeDiscount = this.productList.reduce(
      (last: number, item: any) =>
        last + item?.product_option?.price * item?.quantity,
      0
    );
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.totalprice = this.totalPriceBeforeDiscount = this.productList.reduce(
      (last: number, item: any) =>
        last + item?.product_option?.price * item?.quantity,
      0
    );
  }

  /**
   * Hàm lấy ra danh sách các mã giảm giá có trong hệ thống áp dụng cho hóa đơn
   */
  getAllOrderDiscount() {
    this.userOrderService.getAllDiscountOrderSystem().subscribe(
      (data: any) => {
        this.listDiscountOrder = data;
      },
      (error: any) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: error.error.message,
        });
      }
    );
  }

  /**
   * Chọn mã giảm giá để áp dụng cho hóa đơn
   * @param item mã giảm giá
   */
  chooseDiscountOrder(item: any) {
    // Kiểm tra mã giảm giá
    this.userOrderService.checkOrderDiscount(item.id).subscribe(
      (data: any) => {
        if (data.length > 0) {
          // Đã từng sài mã giảm giá này
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Mã giảm giá đã được sử dụng trước đó',
          });
        } else {
          // User này chưa sài mã giảm giá này trước đó
          if (item.status == true) {
            this.discountOrder = item;
            this.discountPrice =
              (this.totalPriceBeforeDiscount * item.discount) / 100;
            this.totalprice =
              this.totalPriceBeforeDiscount - this.discountPrice;
          } else {
            Swal.fire({
              title: 'Thông báo',
              text: 'Mã giảm giá đã hết hạn hoặc đã bị khóa',
              icon: 'warning',
              confirmButtonText: 'OK',
            });
          }
        }
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: error.error.message,
        });
      }
    );
  }

  /**
   * Xóa mã giảm gía đã chọn
   */
  removeDiscount() {
    this.discountOrder = null;
    this.discountPrice = 0;
    this.totalprice = this.totalPriceBeforeDiscount;
  }
}
