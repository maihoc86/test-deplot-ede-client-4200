import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';



import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Parent_Category } from '../models/Parent_category.module';
import { Parent_Child_Category } from '../models/Parent_Child_category.module';
import { Child_Category } from '../models/Child_category.module';
import { ManageCategotyService } from '../Services/manage-category/manage-categoty.service';
@Component({
  selector: 'app-manager-category',
  templateUrl: './manager-category.component.html',
  styleUrls: ['./manager-category.component.css']
})
export class ManagerCategoryComponent implements OnInit {

  constructor(
    private manageCategoryService: ManageCategotyService,

  ) { }

  ngOnInit(): void {
    this.loadParentCategory();
  }


  public items:any=[];
  public loadParentCategory(){
    this.manageCategoryService.loadParentCategory().subscribe((data) =>{
      const item = data.map(function(obj: {
        id: any;
        name: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }){
        return obj;
      });
      this.items = item;
    })
  }


  public itemP:any=[];
  public loadParent_Child_Category(){
    this.manageCategoryService.loadParent_Child_Category().subscribe((data) =>{
      const item = data.map(function(obj: {
        id: any;
        name: any;
        id_parent: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }){
        return obj;
      });
      this.itemP = item;
    })
  }

  public DeleteParent_Child_Category(id:string){

    this.manageCategoryService.DeleteParent_Child_Category(id).subscribe(data=>{
      Swal.fire({
        icon:'success',
        title:'Xóa tài khoản',
        text: 'Xóa thành công'
      }).then(respone=>{
        this.loadParentCategory();
      })
  
    } ,(err) => {
      console.log(err)
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: err.error,
      });
    })
  }

}
