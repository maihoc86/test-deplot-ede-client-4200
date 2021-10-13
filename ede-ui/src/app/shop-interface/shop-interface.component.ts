import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HeaderService } from '../Services/header/header.service';
import { CookieService } from 'ngx-cookie-service';
import { MyShopService } from '../Services/my-shop/my-shop.service';
@Component({
  selector: 'app-shop-interface',
  templateUrl: './shop-interface.component.html',
  styleUrls: ['./shop-interface.component.css']
})
export class ShopInterfaceComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private router: Router,
    private shopService:MyShopService,
    private headerService: HeaderService,private cookieService:CookieService) { }

  ngOnInit(): void {
    this.loadInterfaceShop(this.getParam());
  }
  public shopInfor: any = {};
  public array: any = [7, 6, 5, 4, 3];
  public getParam() {
    var id = '';
    this.route.params.subscribe((params) => {
      id = params['id'];
    });
    return id;
  }
  loadInterfaceShop(idShop:string) {
    this.shopService.getShopInfo(idShop).subscribe((data) => {
      this.shopInfor=data;
      console.log(data)
    },err=>{
      this.router.navigate([''])
    });
  }
  public ToAllProductInterfaceShop(){
    document.location.href='/shop/showall/interface?idShop='+this.getParam();
  }
}
