import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class AccountActiveService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    }),
  }; constructor(private httpClient: HttpClient) { }

  private REST_API_SERVER = 'http://localhost:8080/ede-customer';
 
  public checkActive(email: string, token: string) {
    var body = 'email='+email+'&token='+token;
    return this.httpClient.post<any>(this.REST_API_SERVER + '/account/verify', body, this.httpOptions);
  }
}
