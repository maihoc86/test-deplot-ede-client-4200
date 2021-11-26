import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import Swal from 'sweetalert2';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Parent_Category } from '../models/Parent_category.model';
import { Parent_Child_Category } from '../models/Parent_Child_category.model';
import { Child_Category } from '../models/Child_category.model';
import { ManageCategotyService } from '../Services/manage-category/manage-categoty.service';
import { ImagesService } from '../Services/images/images.service';
import { Observable } from 'rxjs/internal/Observable';
@Component({
  selector: 'app-manager-category',
  templateUrl: './manager-category.component.html',
  styleUrls: ['./manager-category.component.css'],
})
export class ManagerCategoryComponent implements OnInit {
  @ViewChild('parentInputImage_Parent', { static: false })
  InputVarParent!: ElementRef;
  @ViewChild('parentInputImageParent_Child', { static: false })
  InputVarParentChild!: ElementRef;
  @ViewChild('parentInputImage_Child', { static: false })
  InputVarChild!: ElementRef;
  urlParent = '';
  urlParent_child = '';
  urlChild = '';
  imageParent = '';
  imageParent_Child = '';
  imageChild = '';
  public images: File[] = [];
  public HOST_EDE_FILE = 'http://localhost:8080/ede-file'
  onselectFileParent(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.imageParent = e.target.files[0];
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlParent = e.target.result;
      };
    }
  }
  onselectFileParent_Child(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.imageParent_Child = e.target.files[0];
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlParent_child = e.target.result;
      };
    }
  }
  onselectFile_Child(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.imageChild = e.target.files[0];
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlChild = e.target.result;
      };
    }
  }
  public listParentFilterOption: any = [];
  public listParent_Child_FilterOption: any = [];
  public parent = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,128}[ \\-\\']{0,}){1,128}$"
      ),
    ]),
    image_url: new FormControl(''),
    is_delete: new FormControl(false),
    is_enable: new FormControl('true'),
  });
  public parent_child_category = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,128}[ \\-\\']{0,}){1,128}$"
      ),
    ]),
    image_url: new FormControl(''),
    parentcategory: new FormControl(null, Validators.required),
    is_delete: new FormControl(false),
    is_enable: new FormControl('true'),
  });
  public child_category = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,128}[ \\-\\']{0,}){1,128}$"
      ),
    ]),
    image_url: new FormControl(''),
    parentcategory: new FormControl(null, Validators.required),
    is_delete: new FormControl(false),
    is_enable: new FormControl('true'),
  });
  reset(formGroup: FormGroup) {
    formGroup.reset();
    formGroup.controls['is_delete'].setValue(false)
    formGroup.controls['is_enable'].setValue('true')
    this.imageParent = "";
    this.urlParent = ''
    this.imageParent_Child = "";
    this.urlParent_child = ''
    this.imageChild = "";
    this.urlChild = ''
    this.InputVarParent.nativeElement.value = "";
    this.InputVarParentChild.nativeElement.value = "";
    this.InputVarChild.nativeElement.value = "";
    this.idParentCategory = ''
    this.idParentChildCategory = ''
    this.idChildCategory = ''
  }

  constructor(
    private manageCategoryService: ManageCategotyService,
    private fb: FormBuilder,
    private imageService: ImagesService
  ) {}

  ngOnInit(): void {
    this.listParentFilterOption;
    this.listParent_Child_FilterOption;
    this.loadParentCategory();
    this.loadParent_Child_Category();
    this.load_Child_Category();
  }
  private createNewData(formGroup: FormGroup) {
    const stringReturn: any = {};
    for (const controlName in formGroup.controls) {
      if (controlName) {
        stringReturn[controlName] = formGroup.controls[controlName].value;
      }
    }
    return stringReturn as Parent_Category;
  }
  private createNewDataChildParent(formGroup: FormGroup) {
    const stringReturn: any = {};
    for (const controlName in formGroup.controls) {
      if (controlName) {
        stringReturn[controlName] = formGroup.controls[controlName].value;
      }
    }
    return stringReturn as Parent_Child_Category;
  }
  private createNewDataChild(formGroup: FormGroup) {
    const stringReturn: any = {};
    for (const controlName in formGroup.controls) {
      if (controlName) {
        stringReturn[controlName] = formGroup.controls[controlName].value;
      }
    }
    return stringReturn as Child_Category;
  }
  /*---------------------- Parent ------------------ */
  // thêm mới Parent_Category
  public addNewParentCategory() {
    this.addImage(this.parent, 'Parent').subscribe((data) => {
      if (data == true) {
        this.manageCategoryService
          .addNewParentCategory(this.createNewData(this.parent))
          .subscribe(
            (data) => {
              Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: 'Thêm loại danh mục thành công',
                confirmButtonText: `OK`,
              }).then((result) => {
                if (result.isConfirmed) {
                  this.reset(this.parent);
                  this.urlParent = 'assets/images/fancy_upload.png';
                } else {
                  this.reset(this.parent);
                  this.urlParent = 'assets/images/fancy_upload.png';
                }
              });
            },
            (err) => {
              console.log(err);
              this.erros(err);
            }
          );
      }
    });
  }
  /******************************* Child Parent **************************** */
  // thêm mới Parent_Child_Category
  public addNewParent_child_Category() {
    this.addImage(this.parent_child_category, 'Parent_Child').subscribe(
      (data) => {
        this.manageCategoryService
          .getParentCategory(
            this.parent_child_category.controls['parentcategory'].value
          )
          .subscribe((data) => {
            this.parent_child_category.controls['parentcategory'].setValue(
              data
            );
            this.manageCategoryService
              .addNewParent_child_Category(
                this.createNewDataChildParent(this.parent_child_category)
              )
              .subscribe(
                (data) => {
                  Swal.fire({
                    icon: 'success',
                    title: 'Thành công!',
                    text: 'Thêm loại danh mục thành công',
                    confirmButtonText: `OK`,
                  }).then((result) => {
                    if (result.isConfirmed) {
                      this.reset(this.parent_child_category);
                      this.urlParent_child = 'assets/images/fancy_upload.png';
                    } else {
                      this.reset(this.parent_child_category);
                      this.urlParent_child = 'assets/images/fancy_upload.png';
                    }
                  });
                },
                (err) => {
                  this.erros(err);
                }
              );
          });
      }
    );
  }
  /******************************* Child **************************** */
  // thêm mới Child_Category
  public addNew_child_Category() {
    this.addImage(this.child_category, 'Child').subscribe((data) => {
      this.manageCategoryService
        .getParentChildCategory(
          this.child_category.controls['parentcategory'].value
        )
        .subscribe((data) => {
          this.child_category.controls['parentcategory'].setValue(data);
          this.manageCategoryService
            .addNewchild_Category(this.createNewData(this.child_category))
            .subscribe(
              (data) => {
                Swal.fire({
                  icon: 'success',
                  title: 'Thành công!',
                  text: 'Thêm loại danh mục thành công',
                  confirmButtonText: `OK`,
                }).then((result) => {
                  if (result.isConfirmed) {
                    this.reset(this.child_category);
                    this.urlChild = 'assets/images/fancy_upload.png';
                  } else {
                    this.reset(this.child_category);
                    this.urlChild = 'assets/images/fancy_upload.png';
                  }
                });
              },
              (err) => {
                this.erros(err);
              }
            );
        });
    });
  }

  addImage(form: FormGroup, nameFormGroup: string): Observable<boolean> {
    console.log(nameFormGroup);
    const formData = new FormData();

    nameFormGroup == 'Parent'
      ? formData.append('file', this.imageParent)
      : nameFormGroup == 'Parent_Child'
      ? formData.append('file', this.imageParent_Child)
      : formData.append('file', this.imageChild);

    return this.imageService.createImageCategory(formData).pipe(
      switchMap((response) => {
        form.patchValue({
          image_url: response.text,
        });
        return of(true);
      })
    );
  }

  public p: number = 1;
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
    });
  }

  public pc: number = 1;
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
      this.listParent_Child_FilterOption = item;
      this.itemP = item;
    });
  }
  public c: number = 1;
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
    });
  }

  public DeleteParent_Category(id: string) {
    this.manageCategoryService.DeleteParent_Category(id).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Xóa danh mục ',
          text: 'Xóa thành công',
        }).then((respone) => {
          this.loadParentCategory();
          this.reset(this.parent);
        });
      },
      (err) => {
        console.log(err);
        this.erros(err);
      }
    );
  }

  public DeleteParent_Child_Category(id: string) {
    this.manageCategoryService.DeleteParent_Child_Category(id).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Xóa danh mục ',
          text: 'Xóa thành công',
        }).then((respone) => {
          this.loadParent_Child_Category();
          this.reset(this.parent_child_category);
        });
      },
      (err) => {
        console.log(err);
        this.erros(err);
      }
    );
  }

  public DeleteChild_Category(id: string) {
    this.manageCategoryService.DeleteChild_Category(id).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Xóa danh mục ',
          text: 'Xóa thành công',
        }).then((respone) => {
          this.load_Child_Category();
          this.reset(this.child_category);
        });
      },
      (err) => {
        console.log(err);
        this.erros(err);
      }
    );
  }

  public editP(e: any) {
    const newP: any = {};
    for (const controlName in this.parent.controls) {
      if (controlName) {
        this.parent.controls[controlName].setValue(e[controlName]);
      }
    }
    if (e['is_enable']) {
      this.parent.controls['is_enable'].setValue('true');
    } else {
      this.parent.controls['is_enable'].setValue('false');
    }
    this.idParentCategory = e.id
    this.urlParent = `${this.HOST_EDE_FILE}/get/image/${e.image_url}`
    this.imageOfP = this.urlParent
    return newP as Parent_Category;
  }
  public editPC(e: any) {
    const newP: any = {};

    for (const controlName in this.parent_child_category.controls) {
      if (controlName) {
        this.parent_child_category.controls[controlName].setValue(
          e[controlName]
        );
      }
    }
    if (e['parentcategory']) {
      console.log(e);
      this.parent_child_category.controls['parentcategory'].setValue(
        e['parentcategory'].id
      );
    }
    if (e['is_enable']) {
      this.parent_child_category.controls['is_enable'].setValue('true');
    } else {
      this.parent_child_category.controls['is_enable'].setValue('false');
    }
    this.idParentChildCategory = e.id
    this.urlParent_child = `${this.HOST_EDE_FILE}/get/image/${e.image_url}`
    this.imageOfPC = this.urlParent_child
    return newP as Parent_Child_Category;
  }
  public editC(e: any) {
    const newP: any = {};
    for (const controlName in this.child_category.controls) {
      if (controlName) {
        this.child_category.controls[controlName].setValue(e[controlName]);
      }
    }
    if (e['parentcategory']) {
      this.child_category.controls['parentcategory'].setValue(
        e['parentcategory'].id
      );
    }
    if (e['is_enable']) {
      this.child_category.controls['is_enable'].setValue('true');
    } else {
      this.child_category.controls['is_enable'].setValue('false');
    }
    this.idChildCategory = e.id
    this.urlChild = `${this.HOST_EDE_FILE}/get/image/${e.image_url}`
    this.imageOfC = this.urlChild
    return newP as Child_Category;
  }
  public SearchP(tem: string) {
    this.manageCategoryService.SearchP(tem).subscribe((data: any) => {
      const itemP = data.map(function (obj: {
        id: any;
        name: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }) {
        return obj;
      });
      this.items = itemP;
    });
  }

  public SearchPC(tem: string) {
    this.manageCategoryService.SearchPC(tem).subscribe((data: any) => {
      const it = data.map(function (obj: {
        id: any;
        name: any;
        id_parent: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }) {
        return obj;
      });
      this.itemP = it;
    });
  }

  public SearchC(tem: string) {
    this.manageCategoryService.SearchC(tem).subscribe((data: any) => {
      const itema = data.map(function (obj: {
        id: any;
        name: any;
        id_parent_Child: any;
        image_url: any;
        is_enable: any;
        is_delete: any;
      }) {
        return obj;
      });
      this.itemC = itema;
    });
  }

  //<<<<<<<<<<<<<<<<<<<<<<<< update start in here: vinh
  public idParentCategory: string = ''
  public imageOfP: string = ''
  public idParentChildCategory: string = ''
  public imageOfPC: string = ''
  public idChildCategory: string = ''
  public imageOfC: string = ''

  public updateParentCategory() {
    const nameOfUrl = this.imageOfP.substring(this.imageOfP.lastIndexOf('/') + 1)
    console.log(nameOfUrl, this.imageOfP)
    this.parent.controls['image_url'].setValue(nameOfUrl)
    let categoryUpdate: Parent_Category = this.coverFormGroupToObject<Parent_Category>(this.parent)
    categoryUpdate.id = this.idParentCategory

    this.manageCategoryService.updateParentCategory(categoryUpdate, this.imageParent).then(
      response => {
        this.loadParentCategory();
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          this.reset(this.parent);
        })
      },
      err => {
        this.erros(err);
      }
    );
  }
  public updateParentChildCategory() {
    const nameOfUrl = this.imageOfPC.substring(this.imageOfPC.lastIndexOf('/') + 1)
    this.parent.controls['image_url'].setValue(nameOfUrl)
    let categoryUpdate: Parent_Child_Category = this.coverFormGroupToObject<Parent_Child_Category>(this.parent_child_category)
    categoryUpdate.id = this.idParentChildCategory

    this.manageCategoryService.updateParentChildCategory(categoryUpdate, this.imageParent_Child).then(
      response => {
        this.loadParent_Child_Category();
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          this.reset(this.parent_child_category);
        })
      },
      err => {
        this.erros(err);
      }
    )
  }
  public updateChildCategory() {
    const nameOfUrl = this.imageOfC.substring(this.imageOfC.lastIndexOf('/') + 1)
    this.parent.controls['image_url'].setValue(nameOfUrl)
    let categoryUpdate: Child_Category = this.coverFormGroupToObject<Child_Category>(this.child_category)
    categoryUpdate.id = this.idChildCategory

    this.manageCategoryService.updateChildCategory(categoryUpdate, this.imageChild).then(
      response => {
        this.load_Child_Category();
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm loại danh mục thành công',
          confirmButtonText: `OK`,
        }).then((result) => {
          this.reset(this.child_category);
        })
      },
      err => {
        this.erros(err);
      }
    );
  }
  private coverFormGroupToObject<T>(formGroup: FormGroup) {
    let obj: any = {};
    for (const controlName in formGroup.controls) {
      if (controlName) {
        if ('parentcategory' == controlName) {
          obj[controlName] = { id:  formGroup.controls[controlName].value}
          continue
        }
        obj[controlName] = formGroup.controls[controlName].value
      }
    }
    return obj as T;
  }
  //>>>>>>>>>>>>>>>>>>>>>>>>  update end in here: vinh
  erros(err:any){
    if(err.status==401){
      Swal.fire({
        icon: 'error',
        title: 'Lỗi xác thực đăng nhập',
        text: err.error.message,
      }).then(()=>{
        document.location.href='http://localhost:4200/login'
      });
     }else if(err.status==403){
      Swal.fire({
        icon: 'error',
        title: 'Không có quyền admin',
        text: err.error.message,
      }).then(()=>{
        document.location.href='http://localhost:4200'
      });
     }else{
      Swal.fire({
        icon: 'error',
        title: 'Lỗi',
        text: err.error.message,
      });
     }
  }
}
