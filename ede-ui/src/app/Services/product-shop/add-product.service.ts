import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { CookieService } from 'ngx-cookie-service';
import { ProductOptions } from 'src/app/models/product-options.model';
import { ProductOptionsImage } from 'src/app/models/product-options-image.model';
import { ProductTag } from '../../models/product-tag.model';
import { ProductDiscount } from 'src/app/models/product-discount.model';
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
  public addProductDiscount(data: ProductDiscount) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/discount', data, this.httpOptions);
  }
  public addProductOption(data: ProductOptions) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/options/' + data.id_product, data, this.httpOptions);
  }
  public addProductOptionImage(data: ProductOptionsImage) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/options/images', data, this.httpOptions);
  }
  public addProductTags(data: ProductTag) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/create/product-shop/tag', data, this.httpOptions);
  }
  public updateProduct(data: Product) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/update/product-shop/', data, this.httpOptions);
  }
  public updateProductDiscount(data: ProductDiscount) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/update/product-shop/discount', data, this.httpOptions);
  }
  public updateProductOption(data: ProductOptions) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/update/product-shop/options/', data, this.httpOptions);
  }
  public updateProductOptionImage(data: ProductOptionsImage) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/update/product-shop/options/images', data, this.httpOptions);
  }
  public updateProductTag(data: ProductTag) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/update/product-shop/tag', data, this.httpOptions);
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
  public getAllProductOption(page: any) {
    console.log("hihi: "+page)
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProductOption/'+page, this.httpOptions);
  }

  public getChildParentCategoriesByIdParent(idParent: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_child_category/' + idParent);
  }
  public getChildCategoriesByChildParent(idChildParent: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_child_category/' + idChildParent);
  }
  public getProductByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getproductbyid/' + id);
  }
  public getProductOptionByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getproductoption/' + id);
  }
  public getCategoryByidProduct(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getcatrgory/' + id);
  }
  public getParent_Child_CategoryByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getparentchildcatrgory/' + id);
  }
  public getParent_CategoryByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getparentcatrgory/' + id);
  }
  public getTagbyProductid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/gettag/' + id);
  }

  public countProductOrder(id: any) {
    return this.httpClient.get<any>('http://localhost:8080/ede-order/view/countProductOder/' + id);
  }
  public deleteProductByid(id: any) {
    return this.httpClient.delete<any>(`${this.REST_API_SERVER}/product/delete/` + id);
  }
}
