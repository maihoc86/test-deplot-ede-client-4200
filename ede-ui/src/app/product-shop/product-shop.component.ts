import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AddProductService } from '../Services/product-shop/add-product.service';
import { Product } from '../models/product.model';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { CookieService } from 'ngx-cookie-service';
import { ProductOptions } from '../models/product-options.model';
import { ProductOptionsImage } from 'src/app/models/product-options-image.model';
import { ProductTag } from '../models/product-tag.model';

import * as moment from 'moment';
import { ProductDiscount } from '../models/product-discount.model';

@Component({
  selector: 'app-product-shop',
  templateUrl: './product-shop.component.html',
  styleUrls: ['./product-shop.component.css']
})
export class ProductShopComponent implements OnInit {
  // TODO: Thêm TAG
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: Tag[] = [];
  test: any;
  minDate = moment(new Date()).format('YYYY-MM-DD');
  maxDate = moment(new Date(2023, 1, 1)).format('YYYY-MM-DD');
  images: string[] = [];
  imageArray: string[] = [];
  tagArray: string[] = [];
  image_option: any;
  public isHiddenEndDate: boolean = true;
  public isHiddenChildParent: boolean = true;
  public isHiddenChild: boolean = true;
  public isHiddenDiscount: boolean = true;
  public listChildCategory: any = [];
  public listParent_ChildCategory: any = [];
  public listParentCategory: any = [];
  public listBrands: any = [];
  public listCountry: any = [];
  public listCities: any = [];
  public product = new FormGroup({
    id: new FormControl(''),
    origin: new FormControl('', [
      Validators.required,
    ]),
    location: new FormControl('', [
      Validators.required,
    ]),
    name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){2,25}$"),
    ]),
    description: new FormControl(''),
    enable: new FormControl('true'),
    deleted: new FormControl('false'),
    brand: new FormControl('', Validators.required),
    child_category: new FormControl('', Validators.required),
    parent_category: new FormControl('', Validators.required),
    parent_child_category: new FormControl('', Validators.required),
  });
  public product_options = new FormGroup({
    description: new FormControl(''),
    file: new FormControl(''),
    display_name: new FormControl('', [Validators.required,
    Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){1,25}$"),
    ]),
    price: new FormControl('', [Validators.required,
    Validators.pattern("([0-9]{0,9})\\b"),]),
    size: new FormControl(''),
    quantity: new FormControl('', [Validators.required,
    Validators.pattern("([0-9]{0,4})\\b"),]),
    id_product: new FormControl(''),
  });

  public product_options_image = new FormGroup({
    productoption: new FormControl(''),
    image: new FormControl(''),
  });

  public product_discount = new FormGroup({
    productdiscount: new FormControl(''),
    discount: new FormControl('', Validators.required),
    startdate: new FormControl('', Validators.required),
    enddate: new FormControl('', Validators.required),
  });

  public product_tags = new FormGroup({
    producttag: new FormControl(''),
    tag: new FormControl(''),
  });
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private Addservice: AddProductService,
    private AddresseService: ApiAddressService,
    private cookieService: CookieService
  ) { }

  ngOnInit(): void {
    this.getBrands();
    this.getParentCategory();
    this.getCountry();
    this.getCities();
    this.getProductById();
  }
  private createDataProduct() {
    const newProduct: any = { };
    for (const controlName in this.product.controls) {
      if (controlName) {
        newProduct[controlName] = this.product.controls[controlName].value;
      }
    }
    return newProduct as Product;
  }
  private createNewOption() {
    const newOption: any = { };
    for (const controlName in this.product_options.controls) {
      if (controlName) {
        newOption[controlName] = this.product_options.controls[controlName].value;
      }
    }
    return newOption as ProductOptions;
  }
  private createNewOptionImage() {
    const newProduct: any = { };
    for (const controlName in this.product_options_image.controls) {
      if (controlName) {
        newProduct[controlName] = this.product_options_image.controls[controlName].value;
      }
    }
    return newProduct as ProductOptionsImage;
  }
  private createDataTag() {
    const newProduct: any = { };
    for (const controlName in this.product_tags.controls) {
      if (controlName) {
        newProduct[controlName] = this.product_tags.controls[controlName].value;
      }
    }
    return newProduct as ProductTag;
  }

  private createNewDataDiscount() {
    const newProduct: any = { };
    for (const controlName in this.product_discount.controls) {
      if (controlName) {
        newProduct[controlName] = this.product_discount.controls[controlName].value;
      }
    }
    return newProduct as ProductDiscount;
  }
  addTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.tags.push({ name: value });
      this.tagArray.push(value);
      this.product_tags.patchValue({
        tag: this.tagArray.toString()
      });
      console.log(this.tagArray);
    }

    event.chipInput!.clear();
  }
  removeTag(tag: Tag): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      console.log(this.tagArray);
      this.tagArray.splice(index, 1);
      this.product_tags.patchValue({
        tag: this.tagArray.toString()
      });
      console.log(this.tagArray);
    }
  }
  numberOnly(event: any) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
  showParent_Child() {
    this.isHiddenChild = true;
    this.isHiddenChildParent = false;
    this.getParentChildCategory(this.product.controls['parent_category'].value);
  }
  showChild() {
    this.isHiddenChild = false;
    this.getChildCategory(this.product.controls['parent_child_category'].value);
  }
  showDiscount() {
    this.isHiddenDiscount = false;
  }
  changeDate(event: any) {
    console.log(event);
    this.isHiddenEndDate = false;
  }

  onFileChange(event: any) {
    this.image_option = '';
    if (event.target.files && event.target.files[0]) {
      var filesAmount = event.target.files.length;
      for (let i = 0; i < filesAmount; i++) {
        var reader = new FileReader()
        reader.onload = (event: any) => {
          this.images.push(event.target.result);
        }
        reader.readAsDataURL(event.target.files[i]);
        this.imageArray.push(event.target.files[i].name)
        this.product_options_image.patchValue({
          image: this.imageArray.toString()
        });
      }
    }

  }
  deleteImage(event: any, url: any) {
    // TODO: delete image from server
    var item = url;
    var length = this.images.length;
    for (let i = 0; i < length; i++) {
      if (this.images[i] == item) {
        this.imageArray.splice(i, 1);
        this.images.splice(i, 1);
        this.product_options_image.patchValue({
          image: this.imageArray.toString()
        });
      }
    }
  }
  public updateProduct() {
    this.product.controls['deleted'].setValue('false');
    console.log(this.product.controls['id'].value);
    this.Addservice.updateProduct(this.createDataProduct()).subscribe(
      (data) => {
        console.log(data);
        this.product_options.controls['id_product'].setValue(data.id);
        this.product_discount.controls['productdiscount'].setValue(data);
        this.Addservice.updateProductDiscount(this.createNewDataDiscount()).toPromise().then(data => {
        }), ((error: any) => {
        })
        this.Addservice.updateProductOption(this.createNewOption()).subscribe(
          (data) => {
            this.product_options_image.controls['productoption'].setValue(data);
            if (this.imageArray.length > 0) {
              this.Addservice.updateProductOptionImage(this.createNewOptionImage()).toPromise();
            }
          }), ((error: any) => {
          })
      })
  }
  public addProduct() {
    this.product.controls['deleted'].setValue('false');
    this.Addservice.addProductShop(this.createDataProduct()).subscribe(
      (data) => {
        Swal.fire({
          title: 'Thêm sản phẩm thành công !!',
          text: "Bạn có muốn đăng bán sản phẩm luôn không!",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Đăng bán!'
        }).then((result) => {
          this.product_options.controls['id_product'].setValue(data.id);
          this.product_discount.controls['productdiscount'].setValue(data);
          this.Addservice.addProductDiscount(this.createNewDataDiscount()).toPromise().then(data => {
            console.log(data);
          }), ((error: any) => {
            console.log(error);
          })
          this.Addservice.addProductOption(this.createNewOption()).toPromise().then(data => {
            this.product_options_image.controls['productoption'].setValue(data);
            if (this.imageArray.length > 0) {
              this.Addservice.addProductOptionImage(this.createNewOptionImage()).toPromise().then(data => {
              });
            }
          });
          if (result.isConfirmed) {
            // console.log(data)
            this.Addservice.enableProductShop(data.id).subscribe((data) => {
              Swal.fire({
                title: 'Thông báo!',
                text: 'Sản phẩm đã được đăng bán',
                icon: 'success'
              }
              )
            })
          }
        })
      },
      (err) => {
        console.log(err.error)
        if (err.status == 404) {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: "Chưa đăng nhập",
          });
          this.router.navigate(['/login'])
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: err.error.message,
          });
        }
      }
    );
  }
  public getCities() {
    this.AddresseService.getApiCity().subscribe((data) => {
      const listCities = data.map(function (obj: { name: any; }) {
        return obj;
      });
      this.listCities = listCities;
    });
  }
  public getCountry() {
    this.AddresseService.getCountry().subscribe((data) => {
      const listCountry = data.map(function (obj: { name: any; }) {
        return obj;
      });
      this.listCountry = listCountry;
    });
  }
  public getBrands() {
    this.Addservice.getBrand().subscribe(
      (data) => {
        const listBrands = data.map(function (obj: { id: any; name: any; avatar: any; }) {
          return obj;
        });
        this.listBrands = listBrands;
      }
    );
  }
  public getParentChildCategory(id: any) {
    this.Addservice.getChildParentCategoriesByIdParent(id).subscribe((data) => {
      const listCategories = data.map(function (obj: { id: any; name: any; image_url: any; is_enable: boolean; is_deleted: boolean; child_parentCategory: any; }) {
        return obj;
      });
      this.listParent_ChildCategory = listCategories;
    });
  }
  public getParentCategory() {
    this.Addservice.getParentCategories().subscribe((data) => {
      const listCategories = data.map(function (obj: { id: any; name: any; image_url: any; is_enable: boolean; is_deleted: boolean; child_parentCategory: any; }) {
        return obj;
      });
      this.listParentCategory = listCategories;
    });
  }
  public getChildCategory(id: any) {
    this.Addservice.getChildCategoriesByChildParent(id).subscribe((data) => {
      const listCategories = data.map(function (obj: { id: any; name: any; image_url: any; is_enable: boolean; is_deleted: boolean; child_parentCategory: any; }) {
        return obj;
      });

      this.listChildCategory = listCategories;
    });
  }
  sizeGroups: sizegroup[] = [
    {
      name: 'Size số',
      size: [
        { value: 'so_31', viewValue: '31' },
        { value: 'so_32', viewValue: '32' },
        { value: 'so_33', viewValue: '33' },
        { value: 'so_34', viewValue: '34' },
        { value: 'so_35', viewValue: '35' },
        { value: 'so_36', viewValue: '36' },
        { value: 'so_37', viewValue: '37' },
        { value: 'so_38', viewValue: '38' },
        { value: 'so_39', viewValue: '39' },
        { value: 'so_40', viewValue: '40' },
        { value: 'so_41', viewValue: '41' },
        { value: 'so_42', viewValue: '42' },
        { value: 'so_43', viewValue: '43' },
        { value: 'so_44', viewValue: '44' },
        { value: 'so_45', viewValue: '45' },
      ]
    },
    {
      name: 'Size chữ',
      size: [
        { value: 'chu_s', viewValue: 'S' },
        { value: 'chu_m', viewValue: 'M' },
        { value: 'chu_L', viewValue: 'L' },
        { value: 'chu_XL', viewValue: 'XL' },
        { value: 'chu_XX:', viewValue: 'XXL' },
        { value: 'chu_XXXL', viewValue: 'XXXL' },
      ]
    }
  ];

  public loadProdutedit() {
    var id = '';
    this.route.params.subscribe(params => { console.log(params['id']), id = params['id']; });
    return id;
  }
  public getProductById() {
    if (this.loadProdutedit()) {
      this.Addservice.getProductByid(this.loadProdutedit()).subscribe(data => {
        for (const controlName in this.product.controls) {
          for (const node in data) {
            if (controlName && controlName == node) {
              this.product.controls[controlName].setValue(data[node]);
            }
          }
        }
        if (data['brand']) {
          this.product.controls['brand'].setValue(data['brand']);
        }
        this.Addservice.getProductOptionByid(this.loadProdutedit()).subscribe(data => {
          for (const controlName in this.product_options.controls) {
            for (const node in data) {
              if (controlName && controlName == node) {
                this.product_options.controls[controlName].setValue(data[node]);
              }
            }
          }
        })
        this.Addservice.getCategoryByidProduct(this.loadProdutedit()).subscribe(child => {
          this.Addservice.getParent_Child_CategoryByid(child.id).subscribe(parent_child => {
            this.Addservice.getParent_CategoryByid(parent_child.id).subscribe(parent => {
              this.product.controls['parent_category'].setValue(parent.id);
              this.product.controls['parent_child_category'].setValue(parent_child.id);
              this.product.controls['child_category'].setValue(child);
              this.showParent_Child();
              this.showChild();
            })
          })
        })
        this.Addservice.getTagbyProductid(this.loadProdutedit()).subscribe(tags => {
          tags.forEach((element: any) => {
            this.tags.push({ name: element.tag.trim() });
            this.tagArray.push(element.tag.trim());
            this.product_tags.patchValue({
              tag: this.tagArray.toString()
            });
          });
          console.log(this.tags);
          console.log(this.tagArray);
        })
      }, err => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: "Không tìm thấy sản phẩm",
        }).then(data => {
          this.router.navigateByUrl("/shop/product/manager");
        });
      }
      );
    }
  }
  public objectComparisonFunction = function (option: { id: any; }, value: { id: any; }): boolean {
    return option.id === value.id;
  }
}

interface size {
  value: string;
  viewValue: string;
}

interface sizegroup {
  disabled?: boolean;
  name: string;
  size: size[];
}
export interface Tag {
  name: string;
}
