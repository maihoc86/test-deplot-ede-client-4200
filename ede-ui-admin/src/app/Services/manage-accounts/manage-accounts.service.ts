import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { User } from 'src/app/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class ManageAccountsService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  private REST_API_SERVER = 'http://localhost:8080/ede-customer/admin';
  constructor(private httpClient: HttpClient) { }
  public addNewUser(data: User) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/admin/add-new-user', data, this.httpOptions);
  }

  async sendEmail(email: string) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/send-email-verify', { email: email }).toPromise()
  }



  public loadUser() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/admin/users', this.httpOptions);
  }

  public SearchUser(username:string){
    if(username==""){
      console.log("null ròi");
      return this.httpClient.get<any>(this.REST_API_SERVER + '/admin/users', this.httpOptions);
    }
      return this.httpClient.get<any>(this.REST_API_SERVER + '/admin/search/'+username, this.httpOptions);


  }


  public deleteUser(username:string) {
    return this.httpClient.delete<any>(this.REST_API_SERVER + '/delete/users/'+username, this.httpOptions);
  }

  public updateUser(user: User) {
    return this.httpClient.post<any>(`${this.REST_API_SERVER}/admin/update-customer`, user, this.httpOptions);
  }

}
