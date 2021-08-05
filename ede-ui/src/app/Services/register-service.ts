import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { User } from '../models/user.model';
@Injectable({
  providedIn: 'root',
})
export class CustomerServiceService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      // Authorization: 'my-auth-token',
      // Authorization: 'Basic ' + btoa('username:password'),
    }),
  };

  private REST_API_SERVER = 'http://localhost:8080/ede-customer/register';
  // private REST_API_ADDRESS = 'http://provinces.open-api.vn/api/p/';

  constructor(private httpClient: HttpClient) {}
  public addUser(data: User) {
    const url = `${this.REST_API_SERVER}`;
    console.log(data);
    return this.httpClient.post<any>(url, data, this.httpOptions);
  }
  // public getApiAddress() {
  //   return this.httpClient.get<any>(this.REST_API_ADDRESS, this.httpOptions);
  // }
}
