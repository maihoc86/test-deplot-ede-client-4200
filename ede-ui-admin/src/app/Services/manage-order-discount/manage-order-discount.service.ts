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
   */
  public getAll(searchTuNgay: any, searchDenNgay: any, page: any, size: any) {
    return this.httpClient.get<any>(
      this.REST_API_SERVER +
        '/getAll/discount/order?' +
        'page=' +
        page +
        '&size=' +
        size +
        '&searchTuNgay=' +
        searchTuNgay +
        '&searchDenNgay=' +
        searchDenNgay,
      this.httpOptions
    );
  }

  /**
   * Hàm thêm giảm giá áp dụng cho hóa đơn
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
   * @param id id của giảm giá hóa đơn
   */
  public getById(id: any) {
    return this.httpClient.get<any>(
      this.REST_API_SERVER + '/discount/order/' + id,
      this.httpOptions
    );
  }

  /**
   * Hàm xóa giảm giá hóa đơn
   * @param id id của giảm giá hóa đơn
   */
  public deleteOrderDiscountById(id: any) {
    return this.httpClient.delete<any>(
      `${this.REST_API_SERVER}/delete/discount/order/` + id,
      this.httpOptions
    );
  }
}
