import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { AddressUserService } from '../Services/address-user/address-user.service';
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
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.getAllAddressUser();
  }
  public listAddressUser: any = {};

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
}
