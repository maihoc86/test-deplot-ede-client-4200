import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { ManagerAccountsComponent } from './manager-accounts/manager-accounts.component';
import { ManagerCategoryComponent } from './manager-category/manager-category.component';
import { ManagerProductComponent } from './manager-product/manager-product.component';
import { ManagerShopComponent } from './manager-shop/manager-shop.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'manage-account', component: ManagerAccountsComponent },
  { path: 'manage-category', component: ManagerCategoryComponent },
  { path: 'manage-product', component: ManagerProductComponent },
  { path: 'manage-shop', component: ManagerShopComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
