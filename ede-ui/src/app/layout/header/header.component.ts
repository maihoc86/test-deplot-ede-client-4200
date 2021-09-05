import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute } from '@angular/router';
import { HeaderService } from 'src/app/Services/header/header.service';
import { User } from 'src/app/models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [
    './header.component.css',
	  "../../../assets/css/header/header1.css",
  ]
})
export class HeaderComponent implements OnInit {


  constructor(
    private cookieService:CookieService,
    private headerService:HeaderService,
    private activatedRoute: ActivatedRoute,
    private router:Router
    ) {

    this.u={} as User;
    this.headerService.myMethod$.subscribe(data=>{
      this.cart=data;
      this.loadTotal();
    });
  }

  public txtKeysearch: string = ''

  ngOnInit(): void {
    this.getUserLogin();
    this.activatedRoute.queryParams.subscribe(params => {
      this.params = params
      this.txtKeysearch = params.search
    })
    this.loadCart();
  }
  public totalCart:any=0;
  public cart:Array<any>= [] ;
  public params = {}

  public login:boolean=false;
  public u:User;

  public async getUserLogin(){
   await this.headerService.getUserByToken(this.cookieService.get("auth")).toPromise(
    ).then(data=>{
      console.log(data)
      this.login=true;
      this.u=data;
      
    }).catch(err=>{
      console.log(err)
      this.login=false;
    })
  console.log("545665")
  }

  public async logout(){
    this.router.navigate(['/'])
    this.cookieService.delete("auth")
    document.location.href="";
   
  } 

   loadCart(){
    var json = localStorage.getItem('cart');
   
    this.cart  =json ? JSON.parse(json) : [];
    this.loadTotal();
  }
  public removeItemCart(e:any){
    if(e.qty<=1){
      this.cart.splice(this.cart.findIndex(es=>es.id==e.id),1);
    }else{
      e.qty--;
    }
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.loadTotal();
  }
  loadTotal(){
    this.totalCart=0;
    this.cart.forEach(e=>{
      this.totalCart+=(e.qty*e.price)
    })
  }
}
