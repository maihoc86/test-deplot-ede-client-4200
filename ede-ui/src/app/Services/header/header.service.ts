import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/models/user.model';
@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private http:HttpClient) { }
  private url = 'http://localhost:8080/ede-customer/getuserlogin';
  public getUserByToken(token:String){
    return this.http.get<User>(`${this.url}/`+token)
  }
  private url2 = 'http://localhost:8080/ede-customer/shop/info';
  public getShopByToken(token:String){
    return this.http.get<any>(`${this.url2}/`+token+'/view')
  }
}
