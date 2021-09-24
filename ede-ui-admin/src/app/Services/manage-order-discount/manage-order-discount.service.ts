import { Order_discount } from './../../models/Order_discount.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class ManageOrderDiscountService {
  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) {}
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: this.cookieService.get('auth'),
    }),
  };
  private REST_API_SERVER = 'http://localhost:8080/ede-order/admin';

  /**
   * Hàm lấy tất cả giảm giá của hóa đơn được áp dụng
   * @returns {listObj} danh sách tất cả các giảm giá dánh cho hóa đơn
   */
  public getAll() {
    return this.httpClient.get<any>(
      this.REST_API_SERVER + '/getAll/discount/order',
      this.httpOptions
    );
  }

  /**
   * Hàm thêm giả áp dụng cho hóa đơn
   * @returns {obj} hóa đơn được thêm giảm giá
   */
  public create(data: Order_discount) {
    return this.httpClient.post<any>(
      this.REST_API_SERVER + '/create/discount/order',
      data,
      this.httpOptions
    );
  }

  /**
   * Hàm sửa giả áp dụng cho hóa đơn
   * @returns {obj} hóa đơn được sửa giảm giá
   */
  public update(data: Order_discount) {
    return this.httpClient.put<any>(
      this.REST_API_SERVER + '/update/discount/order',
      data,
      this.httpOptions
    );
  }

  /**
   * Hàm lấy chi tiết giảm giá của hóa đơn
   */
  public getById(id: any) {
    return this.httpClient.get<any>(
      this.REST_API_SERVER + '/discount/order/' + id,
      this.httpOptions
    );
  }

  /**
   * Hàm xóa giảm giá hóa đơn
   */
  public deleteOrderDiscountById(id: any) {
    return this.httpClient.delete<any>(
      `${this.REST_API_SERVER}/delete/discount/order/` + id,
      this.httpOptions
    );
  }
}
