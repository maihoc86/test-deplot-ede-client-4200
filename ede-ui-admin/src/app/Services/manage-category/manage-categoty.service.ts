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
import { CookieService } from 'ngx-cookie-service';
@Injectable({
  providedIn: 'root'
})
export class ManageCategotyService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.cookieService.get('auth')
    }),
  };


  private REST_API_SERVER = 'http://localhost:8080/ede-product';
  private REST_API_IMAGE_SERVER = 'http://localhost:8080/ede-file';

  constructor(private httpClient: HttpClient,private cookieService:CookieService) { }

  public addNewParentCategory(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/admin/create/parent_category', data, this.httpOptions);
  }

  public addNewParent_child_Category(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/admin/create/child_parent_category', data, this.httpOptions);
  }
  public addNewchild_Category(data: Parent_Category) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/admin/create/child_category', data, this.httpOptions);
  }

  // GET
  public getParentCategory(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/admin/view/parent_category/' + id, this.httpOptions);
  }
  public getParentChildCategory(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/parent_child_category/' + id, this.httpOptions);
  }
  public getChildCategory(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/child_category/' + id, this.httpOptions);
  }


  public DeleteParent_Category(id:string){
    return this.httpClient.delete<any>(this.REST_API_SERVER+'/admin/delete/parent_category/'+id,this.httpOptions);
  }
  public DeleteParent_Child_Category(id:string){
    return this.httpClient.delete<any>(this.REST_API_SERVER+'/admin/delete/parent_child_category/'+id,this.httpOptions);
  }
  public DeleteChild_Category(id:string){
    return this.httpClient.delete<any>(this.REST_API_SERVER+'/admin/delete/child_category/'+id,this.httpOptions);
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


  //<<<<<<<<<<< update three table category start in here
  public updateParentCategory(parentCategory: Parent_Category, image: any) {
    return this.updateCategory('update/parent-category',parentCategory, image)
  }
  public updateParentChildCategory(parentChildCategory: Parent_Child_Category, image: any) {
    return this.updateCategory('update/parent-child-category',parentChildCategory, image)
  }
  public updateChildCategory(childCategory: Child_Category, image: any) {
    return this.updateCategory('update/child-category',childCategory, image)
  }

  private async updateCategory(url: string, value: any, image: any) {
    if (image) {
      let fd = new FormData()
      fd.append('file', image)
      // {'Content-Type': 'multipart/form-data'}
      await this.httpClient.put<any>(`${this.REST_API_IMAGE_SERVER}/update/binary/${value?.image_url}`, fd).toPromise()
      .then (
        result => {
          console.log(result)
          value.image_url = result
        }
      )
      .catch(
        error => {
          console.log(error)
        }
      )
    }
    return this.httpClient.put<any>(`${this.REST_API_SERVER}/admin/${url}`, value,this.httpOptions).toPromise()
  }
  //>>>>>>>>>>> update three table category end in here

}
