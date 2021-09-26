import { ProductSearchService } from './../Services/product-search/product-search.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../Services/header/header.service';
@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  public HOST_ORIGIN = "http://localhost:8080"

  constructor(
    private activatedRoute: ActivatedRoute,
    private productSearchSrv: ProductSearchService,
    private headerService: HeaderService
  ) { 
    this.activatedRoute.params.subscribe(({idProduct}) => {
      this.productSearchSrv.getProductSearchById(idProduct).subscribe(result => {
        this.product = result
  
        this.loadListProductRelatedShop(result.shop.id,result.childCategory.id);
      })
    })
  }
  public  qty:number=1;
  public product: any = {};
  public cart: Array<any> = [];
  public listProductRelatedShop: any = { };
  public listProductRelatedProduct: any = { };
  public listProductRelatedCategory: any = { };
  ngOnInit(): void {
    
  }
  addToCart(product: any,qty:any) {
    
    var json = localStorage.getItem('cart');
    this.cart = json ? JSON.parse(json) : [];
    var item: any;
    this.cart.forEach((e) => {
      if (e.id == product.optionDef.id) {
        item = e;
      }
    });
    if (item) {
      item.qty+=qty as number;
    } else {
      this.cart.push({
        qty: 1,
        name: product.name,
        id: product.optionDef.id,
        price: product.optionDef.price,
        discount: product.productDiscount[0]?product.productDiscount[0].discount:0,
      });
    }
  
    console.log(this.cart);
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.headerService.myMethod(this.cart);
  }
  public loadListProductRelatedShop(idShop:string,idcate:string){
    this.productSearchSrv.loadListProductRelatedShop(idShop,idcate).subscribe(data=>{
      console.log(data)
      this.listProductRelatedShop=data.content;
    })
  }
  public loadListProductRelatedProduct(id:string){
    
  }
  public loadListProductRelatedCategory(idCategory:string){
    
  }
}
