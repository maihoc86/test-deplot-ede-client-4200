import { Component, OnInit } from '@angular/core';
import { ManagerShopService } from '../Services/manager-shop/manager-shop.service';
@Component({
  selector: 'app-manager-shop',
  templateUrl: './manager-shop.component.html',
  styleUrls: ['./manager-shop.component.css']
})
export class ManagerShopComponent implements OnInit {

  constructor(private shopService:ManagerShopService) { }

  ngOnInit(): void {
    this.loadAllShop()
  }
  
public p: number = 1;
  public items:any={}
  public loadAllShop(){
    this.shopService.loadAllShop().subscribe(data=>{
      console.log(data)
      this.items=data;
    },err=>{
      console.log(err)
    })
  }

  public Search(name:string){
    this.shopService.loadAllShopByName(name).subscribe(data=>{
      this.items=data;
    },err=>{
      console.log(err)
    })
  }
}
