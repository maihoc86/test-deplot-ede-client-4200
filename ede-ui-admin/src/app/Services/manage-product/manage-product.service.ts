import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class ManageProductService {
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
  private REST_API_SERVER = 'http://localhost:8080/ede-product/admin';
  public getAll() {
    return this.httpClient.get<any>(
      this.REST_API_SERVER + '/getAll',
      this.httpOptions
    );
  }
}
