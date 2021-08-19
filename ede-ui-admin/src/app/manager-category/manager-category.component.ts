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
  @ViewChild('parentInputImage_Child', { static: false })
  InputVarChild!: ElementRef;
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
  onselectFile_Child(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.ImageUrlChild = e.target.files[0].name;
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlChild = e.target.result;
      }
    }
  }

  public listParentFilterOption: any = [];
  public listParent_Child_FilterOption: any = [];

  public parent = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,128}[ \\-\\']{0,}){1,128}$"),
    ]),
    image_url: new FormControl(''),
    is_delete: new FormControl(false),
    is_enable: new FormControl('true'),
  });
  public parent_child_category = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,128}[ \\-\\']{0,}){1,128}$"),
    ]),
    image_url: new FormControl(''),
    parent_category: new FormControl('', Validators.required),
    is_delete: new FormControl(false),
    is_enable: new FormControl('true'),
  });
  public child_category = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,128}[ \\-\\']{0,}){1,128}$"),
    ]),
    image_url: new FormControl(''),
    child_parentCategory: new FormControl('', Validators.required),
    is_delete: new FormControl(false),
    is_enable: new FormControl('true'),
  });
  reset(formGroup: FormGroup) {
    formGroup.reset();
    formGroup.controls['is_delete'].setValue(false)
    formGroup.controls['is_enable'].setValue('true')
    this.ImageUrlParent = "";
    this.ImageUrlParent_Child = "";
    this.ImageUrlChild = "";
    this.InputVarParent.nativeElement.value = "";
    this.InputVarParentChild.nativeElement.value = "";
    this.InputVarChild.nativeElement.value = "";
    this.idParentCategory = ''
    this.idParentChildCategory = ''
    this.idChildCategory = ''
  }

  constructor(
    private manageCategoryService: ManageCategotyService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.listParentFilterOption;
    this.listParent_Child_FilterOption;
    this.loadParentCategory();
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
    console.log(this.parent);
    console.log(this.parent.controls['is_delete']);
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
            this.urlParent = "assets/images/fancy_upload.png";
          } else {
            this.reset(this.parent);
            this.urlParent = "assets/images/fancy_upload.png";
          }
        })
      },
      (err) => {
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: err.error.message,
        });
      }
    );
  }
  /******************************* Child Parent **************************** */
  // thêm mới Parent_Child_Category
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
            this.urlParent_child = "assets/images/fancy_upload.png";
          } else {
            this.reset(this.parent_child_category);
            this.urlParent_child = "assets/images/fancy_upload.png";
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
  /******************************* Child **************************** */
  // thêm mới Child_Category
  public addNew_child_Category() {
    console.log(this.child_category);
    console.log(this.child_category.controls['child_parentCategory']);
    this.manageCategoryService.addNewchild_Category(this.createNewData(this.child_category, this.ImageUrlChild)).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.reset(this.child_category);
            this.urlChild = "assets/images/fancy_upload.png";
          } else {
            this.reset(this.child_category);
            this.urlChild = "assets/images/fancy_upload.png";
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
      }) {
        return obj;
      });
      this.listParentFilterOption = item;
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
      }) {
        return obj;
      });
      this.listParent_Child_FilterOption = item;
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
      }) {
        return obj;
      });
      this.itemC = item;
    })
  }


  public DeleteParent_Category(id: string) {

    this.manageCategoryService.DeleteParent_Category(id).subscribe(data => {
      Swal.fire({
        icon: 'success',
        title: 'Xóa danh mục ',
        text: 'Xóa thành công'
      }).then(respone => {
        this.loadParentCategory();
      })

    }, (err) => {
      console.log(err)
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: err.error,
      });
    })
  }


  public DeleteParent_Child_Category(id: string) {

    this.manageCategoryService.DeleteParent_Child_Category(id).subscribe(data => {
      Swal.fire({
        icon: 'success',
        title: 'Xóa danh mục ',
        text: 'Xóa thành công'
      }).then(respone => {
        this.loadParent_Child_Category();
      })

    }, (err) => {
      console.log(err)
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: err.error,
      });
    })
  }

  
  public DeleteChild_Category(id: string) {

    this.manageCategoryService.DeleteChild_Category(id).subscribe(data => {
      Swal.fire({
        icon: 'success',
        title: 'Xóa danh mục ',
        text: 'Xóa thành công'
      }).then(respone => {
        this.load_Child_Category();
      })

    }, (err) => {
      console.log(err)
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: err.error,
      });
    })
  }

  public editP(e: any) {
    const newP: any = {};
    for (const controlName in this.parent.controls) {
      if (controlName) {
        this.parent.controls[controlName].setValue(e[controlName])
      } 
    
    }
    if(e['is_enable']){
      this.parent.controls['is_enable'].setValue('true')
    }else{
      this.parent.controls['is_enable'].setValue('false')
    }
    this.idParentCategory = e.id
    return newP as Parent_Category;
  }
  public editPC(e: any) {
    const newP: any = {};
    for (const controlName in this.parent_child_category.controls) {
      if (controlName) {
        this.parent_child_category.controls[controlName].setValue(e[controlName])
      } 
    
    }
    if(e['is_enable']){
      this.parent_child_category.controls['is_enable'].setValue('true')
    }else{
      this.parent_child_category.controls['is_enable'].setValue('false')
    }
    this.idParentChildCategory = e.id
    return newP as Parent_Child_Category;
  }
  public editC(e: any) {
    const newP: any = {};
    for (const controlName in this.child_category.controls) {
      if (controlName) {
        this.child_category.controls[controlName].setValue(e[controlName])
      } 
    
    }
    if(e['is_enable']){
      this.child_category.controls['is_enable'].setValue('true')
    }else{
      this.child_category.controls['is_enable'].setValue('false')
    }
    this.idChildCategory = e.id
    return newP as Child_Category;
  }
  public SearchP(tem:string){
    this.manageCategoryService.SearchP(tem).subscribe((data:any) =>{
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
    this.manageCategoryService.SearchPC(tem).subscribe((data:any) =>{
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
    this.manageCategoryService.SearchC(tem).subscribe((data:any) =>{
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


  //<<<<<<<<<<<<<<<<<<<<<<<< update start in here: vinh
  public idParentCategory: string = ''
  public idParentChildCategory: string = ''
  public idChildCategory: string = ''

  public updateParentCategory() {
    this.parent.controls['image_url'].setValue(this.ImageUrlParent)
    let categoryUpdate: Parent_Category = this.coverFormGroupToObject<Parent_Category>(this.parent)
    categoryUpdate.id = this.idParentCategory

    this.manageCategoryService.updateParentCategory(categoryUpdate).subscribe(
      response => {
        console.log(response)
        this.loadParentCategory();
      },
      error => {
        console.log(error)
      }
    )
  }
  public updateParentChildCategory() {
    this.parent_child_category.controls['image_url'].setValue(this.ImageUrlParent_Child)
    let categoryUpdate: Parent_Child_Category = this.coverFormGroupToObject<Parent_Child_Category>(this.parent_child_category)
    categoryUpdate.id = this.idParentChildCategory

    this.manageCategoryService.updateParentChildCategory(categoryUpdate).subscribe(
      response => {
        console.log(response)
        this.loadParent_Child_Category();
      },
      error => {
        console.log(error)
      }
    )
  }
  public updateChildCategory() {
    this.child_category.controls['image_url'].setValue(this.ImageUrlChild)
    let categoryUpdate: Child_Category = this.coverFormGroupToObject<Child_Category>(this.child_category)
    categoryUpdate.id = this.idChildCategory

    this.manageCategoryService.updateChildCategory(categoryUpdate).subscribe (
      response => {
        console.log(response)
        this.load_Child_Category();
      },
      error => {
        console.log(error)
      }
    )
  }
  private coverFormGroupToObject<T>(formGroup: FormGroup) {
    let obj :any = {}
    for (const controlName in formGroup.controls) {
      if (controlName) {
        obj[controlName] = formGroup.controls[controlName].value
      }
    }
    return obj as T
  }
  //>>>>>>>>>>>>>>>>>>>>>>>>  update end in here: vinh

}
