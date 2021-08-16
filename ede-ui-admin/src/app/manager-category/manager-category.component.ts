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
  url = "assets/images/fancy_upload.png";
  nameImageUrl = "";
  onselectFile(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.nameImageUrl = e.target.files[0].name;
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.url = e.target.result;
      }
    }
  }
  public parent = new FormGroup({
    name: new FormControl(''),
    image_url: new FormControl(''),
    is_delete: new FormControl(false),
    is_enable: new FormControl(false),
  });

  constructor(
    private manageCategoryService: ManageCategotyService,
  ) { }

  ngOnInit(): void {
    this.loadParentCategory();
  }

  // lấy dữ liệu từ FormControl Parent_Category về
  private createNewDataParentCategory() {
    const newParentCategory: any = {};
    this.parent.controls['image_url'].setValue(this.nameImageUrl);
    for (const controlName in this.parent.controls) {
      if (controlName) {
        newParentCategory[controlName] = this.parent.controls[controlName].value;
        console.log(newParentCategory[controlName]);
      }
    }
    return newParentCategory as Parent_Category;
  }
  // thêm mới Parent_Category
  public addNewParentCategory() {
    this.manageCategoryService.addNewParentCategory(this.createNewDataParentCategory()).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.parent.reset()
          } else {
            this.parent.reset()
          }
        })
      },
      (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
  }
  public items: any = [];
  public loadParentCategory() {
    this.manageCategoryService.loadParentCategory().subscribe((data) => {
      const item = data.map(function (obj: {
        id: any;
        name: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }) {
        return obj;
      });
      this.items = item;
    })
  }
  public itemP: any = [];
  public loadParent_Child_Category() {
    this.manageCategoryService.loadParent_Child_Category().subscribe((data) => {
      const item = data.map(function (obj: {
        id: any;
        name: any;
        id_parent: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }) {
        return obj;
      });
      this.itemP = item;
    })
  }

}
