import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AddressUserService {
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

  private REST_API_SERVER = 'http://localhost:8080/ede-customer';

  public getAllAdressByUser() {
    const url = this.REST_API_SERVER + '/address/getAll';
    return this.httpClient.get<any>(url, this.httpOptions);
  }
}
