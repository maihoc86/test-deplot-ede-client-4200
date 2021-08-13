import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { ManagerAccountsComponent } from './manager-accounts/manager-accounts.component';

const routes: Routes = [
  {path: '', component: IndexComponent},
  {path: 'manage-account', component: ManagerAccountsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
