import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/models/user.model';
import { Observable, Subject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  myMethod$: Observable<any>;
  private myMethodSubject = new Subject<any>();


  constructor(private http: HttpClient) { this.myMethod$ = this.myMethodSubject.asObservable(); }
  myMethod(data: any) {
    this.myMethodSubject.next(data);
  }

  public getCart(id:any){
    return this.http.get('http://localhost:8080/ede-customer/getcartitem/'+id);
  }
  public updateCart(data:any,id:any){
    return this.http.put('http://localhost:8080/ede-customer/updatecartitem/'+id,data);
  }
  private url = 'http://localhost:8080/ede-customer/getuserlogin';
  public getUserByToken(token: String) {
    return this.http.get<User>(`${this.url}/` + token)
  }
  private url2 = 'http://localhost:8080/ede-customer/shop/info';
  public getShopByToken(token: String) {
    return this.http.get<any>(`${this.url2}/` + token + '/view')
  }





}
