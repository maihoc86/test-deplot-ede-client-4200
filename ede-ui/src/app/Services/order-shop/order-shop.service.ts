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

  public getOrderShop( keyword: string ,status:string,page:number,size:number) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/order/shop/getAll?keyword='+keyword+"&page="+page+"&size="+size+"&status="+status, this.httpOptions);
  }
  public getOrderDetailShop(id:string, keyword: string ,page: number, size: number) {
    console.log("vào đc getOrderDetailShop ")
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/orderDetail/shop/getAll/'+id+"?keyword="+keyword+"&page="+page+"&size="+size, this.httpOptions);
  }
  public getAllOrderShop() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/order/getAll', this.httpOptions);
  }
  public getAllOrderUser(id: string){
    return this.httpClient.get<any>(this.REST_API_SERVER+'/view/order/user/'+id,this.httpOptions);
  }
}
