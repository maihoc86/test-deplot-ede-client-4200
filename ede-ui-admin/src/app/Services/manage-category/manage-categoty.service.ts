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

  public addNewParentCategory(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/parent_category', data, this.httpOptions);
  }

  public addNewParent_child_Category(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/child_parent_category', data, this.httpOptions);
  }
  public addNewchild_Category(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/child_category', data, this.httpOptions);
  }


  public DeleteParent_Category(id:string){
    return this.httpClient.get<any>(this.REST_API_SERVER+'/delete/parent_category/'+id,this.httpOptions);
  }
  public DeleteParent_Child_Category(id:string){
    return this.httpClient.get<any>(this.REST_API_SERVER+'/delete/parent_child_category/'+id,this.httpOptions);
  }
  public DeleteChild_Category(id:string){
    return this.httpClient.get<any>(this.REST_API_SERVER+'/delete/child_category/'+id,this.httpOptions);
  }
  public loadParentCategory() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/parent_category', this.httpOptions);
  }

  public loadParent_Child_Category() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_parent_category', this.httpOptions);
  }

  public load_Child_Category() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_category', this.httpOptions);
  }

  public SearchP(name: string) {
    if (name == "") {
      console.log("null ròi");
      return this.httpClient.get<any>(this.REST_API_SERVER + '/view/parent_category', this.httpOptions);
    }
    return this.httpClient.get<any>(this.REST_API_SERVER + '/search/parent_category/'+name, this.httpOptions);
  }


  public SearchPC(name: string) {
    if (name == "") {
      console.log("null ròi");
      return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_parent_category', this.httpOptions);
    }
    return this.httpClient.get<any>(this.REST_API_SERVER + '/search/parent_child_category/'+name, this.httpOptions);
  }

  public SearchC(name: string) {
    if (name == "") {
      console.log("null ròi");
      return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_category', this.httpOptions);
    }
    return this.httpClient.get<any>(this.REST_API_SERVER + '/search/child_category/'+name, this.httpOptions);
  }




}
