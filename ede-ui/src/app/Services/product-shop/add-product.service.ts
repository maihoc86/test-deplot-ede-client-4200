import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/models/product.model';

@Injectable({
  providedIn: 'root'
})
export class AddProductService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };
  constructor(private httpClient: HttpClient) { }

  private REST_API_SERVER = 'http://localhost:8080/ede-product';

  public addProductShop(data: Product) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop', data, this.httpOptions);
  }
  public getBrand() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/listBrand');
  }
  public getCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/listCategories');
  }
  public getAllProduct() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProduct');
  }
}
