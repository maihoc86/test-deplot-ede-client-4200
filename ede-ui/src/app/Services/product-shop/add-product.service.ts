import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { CookieService } from 'ngx-cookie-service';
import { ProductOptions } from 'src/app/models/product-options.model';
import { ProductOptionsImage } from 'src/app/models/product-options-image.model';
import { ProductTag } from '../../models/product-tag.model';
import { ProductDiscount } from 'src/app/models/product-discount.model';
import { Observable } from 'rxjs';
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
    return this.httpClient.post<any>(this.REST_API_SERVER + '/user/create/product-shop', data, this.httpOptions);
  }
  public addProductDiscount(data: ProductDiscount) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/user/create/product-shop/discount', data, this.httpOptions);
  }
  public addProductOption(data: ProductOptions) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/user/create/product-shop/options/', data, this.httpOptions);
  }
  public addProductOptionImage(data: ProductOptionsImage) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/user/create/product-shop/options/images', data, this.httpOptions);
  }
  public addProductTags(data: ProductTag) {
    return this.httpClient.post<any>(this.REST_API_SERVER + '/user/create/product-shop/tag', data, this.httpOptions);
  }
  public updateProduct(data: Product) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/user/update/product-shop/', data, this.httpOptions);
  }
  public updateProductDiscount(data: ProductDiscount) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/user/update/product-shop/discount', data, this.httpOptions);
  }
  public updateProductOption(data: ProductOptions) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/user/update/product-shop/options/', data, this.httpOptions);
  }
  public updateProductOptionImage(data: ProductOptionsImage) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/user/update/product-shop/options/images', data, this.httpOptions);
  }
  public updateProductTag(data: ProductTag) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/user/update/product-shop/tag', data, this.httpOptions);
  }
  public enableProductShop(data: Product) {
    return this.httpClient.put<any>(this.REST_API_SERVER + '/user/enable/product-shop/', data, this.httpOptions);
  }
  public getBrand() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/listBrand', this.httpOptions);
  }
  public getParentCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_category', this.httpOptions);
  }
  public getParentChildCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_child_category', this.httpOptions);
  }
  public getChildCategories() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_child_category', this.httpOptions);
  }
  public getChildCategoriesShop() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/customer/shop/all/category', this.httpOptions);
  }

  public getAllProductShopByCustomer(page: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/customer/shop/all/product?page=' + page, this.httpOptions);
  }
  public getAllProductDiscountShopByCustomer() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/customer/shop/all/product/discount', this.httpOptions);
  }


  public getAllProduct() {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProduct', this.httpOptions);
  }

  public getAllProductOption(keyword: any, page: any, size: any) {
    console.log("getAllProductOption: " + page)
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProductOption?keyword=' + keyword + "&page=" + page + "&size=" + size, this.httpOptions);
  }

  public getAllProductByEnable(keyword: any, value: boolean, page: any, size: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProductOption/enable?keyword=' + keyword + "&value=" + value + "&page=" + page + "&size=" + size, this.httpOptions);
  }

  public getAllProductByQty0(keyword: any, page: any, size: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getAllProductOption/quantity0?keyword=' + keyword + "&page=" + page + "&size=" + size, this.httpOptions);
  }

  public getChildParentCategoriesByIdParent(idParent: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_parent_child_category/' + idParent, this.httpOptions);
  }
  public getChildCategoriesByChildParent(idChildParent: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/list_child_category/' + idChildParent, this.httpOptions);
  }
  public getProductByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getproductbyid/' + id, this.httpOptions);
  }
  public getProductOptionByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getproductoption/' + id, this.httpOptions);
  }
  public getProductOptionImageByIdOption(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getproductoptionimage/' + id, this.httpOptions);
  }
  public getCategoryByidProduct(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getcatrgory/' + id, this.httpOptions);
  }
  public getParent_Child_CategoryByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getparentchildcatrgory/' + id, this.httpOptions);
  }
  public getParent_CategoryByid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/getparentcatrgory/' + id, this.httpOptions);
  }
  public getTagbyProductid(id: string) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/gettag/' + id, this.httpOptions);
  }
  public countProductOrder(id: any) {
    return this.httpClient.get<any>('http://localhost:8080/ede-order/view/countProductOder/' + id, this.httpOptions);
  }
  public deleteProductByid(id: any) {
    return this.httpClient.delete<any>(`${this.REST_API_SERVER}/user/product/delete/` + id, this.httpOptions);
  }

  public getAllProductShowInterface(page: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/customer/shop/all/product?page=' + page, this.httpOptions);
  }

  public getAllProductShowInterfaceFilter(category: any,location: any, brand: any, page: any) {
    return this.httpClient.get<any>(this.REST_API_SERVER + '/view/customer/shop/all/product/filter?page=' + page + '&category=' + category + '&location=' + location + '&brand='+ brand, this.httpOptions);
  }
  // public getAllProductShowInterfaceFilterByLocation(location: any, page: any) {
  //   return this.httpClient.get<any>(this.REST_API_SERVER + '/view/customer/shop/all/product/filterLocation?page=' + page + '&location=' + location, this.httpOptions);
  // }

}
