import { AddProductService } from './../../Services/product-shop/add-product.service';
import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute } from '@angular/router';
import { HeaderService } from 'src/app/Services/header/header.service';
import { User } from 'src/app/models/user.model';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [
    './header.component.css',
    '../../../assets/css/header/header1.css',
  ],
})
export class HeaderComponent implements OnInit {
  constructor(
    private cookieService: CookieService,
    private headerService: HeaderService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private producService: AddProductService,
  ) {
    this.addViewPage();
    router.events.subscribe(() => {
      this.hidenSearch();
    });
    this.u = {} as User;
    this.headerService.myMethod$.subscribe((data) => {
      this.cart = data;
      this.loadTotal();
      if (this.login) {
        this.headerService
          .updateCart(this.cart, this.u.id)
          .subscribe((data) => {
            // console.log(data)
          });
      }
    });
  }

  public txtKeysearch: string = '';

  ngOnInit(): void {
    this.getUserLogin();
    this.activatedRoute.queryParams.subscribe((params) => {
      this.params = params;
      this.txtKeysearch = params.search;
    });
    this.loadCart();
    this.getAllCategory();
  }
  public totalCart: any = 0;
  public cart: Array<any> = [];
  public params = {};

  public login: boolean = false;
  public u: User;
  public active: boolean = false;

  public list_parent_category: any = [];
  public list_parent_child_category: any = [];
  public list_child_category: any = [];

  public viewPage = new FormGroup({
    id: new FormControl(''),
    ip: new FormControl(''),
    cookie: new FormControl(null),
    date_view: new FormControl(''),
  });

  addViewPage() {
    this.headerService.getIpAddress().subscribe((ip: any) => {
      this.headerService.getIpAddressDB(ip.ip).subscribe((data) => {
        if (data == null) {
          this.viewPage.controls['ip'].setValue(ip.ip);
          if (this.cookieService.get('auth') != '') {
            this.viewPage.controls['cookie'].setValue(
              this.cookieService.get('auth')
            );
          }
          this.headerService.insertViewPage(this.viewPage.value).subscribe(
            (data) => {},
            (err) => {
              console.log(err);
            }
          );
        }
      });
    });
  }

  public async getUserLogin() {
    await this.headerService
      .getUserByToken(this.cookieService.get('auth'))
      .toPromise()
      .then((data) => {
        this.login = true;
        this.u = data;
      })
      .catch((err) => {
        console.log(err);
        this.login = false;
      });
  }

  public async logout() {
    this.router.navigate(['/']);
    this.cookieService.delete('auth', '/');
    localStorage.removeItem('cart');
    document.location.href = '';
  }

  loadCart() {
    var json = localStorage.getItem('cart');

    this.cart = json ? JSON.parse(json) : [];
    this.loadTotal();
  }
  public removeItemCart(e: any) {
    this.cart.splice(
      this.cart.findIndex((es) => es.id == e.id),
      1
    );
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.loadTotal();
    this.headerService.myMethod(this.cart);
  }
  /**
   * H??m thay ?????i s??? l?????ng
   */
  changeQty(qtyCurrent: any, qtyChange: any) {
    qtyCurrent.quantity = qtyChange;
    qtyChange > qtyCurrent.qty
      ? qtyCurrent.quantity++
      : qtyCurrent.quantity == qtyChange
      ? (qtyCurrent.quantity = qtyChange)
      : qtyCurrent.quantity--;
    qtyCurrent.quantity == 0 ? this.removeItemCart(qtyCurrent) : '';
    localStorage.setItem('cart', JSON.stringify(this.cart));
    this.loadTotal();
    this.headerService.myMethod(this.cart);
  }
  /**
   * H??m ch??? ???????c d??ng s???
   */
  numberOnly(event: any, qtyCurrent: any, qtyChange: any) {
    const charCode = event.which ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    } else {
      qtyCurrent.qty = qtyChange;
      localStorage.setItem('cart', JSON.stringify(this.cart));
      this.loadTotal();
      return true;
    }
  }
  loadTotal() {
    this.totalCart = 0;
    this.cart.forEach((e) => {
      this.totalCart +=
        e.qty *
        (e.discount == 0 ? e.price : e.price - e.price * (e.discount / 100));
    });
  }

  openShop() {
    this.headerService
      .getShopByToken(this.cookieService.get('auth'))
      .subscribe((data) => {
        console.log(data);
        if (data.status) {
          this.router.navigate(['shop/product/all']);
        } else {
          Swal.fire({
            title: 'Th??ng b??o',
            text: 'Shop c???a b???n ???? b??? t???m kho??',
            showConfirmButton: true,
            showCancelButton: true,
            confirmButtonText: 'Li??n h??? h??? tr????',
            cancelButtonText: 'Tr??? l???i',
          });
        }
      });
    //
  }

  hidenSearch() {
    const segments: any = window.location.href;
    if (segments.indexOf('/shop/') > -1) {
      this.active = true;
    }
  }

  getAllCategory(){
    this.producService.getChildCategories().subscribe(data => {
      this.list_child_category = data;
    })
    this.producService.getParentChildCategories().subscribe(data => {
      this.list_parent_child_category = data;
    })
    this.producService.getParentCategories().subscribe(data => {
      this.list_parent_category = data;
    })
  }


}
