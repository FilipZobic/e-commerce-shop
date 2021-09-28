import { Component, OnInit } from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {Country} from "../../../model/country";
import {CountryService} from "../../../services/country.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {RegistrationDto} from "../../../model/dto/registration-dto";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-authentication-registration-form',
  templateUrl: './authentication-registration-form.component.html',
  styleUrls: ['./authentication-registration-form.component.scss']
})
export class AuthenticationRegistrationFormComponent {
  form: FormGroup = new FormGroup({});
  countries: Country[] = [{alpha2Code: "RS", name: "Serbia"}];
  hide = true;
  constructor(private countryService: CountryService, private authenticationService: AuthenticationService, private fb: FormBuilder) {
    // fb.group(
    //   {
    //     "password": [null],
    //     "confirmPassword": [null],
    //     "email": [null, authenticationService.validateUsernameNotTaken.bind(authenticationService)],
    //     "country": [null],
    //     "fullName": [null],
    //     "address": [null],
    //     "city": [null],
    //     "zipCode": [null],
    //   }
    // )
    this.form.addControl('password', new FormControl(null));
    this.form.addControl('confirmPassword', new FormControl(null));
    this.form.addControl('email', new FormControl(null));
    this.form.addControl('country', new FormControl(null));
    this.form.addControl('fullName', new FormControl(null));
    this.form.addControl('address', new FormControl(null));
    this.form.addControl('city', new FormControl(null));
    this.form.addControl('zipCode', new FormControl(null));
    this.countryService.itemsSubject.subscribe(newCountries => {
      this.countries = newCountries;
    })
    this.form.controls.email.addAsyncValidators(authenticationService.validateUsernameNotTaken.bind(authenticationService))
    // @ts-ignore
    this.form.addValidators(authenticationService.passwordMatchValidator("password", "confirmPassword"));
  }

  matcher = new MyErrorStateMatcher();

  onSubmit() {
    if (this.form.valid) {
      const dto: RegistrationDto = {
        password: this.form.value.password,
        email: this.form.value.email,
        fullName: this.form.value.fullName,
        address: {
          addressValue: this.form.value.address,
          cityName: this.form.value.city,
          countryAlpha2Code: this.form.value.country,
          zipCode: this.form.value.zipCode
        }
      }
      console.log(dto)
    }
  }
}
