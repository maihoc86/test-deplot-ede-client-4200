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
  private REST_API_SERVER = 'http://localhost:8080/ede-product';
  public getAll(page: any, size: any) {
    return this.httpClient.get<any>(
      this.REST_API_SERVER + '/product/getAll?page=' + page + '&size=' + size,
      this.httpOptions
    );
  }
  public deleteById(id: string) {
    return this.httpClient.delete<any>(
      this.REST_API_SERVER + '/product/delete/' + id,
      this.httpOptions
    );
  }
}
