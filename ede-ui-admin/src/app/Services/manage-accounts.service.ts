import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { User } from '../models/user.model';
@Injectable({
  providedIn: 'root'
})
export class ManageAccountsService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  private REST_API_SERVER = 'http://localhost:8080/ede-customer';
  constructor(private httpClient: HttpClient) { }
  public addNewUser(data: User) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/admin/add-new-user', data, this.httpOptions);
  }
}
