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

  private REST_API_SERVER = 'https://poly-java6-4066b-default-rtdb.firebaseio.com/';

  constructor(private httpClient: HttpClient) {}
  public addUser(data: User) {
    const url = `${this.REST_API_SERVER}/user.json`;
    console.log(data);
    return this.httpClient.post<any>(url, data, this.httpOptions);
  }
}
