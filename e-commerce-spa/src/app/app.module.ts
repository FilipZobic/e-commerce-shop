import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { BaseAuthenticationBoxFormComponent } from './pages/authentication/base-authentication-box-form/base-authentication-box-form.component';
import { AuthenticationLoginFormComponent } from './pages/authentication/authentication-login-form/authentication-login-form.component';
import { AuthenticationRegistrationFormComponent } from './pages/authentication/authentication-registration-form/authentication-registration-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import { EmailInputComponent } from './components/input/email-input/email-input.component';
import { PasswordInputComponent } from './components/input/password-input/password-input.component';
import { HomeComponent } from './pages/home/home.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatBadgeModule} from "@angular/material/badge";
import {MatSliderModule} from "@angular/material/slider";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { RenderIfRoleDirective } from './directives/render-if-role.directive';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { TextInputComponent } from './components/input/text-input/text-input.component';
import { CountrySelectComponent } from './components/input/country-select/country-select.component';
import { HeaderComponent } from './pages/home/header/header.component';
import { NavigationBarComponent } from './pages/home/navigation-bar/navigation-bar.component';
import { ProductComponent } from './pages/product/product.component';
import { AdminComponent } from './pages/admin/admin.component';
import { OrderComponent } from './pages/order/order.component';
import { TransactionComponent } from './pages/transaction/transaction.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LaptopSearchFormComponent } from './components/laptop-search-form/laptop-search-form.component';
import { PrimaryPaginatorComponent } from './components/primary-paginator/primary-paginator.component';
import {MatListModule} from "@angular/material/list";
import {MatTabsModule} from "@angular/material/tabs";
import {MatTableModule} from "@angular/material/table";
import {UsersTabComponent} from "./pages/admin/users-tab/users-tab.component";
import { ManufacturerTabComponent } from './pages/admin/manufacturer-tab/manufacturer-tab.component';
import { ManufacturerTabUpdateFormComponent } from './pages/admin/manufacturer-tab/manufacturer-tab-update-form/manufacturer-tab-update-form.component';
import { ManufacturerTabCreateFormComponent } from './pages/admin/manufacturer-tab/manufacturer-tab-create-form/manufacturer-tab-create-form.component';
import { ManufacturerTabTableComponent } from './pages/admin/manufacturer-tab/manufacturer-tab-table/manufacturer-tab-table.component';
import { LaptopTabComponent } from './pages/admin/laptop-tab/laptop-tab.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent,
    BaseAuthenticationBoxFormComponent,
    AuthenticationLoginFormComponent,
    AuthenticationRegistrationFormComponent,
    EmailInputComponent,
    PasswordInputComponent,
    HomeComponent,
    RenderIfRoleDirective,
    TextInputComponent,
    CountrySelectComponent,
    HeaderComponent,
    NavigationBarComponent,
    ProductComponent,
    AdminComponent,
    OrderComponent,
    TransactionComponent,
    ProfileComponent,
    LaptopSearchFormComponent,
    PrimaryPaginatorComponent,
    UsersTabComponent,
    ManufacturerTabComponent,
    ManufacturerTabUpdateFormComponent,
    ManufacturerTabCreateFormComponent,
    ManufacturerTabTableComponent,
    LaptopTabComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatCardModule,
    MatToolbarModule,
    MatMenuModule,
    MatSidenavModule,
    MatGridListModule,
    MatPaginatorModule,
    MatBadgeModule,
    MatSliderModule,
    MatCheckboxModule,
    HttpClientModule,
    MatListModule,
    MatTabsModule,
    MatTableModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
