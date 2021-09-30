import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroup, FormGroupDirective, NgForm} from "@angular/forms";
import {Country} from "../../../model/country";
import {CountryService} from "../../../services/country.service";
import {RegistrationDto} from "../../../model/dto/registration-dto";
import {InputValidationService} from "../../../services/input-validation.service";

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
  @Output()
  submitLoginEmitter: EventEmitter<RegistrationDto> = new EventEmitter<RegistrationDto>();

  constructor(private countryService: CountryService, private inputValidationService: InputValidationService) {
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
    this.form.controls.email.addAsyncValidators(inputValidationService.validateUsernameNotTaken.bind(inputValidationService))
    // @ts-ignore
    this.form.addValidators(inputValidationService.passwordMatchValidator("password", "confirmPassword"));
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
      this.submitLoginEmitter.emit(dto);
    }
  }
}
