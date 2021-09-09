import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class OrderShopService {


  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.cookieService.get('auth')
    }),
  };

  private REST_API_SERVER = 'http://localhost:8080/ede-order';

  public getOrderShop(status:string,page:number,size:number) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/order/shop/getAll?page='+page+"&size="+size+"&status="+status, this.httpOptions);
  }
  public getOrderDetailShop(id:string,page: number, size: number) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/orderDetail/shop/getAll/'+id+"?page="+page+"&size="+size, this.httpOptions);
  }

}
