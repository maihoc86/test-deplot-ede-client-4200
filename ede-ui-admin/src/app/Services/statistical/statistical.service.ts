import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { User } from 'src/app/models/user.model';
import { CookieService } from 'ngx-cookie-service';
@Injectable({
  providedIn: 'root',
})
export class StatisticalService {
  private API_USER = 'http://localhost:8080/ede-customer';
  private API_PRODUCT = 'http://localhost:8080/ede-product';
  private API_ORDER = 'http://localhost:8080/ede-order';
  private API_SHOP = 'http://localhost:8080/ede-customer/shop';
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

  public selectTotalUserNew() {
    return this.httpClient.get<any>(
      this.API_USER + '/admin/getNewUsers',
      this.httpOptions
    );
  }
  public selectTotalUsers() {
    return this.httpClient.get<any>(
      this.API_USER + '/admin/users',
      this.httpOptions
    );
  }
  public selectTotalProductNew() {
    return this.httpClient.get<any>(
      this.API_PRODUCT + '/admin/getProductNew',
      this.httpOptions
    );
  }
  public selectTotalProduct() {
    return this.httpClient.get<any>(
      this.API_PRODUCT + '/admin/product/getAll',
      this.httpOptions
    );
  }
  public selectTotalShop() {
    return this.httpClient.get<any>(
      this.API_SHOP + '/admin/viewall',
      this.httpOptions
    );
  }

  public selectTotalShopNew() {
    return this.httpClient.get<any>(
      this.API_SHOP + '/admin/getNewShops',
      this.httpOptions
    );
  }
}
