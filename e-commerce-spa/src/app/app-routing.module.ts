import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthenticationComponent} from './pages/authentication/authentication.component';
import {HomeComponent} from "./pages/home/home.component";
import {RoleGuard} from "./guards/role.guard";

const routes: Routes = [
  {component: AuthenticationComponent, path: 'authentication'},
  {component: HomeComponent, path: '', data: {allowedRoles: ['ROLE_ADMIN', 'ROLE_USER']}, canActivate: [RoleGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
