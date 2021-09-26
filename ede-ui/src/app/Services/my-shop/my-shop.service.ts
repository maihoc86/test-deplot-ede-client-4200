import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Shop } from '../../models/shop.model';
@Injectable({
  providedIn: 'root',
})
export class MyShopService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: this.cookieService.get('auth'),
    }),
  };
  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) {}

  private REST_API_SERVER = 'http://localhost:8080/ede-customer/shop';

  public updateInfoShop(data: Shop) {
    return this.httpClient.put<any>(
      this.REST_API_SERVER + '/info/update/',
      data,
      this.httpOptions
    );
  }

  /**
   * Hàm lấy ra thông tin shop theo ID
   */
  public getShopInfo(idShop: Shop) {
    return this.httpClient.get<any>(
      this.REST_API_SERVER + '/view/info/' + idShop,
      this.httpOptions
    );
  }
}
