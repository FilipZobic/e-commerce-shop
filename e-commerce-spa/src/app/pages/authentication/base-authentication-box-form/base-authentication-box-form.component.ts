import { Component, OnInit } from '@angular/core';
import {UserLoginDto} from "../../../model/dto/user-login-dto";
import {AuthenticationService} from "../../../services/authentication.service";
import {CountryService} from "../../../services/country.service";
import {Country} from "../../../model/country";

@Component({
  selector: 'app-base-authentication-box-form',
  templateUrl: './base-authentication-box-form.component.html',
  styleUrls: ['./base-authentication-box-form.component.scss']
})
export class BaseAuthenticationBoxFormComponent {
  LOGIN = 'login';
  REGISTRATION = 'registration';
  active = this.REGISTRATION;
  readonly countries: Country[] = [];

  constructor(private authenticationService: AuthenticationService, private countryService: CountryService) {
    this.countries = this.countryService.countries;
  }

  switchToRegistration(): void {
    this.active = this.REGISTRATION;
  }

  switchToSignIn(): void {
    this.active = this.LOGIN;
  }

  handleLoginSubmit(userLoginDto: UserLoginDto) {
    this.authenticationService.attemptLogin(userLoginDto);
  }
}
