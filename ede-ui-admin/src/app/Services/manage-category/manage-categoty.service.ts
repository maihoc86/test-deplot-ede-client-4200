import { Injectable } from '@angular/core';
import{
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
}
from '@angular/common/http';
import { Parent_Category } from 'src/app/models/Parent_category.module';
import { Parent_Child_Category } from 'src/app/models/Parent_Child_category.module';
import { Child_Category } from 'src/app/models/Child_category.module';

@Injectable({
  providedIn: 'root'
})
export class ManageCategotyService {
  private httpOptions ={
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };


  private REST_API_SERVER ='http://localhost:8080/ede-product';

  constructor(private httClient: HttpClient) { }
  public loadParentCategory(){
    return this.httClient.get<any>(this.REST_API_SERVER+'/view/parent_category', this.httpOptions);
  }

  public loadParent_Child_Category(){
    return this.httClient.get<any>(this.REST_API_SERVER+'/view/child_parent_category', this.httpOptions);
  }

  public load_Child_Category(){
    return this.httClient.get<any>(this.REST_API_SERVER+'/view/child_category', this.httpOptions);
  }

  public DeleteParent_Child_Category(id:string){
    return this.httClient.get<any>(this.REST_API_SERVER+'/delete/child_parent_category/'+id,this.httpOptions);
  }
}
