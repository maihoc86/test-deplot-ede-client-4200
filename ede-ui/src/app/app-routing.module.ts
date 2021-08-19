import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountActiveComponent } from './account-active/account-active.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { ProductOptionsShopComponent } from './product-options-shop/product-options-shop.component';
import { ProductShopComponent } from './product-shop/product-shop.component';
import { RegisterAccountComponent } from './register-account/register-account.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'register', component: RegisterAccountComponent },
  { path: 'account/verify', component: AccountActiveComponent },
  { path: 'shop/product/manager', component: ProductShopComponent },
  { path: 'shop/product/options', component: ProductOptionsShopComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
