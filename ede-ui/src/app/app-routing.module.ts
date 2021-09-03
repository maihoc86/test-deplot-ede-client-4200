import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountActiveComponent } from './account-active/account-active.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { ProductShopComponent } from './product-shop/product-shop.component';
import { RegisterAccountComponent } from './register-account/register-account.component';
import { ProductAllComponent } from './product-all/product-all.component';
import { OrderAllComponent } from './order-all/order-all.component';
import { ShopInterfaceComponent } from './shop-interface/shop-interface.component';
import { ShowAllProductsShopInterfaceComponent } from './show-all-products-shop-interface/show-all-products-shop-interface.component';
import { ShopProfileComponent } from './shop-profile/shop-profile.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { RevenueShopComponent } from './revenue-shop/revenue-shop.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'register', component: RegisterAccountComponent },
  { path: 'account/verify', component: AccountActiveComponent },
  { path: 'shop/product/manager', component: ProductShopComponent },
  { path: 'shop/product/all/1', component: ProductAllComponent},
  { path: 'shop/order/all', component:OrderAllComponent},
  { path: 'shop/interface', component: ShopInterfaceComponent},
  { path: 'shop/showall/interface', component: ShowAllProductsShopInterfaceComponent},
  { path: 'shop/profile', component: ShopProfileComponent},
  { path: 'shop/product/manager/:id', component:ProductShopComponent, pathMatch: 'full'},
  { path: 'search', component: ProductSearchComponent },
  { path: 'shop/revenue', component:RevenueShopComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
