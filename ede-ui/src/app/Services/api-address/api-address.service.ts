import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { User } from '../../models/user.model';
@Injectable({
  providedIn: 'root'
})
export class ApiAddressService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };
  constructor(private httpClient: HttpClient) { }

  private REST_API_SERVER = 'https://api-location-vn.herokuapp.com/';
  private Rest_API_COUNTRY = 'https://restcountries.eu/rest/v2/all';

  public getCountry() {
    const url = `${this.Rest_API_COUNTRY}`;
    return this.httpClient.get<any>(url, this.httpOptions);
  }

  public getApiCity() {
    const url = `${this.REST_API_SERVER}provinces/`;
    return this.httpClient.get<any>(url, this.httpOptions);
  }

  public getApiDistricts(id: any) {
    const url = `${this.REST_API_SERVER}districts/province/` + id;
    return this.httpClient.get<any>(url, this.httpOptions);
  }


  public getApiWards(id: any) {
    const url = `${this.REST_API_SERVER}wards/district/` + id;
    return this.httpClient.get<any>(url, this.httpOptions);
  }


}
