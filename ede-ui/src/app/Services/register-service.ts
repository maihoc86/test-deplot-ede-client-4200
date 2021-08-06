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
export class RegisterService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  private REST_API_SERVER = 'http://localhost:8080/ede-customer/register';
  constructor(private httpClient: HttpClient) {}
  public addUser(data: User) {
    const url = `${this.REST_API_SERVER}`;
    console.log(data);
    return this.httpClient.post<any>(url, data, this.httpOptions);
  }
}
