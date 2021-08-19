import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
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
@Component({
  selector: 'app-product-options-shop',
  templateUrl: './product-options-shop.component.html',
  styleUrls: ['./product-options-shop.component.css']
})
export class ProductOptionsShopComponent implements OnInit {
  // selectable = true;
  // removable = true;
  // separatorKeysCodes: number[] = [ENTER, COMMA];
  // fruitCtrl = new FormControl();
  // filteredFruits: Observable<string[]>;
  // fruits: string[] = [];
  // allFruits: string[] = ['Apple', 'Lemon', 'Lime', 'Orange', 'Strawberry'];
  // @ViewChild('fruitInput')
  // fruitInput!: ElementRef<HTMLInputElement>;
  public product = new FormGroup({
    display_name: new FormControl(''),
    price: new FormControl(''),
    size: new FormControl('false'),
    quantity: new FormControl('', Validators.required),
    id_product: new FormControl('', Validators.required),
  });
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private Addservice: AddProductService,
    private AddresseService: ApiAddressService,
  ) {
    // this.filteredFruits = this.fruitCtrl.valueChanges.pipe(
    //   startWith(null),
    //   map((fruit: string | null | undefined) => fruit ? this._filter(fruit) : this.allFruits.slice()));
  }
  // add(event: MatChipInputEvent): void {
  //   const value = (event.value || '').trim();

  //   // Add our fruit
  //   if (value) {
  //     this.fruits.push(value);
  //   }
  //   // Clear the input value
  //   event.chipInput!.clear();

  //   this.fruitCtrl.setValue(null);
  // }
  // remove(fruit: string): void {
  //   const index = this.fruits.indexOf(fruit);

  //   if (index >= 0) {
  //     this.fruits.splice(index, 1);
  //   }
  // }
  // selected(event: MatAutocompleteSelectedEvent): void {
  //   this.fruits.push(event.option.viewValue);
  //   this.fruitInput.nativeElement.value = '';
  //   this.fruitCtrl.setValue(null);
  // }

  // private _filter(value: string): string[] {
  //   const filterValue = value.toLowerCase();
  //   return this.allFruits.filter(fruit => fruit.toLowerCase().includes(filterValue));
  // }
  numberOnly(event: any) {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
  ngOnInit(): void {
    this.listProducts;
    this.getListProducts();
  }
  public listProducts: any = [];
  private createNewData() {
    const newProductOption: any = {};
    for (const controlName in this.product.controls) {
      if (controlName) {
        newProductOption[controlName] = this.product.controls[controlName].value;
      }
    }
    return newProductOption as Product;
  }
  public registerUser() {
    this.product.controls['delete'].setValue('false');
    this.Addservice.addProductShop(this.createNewData()).subscribe(
      (data) => {
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Thêm sản phẩm thành công !',
          confirmButtonText: `OK`,
        }).then((result) => {
          if (result.isConfirmed) {
            this.router.navigate(['login']);
          } else {
            this.router.navigate(['login']);
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
  public getListProducts() {
    this.Addservice.getAllProduct().subscribe((data) => {
      const listProducts = data.map(function (obj: { id: any; name: any; }) {
        return obj;
      });
      this.listProducts = listProducts;
      console.log(this.listProducts);
    });
  }

}
