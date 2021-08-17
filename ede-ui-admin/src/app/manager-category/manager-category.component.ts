import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';



import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Parent_Category } from '../models/Parent_category.model';
import { Parent_Child_Category } from '../models/Parent_Child_category.model';
import { Child_Category } from '../models/Child_category.model';
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
    // this.loadParent_Child_Category();
    // this.load_Child_Category();

  }


  public p: number = 1;
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

  public pc: number = 1;
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


  public c: number = 1;
  public itemC:any=[];
  public load_Child_Category(){
    this.manageCategoryService.load_Child_Category().subscribe((data) =>{
      const item = data.map(function(obj: {
        id: any;
        name: any;
        id_parent_Child: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }){
        return obj;
      });
      this.itemC = item;
    })
  }


  public SearchP(tem:string){
    this.manageCategoryService.SearchP(tem).subscribe((data) =>{
      const itemP = data.map(function(obj: {
        id: any;
        name: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }){
        return obj;
      });
      this.items = itemP;
    })
  }


  public SearchPC(tem:string){
    this.manageCategoryService.SearchPC(tem).subscribe((data) =>{
      const it = data.map(function(obj: {
        id: any;
        name: any;
        id_parent: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }){
        return obj;
      });
      this.itemP = it;
    })
  }


  public SearchC(tem:string){
    this.manageCategoryService.SearchC(tem).subscribe((data) =>{
      const itema = data.map(function(obj: {
        id: any;
        name: any;
        id_parent_Child: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }){
        return obj;
      });
      this.itemC = itema;
    })
  }







}
