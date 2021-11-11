import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { AddressUserService } from '../Services/address-user/address-user.service';
import { ApiAddressService } from '../Services/api-address/api-address.service';
import { HeaderService } from '../Services/header/header.service';

@Component({
  selector: 'app-user-address',
  templateUrl: './user-address.component.html',
  styleUrls: ['./user-address.component.css'],
})
export class UserAddressComponent implements OnInit {
  constructor(
    private headerService: HeaderService,
    private address_user: AddressUserService,
    private cookieService: CookieService,
    private apiAddressService: ApiAddressService
  ) {}

  ngOnInit(): void {
    this.getAllAddressUser();
    this.getApiCity();
  }
  public address = new FormGroup({
    fullName: new FormControl(''),
    phone: new FormControl(''),
    city: new FormControl(''),
    district: new FormControl(''),
    wards: new FormControl(''),
    address: new FormControl(''),
  });
  public listAddressUser: any = {};
  public listCitys: any = [];
  public listDistricts: any = [];
  public listWards: any = [];
  isHiddenAddress: boolean = true;
  isHiddenWards: boolean = true;
  isHiddenDistrict: boolean = true;

  public getAllAddressUser() {
    this.address_user.getAllAdressByUser().subscribe(
      (data) => {
        this.listAddressUser = data;
        console.log(this.listAddressUser);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  /**
   * Hàm lấy ra tất cả các thành phố
   * @returns {obj} danh sách thành phố
   */
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

  /**
   * Hàm lấy ra tất cả các quận theo id của thành phố
   * @param {string} id id của thành phố
   * @returns {obj} danh sách quận
   */
  public getApiDistricts(id: any) {
    this.apiAddressService.getApiDistricts(id).subscribe((data) => {
      const listDistrict = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.listDistricts = listDistrict;
    });
  }

  /**
   * Hàm lấy ra tất cả các phường theo id của quận
   * @param {string} id id của quận
   * @returns {obj} danh sách phường
   */
  public getApiWards(id: any) {
    this.apiAddressService.getApiWards(id).subscribe((data) => {
      const listWard = data.map(function (obj: { id: any; name: any }) {
        return obj;
      });
      this.listWards = listWard;
    });
  }

  chooseCity() {
    this.isHiddenDistrict = false;
    this.getApiDistricts(this.address.controls['city'].value);
  }

  chooseDistrict() {
    this.isHiddenWards = false;
    this.getApiWards(this.address.controls['district'].value);
  }

  chooseWards() {
    this.isHiddenAddress = false;
  }
}
