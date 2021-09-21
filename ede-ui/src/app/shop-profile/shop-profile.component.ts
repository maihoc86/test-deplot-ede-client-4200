import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { HeaderService } from '../Services/header/header.service';
import { CookieService } from 'ngx-cookie-service';
import Swal from 'sweetalert2';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../models/user.model';
import { Shop } from '../models/shop.model';
import { MyShopService } from '../Services/my-shop/my-shop.service';
import { ApiAddressService } from '../Services/api-address/api-address.service';

@Component({
  selector: 'app-shop-profile',
  templateUrl: './shop-profile.component.html',
  styleUrls: ['./shop-profile.component.css'],
})
export class ShopProfileComponent implements OnInit {
  @ViewChild('InputImage', { static: false })
  InputImage!: ElementRef;

  constructor(
    private headerService: HeaderService,
    private cookieService: CookieService,
    private router: Router,
    private shopService: MyShopService,
    private apiAddressService: ApiAddressService
  ) {
    this.shop = {} as Shop;
  }
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;
  urlImageLoad = '/assets/img/ba.jpg';
  ImageUrl = '';
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  onselectFile(e: any) {
    if (e.target.files) {
      var reader = new FileReader();
      this.ImageUrl = e.target.files[0].name;
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (e: any) => {
        this.urlImageLoad = e.target.result;
      };
    }
  }
  showSelectionAddress() {
    this.formShop.controls['address'].setValue('');
    this.isHiddenAddress = !this.isHiddenAddress;
  }
  getDistricts() {
    this.isHiddenDistrict = false;
    this.getApiDistricts(this.formShop.controls['city'].value.id);
  }
  getWards() {
    this.isHiddenWards = false;

    this.getApiWards(this.formShop.controls['district'].value.id);
  }
  getUrl_sub(image_sub: string) {
    this.urlImageLoad = 'http://localhost:8080/ede-file/get/image/' + image_sub;
    return 'url(' + this.urlImageLoad + ')';
  }
  public formShop = new FormGroup({
    id: new FormControl(''),
    name: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF]{0,25}[ \\-\\']{0,}){3,150}$"
      ),
    ]),
    image: new FormControl(''),
    city: new FormControl(''),
    district: new FormControl(''),
    wards: new FormControl(''),
    address: new FormControl('', [
      Validators.required,
      Validators.pattern(
        "^\\S([a-zA-Z0-9\\xC0-\\uFFFF\\.]{1,}[ \\-\\' \\.-/,]{0,}){5,200}$"
      ),
    ]),
    description: new FormControl(''),
    user: new FormControl(''),
    create_date: new FormControl(''),
  });
  ngOnInit(): void {
    this.getApiCity();
    this.loadProfileShopByUserLogin();
  }
  private createNewDataSop() {
    const newShop: any = {};
    for (const controlName in this.formShop.controls) {
      if (controlName) {
        newShop[controlName] = this.formShop.controls[controlName].value;
      }
    }
    return newShop as Shop;
  }
  updateInfoShop() {
    this.headerService
      .getShopByToken(this.cookieService.get('auth'))
      .subscribe((data) => {
        this.shop = data;
        if (
          this.formShop.controls['wards'].value.name !== undefined &&
          this.formShop.controls['district'].value.name !== null &&
          this.formShop.controls['city'].value.name !== null
        ) {
          var newAddress =
            this.formShop.controls['address'].value +
            ', ' +
            this.formShop.controls['wards'].value.name +
            ', ' +
            this.formShop.controls['district'].value.name +
            ', ' +
            this.formShop.controls['city'].value.name;
          this.formShop.controls['address'].setValue(newAddress);
        }
        this.formShop.controls['user'].setValue(data.user);
        if (this.ImageUrl !== '') {
          this.formShop.controls['image'].setValue(this.ImageUrl);
        }
        this.shopService
          .updateInfoShop(this.createNewDataSop())
          .subscribe((data) => {
            Swal.fire({
              icon: 'success',
              title: 'Thành công',
              text: 'Cập nhật thành công',
            }).then(() => {
              window.location.reload();
            });
          });
      });
  }
  public shop: Shop;
  public loadProfileShopByUserLogin() {
    this.headerService.getShopByToken(this.cookieService.get('auth')).subscribe(
      (data) => {
        this.shop = data;
        for (const controlName in this.formShop.controls) {
          for (const node in data) {
            if (controlName && controlName == node) {
              this.formShop.controls[controlName].setValue(data[node]);
            }
          }
        }
      },
      (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Lỗi',
          text: 'Lỗi đăng nhập',
        });
        this.router.navigate(['/login']);
      }
    );
  }
  public getApiCity() {
    this.apiAddressService.getApiCity().subscribe((data) => {
      const listCity = data.map(function (obj: {
        id: any;
        code: any;
        name: any;
      }) {
        return obj;
      });
      this.listCitys = listCity;
    });
  }
  public getApiDistricts(id: any) {
    this.apiAddressService.getApiDistricts(id).subscribe((data) => {
      const listDistrict = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.listDistricts = listDistrict;
    });
  }

  public getApiWards(id: any) {
    this.apiAddressService.getApiWards(id).subscribe((data) => {
      const listWard = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.listWards = listWard;
    });
  }
}
