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

  private REST_API_SERVER = 'http://localhost:8080/ede-customer';
  constructor(private httpClient: HttpClient) { }
  public registerAccount(data: User) {
    return this.sendData(data, this.REST_API_SERVER + '/register');
  }

  public createAccountByAdmin(data: User) {
    return this.sendData(data, this.REST_API_SERVER + '/create-account-by-admin');
  }

  public sendData(data: User, url: string) {
    return this.httpClient.post<any>(url, data, this.httpOptions);
  }
}
