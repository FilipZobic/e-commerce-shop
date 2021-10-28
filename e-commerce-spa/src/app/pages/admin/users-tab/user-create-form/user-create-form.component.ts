import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Country} from "../../../../model/country";
import {RegistrationDto} from "../../../../model/dto/registration-dto";
import {InputValidationService} from "../../../../services/input-validation.service";
import {MyErrorStateMatcher} from "../../../authentication/authentication-registration-form/authentication-registration-form.component";
import {SelectDto} from "../../../../model/select-dto";

@Component({
  selector: 'app-user-create-form',
  templateUrl: './user-create-form.component.html',
  styleUrls: ['./user-create-form.component.scss']
})
export class UserCreateFormComponent {

  form: FormGroup = new FormGroup({});
  @Input()
  countries: Country[] = [{alpha2Code: "RS", name: "Serbia"}];
  @Output()
  submitCreateUserEmitter: EventEmitter<RegistrationDto> = new EventEmitter<RegistrationDto>();

  constructor(private inputValidationService: InputValidationService) {
    this.form.addControl('password', new FormControl(null));
    this.form.addControl('email', new FormControl(null));
    this.form.addControl('country', new FormControl(null));
    this.form.addControl('fullName', new FormControl(null));
    this.form.addControl('address', new FormControl(null));
    this.form.addControl('city', new FormControl(null));
    this.form.addControl('zipCode', new FormControl(null));
    this.form.addControl('role', new FormControl(null));
    this.form.addControl('isDeleted', new FormControl(false));
    this.form.addControl('isEnabled', new FormControl(true));
    this.form.controls.email.addAsyncValidators(inputValidationService.validateUsernameNotTaken.bind(inputValidationService))
  }

  matcher = new MyErrorStateMatcher();
  roleSelectDtos: SelectDto[] = [{value: "ROLE_ADMIN", display: "Admin"}, {value: "ROLE_USER", display: "User"}];

  onSubmit() {
    if (this.form.valid) {
      const dto: RegistrationDto = {
        id: null,
        password: this.form.value.password,
        email: this.form.value.email,
        fullName: this.form.value.fullName,
        isEnabled: this.form.value.isEnabled,
        isDeleted: this.form.value.isDeleted,
        role: this.form.value.role,
        address: {
          addressValue: this.form.value.address,
          cityName: this.form.value.city,
          countryAlpha2Code: this.form.value.country,
          zipCode: this.form.value.zipCode
        }
      }
      this.submitCreateUserEmitter.emit(dto);
      this.form.reset()
      this.form.markAsPristine()
      this.form.markAsUntouched()
      this.form.patchValue({
        isEnabled: true
      })
    }
  }
}
