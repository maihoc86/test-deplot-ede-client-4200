import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
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
@Component({
  selector: 'app-product-shop',
  templateUrl: './product-shop.component.html',
  styleUrls: ['./product-shop.component.css']
})
export class ProductShopComponent implements OnInit {

  public product = new FormGroup({
    origin: new FormControl('', [
      Validators.required,
    ]),
    name: new FormControl('', [
      Validators.required,
      Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){5,25}$"),
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
    Validators.pattern("^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{1,25}[ \\-\\']{0,}){5,25}$"),]),
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
  }
  images: string[] = [];
  imageArray: string[] = [];
  image_option: any;
  public isHiddenChildParent: boolean = true;
  public isHiddenChild: boolean = true;
  public listChildCategory: any = [];
  public listParent_ChildCategory: any = [];
  public listParentCategory: any = [];
  public listBrands: any = [];
  public listCountry: any = [];
  private createNewData() {
    const newProduct: any = {};
    for (const controlName in this.product.controls) {
      if (controlName) {
        newProduct[controlName] = this.product.controls[controlName].value;
      }
    }
    return newProduct as Product;
  }
  private createNewOption() {
    const newOption: any = {};
    for (const controlName in this.product_options.controls) {
      if (controlName) {
        newOption[controlName] = this.product_options.controls[controlName].value;
      }
    }
    return newOption as ProductOptions;
  }
  private createNewOptionImage() {
    const newProduct: any = {};
    for (const controlName in this.product_options_image.controls) {
      if (controlName) {
        newProduct[controlName] = this.product_options_image.controls[controlName].value;
      }
    }
    return newProduct as ProductOptionsImage;
  }
  numberOnly(event: any) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
  showParent_Child() {
    this.isHiddenChildParent = false;
    this.getParentChildCategory(this.product.controls['parent_category'].value);
  }
  showChild() {
    this.isHiddenChild = false;
    this.getChildCategory(this.product.controls['parent_child_category'].value);
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
  public addProduct() {
    this.product.controls['deleted'].setValue('false');
    this.Addservice.addProductShop(this.createNewData()).subscribe(
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
          this.Addservice.addProductOption(this.createNewOption()).toPromise().then(data => {
            this.product_options_image.controls['productoption'].setValue(data);
            if(this.imageArray.length > 0){
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

