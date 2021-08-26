import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { RegisterAccountComponent } from './register-account/register-account.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AccountActiveComponent } from './account-active/account-active.component';
import { ProductShopComponent } from './product-shop/product-shop.component';
import { AsideShopComponent } from './layout/aside-shop/aside-shop.component';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { ProductAllComponent } from './product-all/product-all.component';
import { OrderAllComponent } from './order-all/order-all.component';
import { ShopInterfaceComponent } from './shop-interface/shop-interface.component';
import { ShowAllProductsShopInterfaceComponent } from './show-all-products-shop-interface/show-all-products-shop-interface.component';
import { ShopProfileComponent } from './shop-profile/shop-profile.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    IndexComponent,
    LoginComponent,
    RegisterAccountComponent,
    ForgotPasswordComponent,
    AccountActiveComponent,
    ProductShopComponent,
    AsideShopComponent,
    ProductAllComponent,
    OrderAllComponent,
    ShopInterfaceComponent,
    ShowAllProductsShopInterfaceComponent,
    ShopProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatIconModule,
    MatDatepickerModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
