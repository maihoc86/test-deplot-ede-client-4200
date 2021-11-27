import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class UserOrderService {
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

  private API_SERVER = 'http://localhost:8080/ede-order';

  getAllDiscountOrderSystem() {
    return this.httpClient.get(
      `${this.API_SERVER}/view/all/discount/order`,
      this.httpOptions
    );
  }

  checkOrderDiscount(orderId: any) {
    return this.httpClient.get(
      `${this.API_SERVER}/check/discount/order?idDiscount=` + orderId,
      this.httpOptions
    );
  }
}
