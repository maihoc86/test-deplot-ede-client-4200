import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class ManagerShopService {

  constructor(private http:HttpClient) { }
  private REST_API_SERVER = 'http://localhost:8080/ede-customer';
  public loadAllShop(){
    return this.http.get<any>(this.REST_API_SERVER+'/shop/viewall')
  }

  public loadAllShopByName(name:string){
    return this.http.get<any>(this.REST_API_SERVER+'/shop/viewallbyname?name='+name)
  }
}
