import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
}
  from '@angular/common/http';
import { Parent_Category } from 'src/app/models/Parent_category.model';
import { Parent_Child_Category } from 'src/app/models/Parent_Child_category.model';
import { Child_Category } from 'src/app/models/Child_category.model';

@Injectable({
  providedIn: 'root'
})
export class ManageCategotyService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };


  private REST_API_SERVER = 'http://localhost:8080/ede-product';

  constructor(private httpClient: HttpClient) { }
  public loadParentCategory() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/parent_category', this.httpOptions);
  }

  public loadParent_Child_Category() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_parent_category', this.httpOptions);
  }

  public load_Child_Category() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_category', this.httpOptions);
  }

  public addNewParentCategory(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/parent_category', data, this.httpOptions);
  }
}
