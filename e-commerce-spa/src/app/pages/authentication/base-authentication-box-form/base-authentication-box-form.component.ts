import { Component, OnInit } from '@angular/core';
import {UserLoginDto} from "../../../model/dto/user-login-dto";
import {AuthenticationService} from "../../../services/authentication.service";
import {CountryService} from "../../../services/country.service";
import {Country} from "../../../model/country";
import {RegistrationDto} from "../../../model/dto/registration-dto";

@Component({
  selector: 'app-base-authentication-box-form',
  templateUrl: './base-authentication-box-form.component.html',
  styleUrls: ['./base-authentication-box-form.component.scss']
})
export class BaseAuthenticationBoxFormComponent implements OnInit{
  LOGIN = 'login';
  REGISTRATION = 'registration';
  active = this.LOGIN;
  countries: Country[] = [];

  constructor(private authenticationService: AuthenticationService, private countryService: CountryService) {
    this.countryService.itemsSubject.subscribe(newCountries => {
      this.countries = newCountries;
    })
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

  handleRegistrationSubmit(userRegistrationDto: RegistrationDto) {
    this.authenticationService.attemptRegistration(userRegistrationDto);
  }

  ngOnInit(): void {
  }


}
