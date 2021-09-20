import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProductSearchService {

  constructor(
    private httpClient: HttpClient
  ) { }

  private URL_ORIGIN: string = 'http://localhost:8080'

  public getProductByKeySearch(queryParam: any) {
    return (
      this.httpClient
        .get<Response>(`${this.URL_ORIGIN}/ede-product/view/get-products`, {
          params : queryParam
        })
    )
  }

  public getProductSearchById(id: string) {
    return this.httpClient.get<any>(`${this.URL_ORIGIN}/ede-product/view/get-product-search/${id}`)
  }

}
