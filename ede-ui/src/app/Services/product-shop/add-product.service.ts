import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { CookieService } from 'ngx-cookie-service';
import { ProductOptions } from 'src/app/models/product-options.model';
import { ProductOptionsImage } from 'src/app/models/product-options-image.model';
import { ProductTag } from '../../models/product-tag.model';
@Injectable({
  providedIn: 'root'
})
export class AddProductService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.cookieService.get('auth')
    }),
  };
  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

  private REST_API_SERVER = 'http://localhost:8080/ede-product';

  public addProductShop(data: Product) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop', data, this.httpOptions);
  }
  public addProductOption(data: ProductOptions) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/options/'+data.id_product,data, this.httpOptions);
  }
  public addProductOptionImage(data: ProductOptionsImage) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/options/images',data, this.httpOptions);
  }
  public addProductTags(data: ProductTag) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/tag',data, this.httpOptions);
  }
  public enableProductShop(id: string) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/enable/product-shop/' + id, this.httpOptions);
  }
  public getBrand() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/listBrand');
  }
  public getParentCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_category');
  }
  public getParentChildCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_child_category');
  }
  public getChildCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_child_category');
  }
  public getAllProduct() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProduct');
  }
  public getAllProductOption() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProductOption');
  }

  public getChildParentCategoriesByIdParent(idParent: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_child_category/' + idParent);
  }
  public getChildCategoriesByChildParent(idChildParent: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_child_category/' + idChildParent);
  }

  public countProductOrder(id: any) {
    console.log("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+id)
    return this.httpClient.get<any>('http://localhost:8080/ede-order/view/countProductOder/' + id);
  }


}
