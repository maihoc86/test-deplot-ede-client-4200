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

  constructor(private httClient: HttpClient) { }
  public loadParentCategory() {
    return this.httClient.get<any>(this.REST_API_SERVER + '/view/parent_category', this.httpOptions);
  }

  public loadParent_Child_Category() {
    return this.httClient.get<any>(this.REST_API_SERVER + '/view/child_parent_category', this.httpOptions);
  }

  public load_Child_Category() {
    return this.httClient.get<any>(this.REST_API_SERVER + '/view/child_category', this.httpOptions);
  }

  public SearchP(name: string) {
    if (name == "") {
      console.log("null ròi");
      return this.httClient.get<any>(this.REST_API_SERVER + '/view/parent_category', this.httpOptions);
    }
    return this.httClient.get<any>(this.REST_API_SERVER + '/search/parent_category/'+name, this.httpOptions);
  }


  public SearchPC(name: string) {
    if (name == "") {
      console.log("null ròi");
      return this.httClient.get<any>(this.REST_API_SERVER + '/view/child_parent_category', this.httpOptions);
    }
    return this.httClient.get<any>(this.REST_API_SERVER + '/search/parent_child_category/'+name, this.httpOptions);
  }

  public SearchC(name: string) {
    if (name == "") {
      console.log("null ròi");
      return this.httClient.get<any>(this.REST_API_SERVER + '/view/child_category', this.httpOptions);
    }
    return this.httClient.get<any>(this.REST_API_SERVER + '/search/child_category/'+name, this.httpOptions);
  }




}
