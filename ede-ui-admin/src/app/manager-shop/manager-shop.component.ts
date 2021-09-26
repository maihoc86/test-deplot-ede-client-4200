import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
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

  public UpdateStatusShop(id: string, status: any){

    if(status){
      Swal.fire({
        title:'Thông báo!',
        text: 'Bạn có muốn tạm khoá shop?',
        icon: 'question',
        showConfirmButton: true,
        showCancelButton: true,
        confirmButtonText: 'Khoá',
        cancelButtonText: 'Huỷ'

      }).then(
        (rs) =>{
          if(rs.isConfirmed){
            this.shopService.updateStatusShop(id, !status).subscribe(data => {
              this.items = data;
              this.loadAllShop();
            }, err => {
              console.log(err);
            });
          }
        }
      )
    }else{
      Swal.fire({
        title: 'Thông báo!',
        text: 'Bạn có muốn kích hoạt shop?',
        icon: 'question',
        showConfirmButton: true,
        showCancelButton: true,
        confirmButtonText: 'Kích hoạt',
        cancelButtonText: 'Huỷ'

      }).then(
        (rs) => {
          if (rs.isConfirmed) {
            this.shopService.updateStatusShop(id, !status).subscribe(data => {
              this.items = data;
              this.loadAllShop();
            }, err => {
              console.log(err);
            });
          }
        }
      )
    }


  }


}
