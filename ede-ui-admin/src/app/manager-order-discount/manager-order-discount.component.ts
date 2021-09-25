import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import Swal from 'sweetalert2';
import { Order_discount } from '../models/Order_discount.model';
import { ManageOrderDiscountService } from '../Services/manage-order-discount/manage-order-discount.service';

@Component({
  selector: 'app-manager-order-discount',
  templateUrl: './manager-order-discount.component.html',
  styleUrls: ['./manager-order-discount.component.css'],
})
export class ManagerOrderDiscountComponent implements OnInit {
  constructor(private service: ManageOrderDiscountService) {}
  public listOrderDiscount: any = [];
  public page: any = [];
  public size: number = 5;
  public p: number = 1;
  public count: any;

  minDate = moment(new Date()).format('YYYY-MM-DD');
  maxDate = moment(new Date(2023, 1, 1)).format('YYYY-MM-DD');
  ngOnInit(): void {
    this.getAll('', '', 1, this.size);
  }

  /**
   * Hàm lưu trữ dữ liệu người dùng nhập từ form
   */
  public orderDiscount = new FormGroup({
    id: new FormControl(''),
    discount: new FormControl('', [Validators.required]),
    total: new FormControl('', [
      Validators.required,
      Validators.pattern('([0-9]{0,9})\\b'),
    ]),
    todate: new FormControl('', [Validators.required]),
    enddate: new FormControl('', [Validators.required]),
    status: new FormControl(true, [Validators.required]),
  });
  /**
   * Hàm lưu trữ dữ liệu người dùng nhập từ search
   */
  public searchGroup = new FormGroup({
    searchTuNgay: new FormControl(''),
    searchDenNgay: new FormControl(''),
  });

  /**
   * Hàm tạo ra data để gửi lên server
   */
  private createNewData(formGroup: FormGroup) {
    const stringReturn: any = {};
    for (const controlName in formGroup.controls) {
      if (controlName) {
        stringReturn[controlName] = formGroup.controls[controlName].value;
      }
    }
    return stringReturn as Order_discount;
  }

  /**
   * Hàm thêm mới giảm giá hóa đơn
   */
  public create() {
    this.service.create(this.createNewData(this.orderDiscount)).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Thêm mới thành công',
          text: '',
        }).then(() => {
          this.getAll('', '', 1, this.size);
          window.location.reload();
        });
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Có lỗi xảy ra',
          text: error.error.message,
        });
      }
    );
  }
  /**
   * Hàm lấy ra tất cả giảm giá hóa đơn
   */
  public getAll(searchTuNgay: any, searchDenNgay: any, page: any, size: any) {
    page = page - 1;
    this.service.getAll(searchTuNgay,searchDenNgay,page, size).subscribe(
      (data) => {
        const item = data.content.map(function (obj: {
          id: string;
          total: number;
          discount: number;
          todate: Date;
          enddate: Date;
          status: boolean;
        }) {
          return obj;
        });
        this.listOrderDiscount = item;
        this.page = data;
        this.count = this.page.totalElements;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Có lỗi lấy dữ liệu',
          text: error.error.message,
        });
      }
    );
  }

  /**
   * Hàm chỉ cho phép nhập số
   */
  numberOnly(event: any) {
    const charCode = event.which ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  /**
   *
   */
  public editOrderDiscount(id: any) {
    this.service.getById(id).subscribe(
      (data) => {
        this.orderDiscount.patchValue({
          id: data.id,
          discount: data.discount,
          total: data.total,
          todate: data.todate,
          enddate: data.enddate,
        });
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Có lỗi lấy dữ liệu',
          text: error.error.message,
        });
      }
    );
  }

  /**
   * Hàm cập nhật giảm giá hóa đơn
   */
  public update() {
    this.service.update(this.createNewData(this.orderDiscount)).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Cập nhật thành công',
          text: '',
        }).then(() => {
          this.getAll('', '', 1, this.size);
          window.location.reload();
        });
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Có lỗi xảy ra',
          text: error.error.message,
        });
      }
    );
  }

  /**
   *
   */
  public deleteOrderDiscount(id: any) {
    Swal.fire({
      title: 'Bạn có chắc chắn muốn xóa?',
      text: '',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Đồng ý',
      cancelButtonText: 'Hủy',
    }).then((result) => {
      if (result.value) {
        this.service.deleteOrderDiscountById(id).subscribe(
          (data) => {
            Swal.fire({
              icon: 'success',
              title: 'Xóa thành công',
              text: '',
            }).then(() => {
              this.getAll('', '', 1, this.size);
              window.location.reload();
            });
          },
          (error) => {
            Swal.fire({
              icon: 'error',
              title: 'Có lỗi xảy ra',
              text: error.error.message,
            });
          }
        );
      }
    });
  }
  /**
   * Hàm tìm kiếm
   * @param event sự kiện nhập trên bàn phím
   */
  search() {
    this.getAll(this.searchGroup.value.searchTuNgay, this.searchGroup.value.searchDenNgay, 1, this.size);
    console.log(this.listOrderDiscount);
  }
  /**
   * Hàm làm mới
   */
  public resetForm() {
    window.location.reload();
  }
}
