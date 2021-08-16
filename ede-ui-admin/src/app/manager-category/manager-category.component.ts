import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import Swal from 'sweetalert2';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
  FormBuilder,
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
  @ViewChild('parentInputImage_Parent', { static: false })
  InputVarParent!: ElementRef;
  @ViewChild('parentInputImageParent_Child', { static: false })
  InputVarParentChild!: ElementRef;
  urlParent = "assets/images/fancy_upload.png";
  urlParent_child = "assets/images/fancy_upload.png";
  urlChild = "assets/images/fancy_upload.png";
  ImageUrlParent = "";
  ImageUrlParent_Child = "";
  ImageUrlChild = "";
  onselectFileParent(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.ImageUrlParent = e.target.files[0].name;
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlParent = e.target.result;
      }
    }
  }
  onselectFileParent_Child(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.ImageUrlParent_Child = e.target.files[0].name;
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlParent_child = e.target.result;
      }
    }
  }
  reset(formGroup: FormGroup) {
    formGroup.reset();
    this.InputVarParent.nativeElement.value = "";
    this.InputVarParentChild.nativeElement.value = "";

  }
  public listParentFilterOption: any = [];
  public parent = new FormGroup({
    name: new FormControl(''),
    image_url: new FormControl(''),
    is_delete: new FormControl(false),
    is_enable: new FormControl(false),
  });
  public parent_child_category = new FormGroup({
    name: new FormControl(''),
    image_url: new FormControl(''),
    parent_category: new FormControl(),
    is_delete: new FormControl(false),
    is_enable: new FormControl(false),
  });

  constructor(
    private manageCategoryService: ManageCategotyService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.listParentFilterOption;
    this.loadParentCategory();
    this.loadParent_Child_Category();
    this.load_Child_Category();

  }
  private createNewData(formGroup: FormGroup, url: string) {
    const stringReturn: any = {}
    formGroup.controls['image_url'].setValue(url);
    for (const controlName in formGroup.controls) {
      if (controlName) {
        stringReturn[controlName] = formGroup.controls[controlName].value;
      }
    }
    return stringReturn as Parent_Category;
  }
  /*---------------------- Parent ------------------ */
  // thêm mới Parent_Category
  public addNewParentCategory() {
    this.manageCategoryService.addNewParentCategory(this.createNewData(this.parent, this.ImageUrlParent)).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.reset(this.parent);
            this.urlParent = '';
          } else {
            this.reset(this.parent);
            this.urlParent = '';
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
  /******************************* Child Parent **************************** */
  // thêm mới Parent_Category
  public addNewParent_child_Category() {
    console.log(this.parent_child_category);
    console.log(this.parent_child_category.controls['id_parent']);
    this.manageCategoryService.addNewParent_child_Category(this.createNewData(this.parent_child_category, this.ImageUrlParent_Child)).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.reset(this.parent_child_category);
            this.urlParent_child = ''
          } else {
            this.reset(this.parent_child_category);
            this.urlParent_child = ''
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
      this.listParentFilterOption = item;
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



  public itemC: any = [];
  public load_Child_Category() {
    this.manageCategoryService.load_Child_Category().subscribe((data) => {
      const item = data.map(function (obj: {
        id: any;
        name: any;
        id_parent_Child: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }) {
        return obj;
      });
      this.itemC = item;
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
