import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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
  ngOnInit(): void {
    this.getAll();
  }

  /**
   * Hàm lưu trữ dữ liệu người dùng nhập từ form
   */
  public orderDiscount = new FormGroup({
    id: new FormControl(''),
    discount: new FormControl(''),
    total: new FormControl(''),
    todate: new FormControl(''),
    enddate: new FormControl(''),
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
          this.getAll();
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
  public getAll() {
    this.service.getAll().subscribe(
      (data) => {
        this.listOrderDiscount = data;
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
          this.getAll();
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
              this.getAll();
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
   * Hàm làm mới
   */
  public resetForm() {
    window.location.reload();
  }
}
