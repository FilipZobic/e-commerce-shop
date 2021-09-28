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
    CountrySelectComponent
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
        HttpClientModule
    ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
