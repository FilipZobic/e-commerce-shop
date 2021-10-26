import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {Laptop} from "../../../../model/laptop";
import {SelectDto} from "../../../../model/select-dto";
import {RegistrationDto} from "../../../../model/dto/registration-dto";
import {InputValidationService} from "../../../../services/input-validation.service";
import {UserData} from "../../../../model/user-data";
import {Country} from "../../../../model/country";
import {MyErrorStateMatcher} from "../../../authentication/authentication-registration-form/authentication-registration-form.component";
import {Observable, of} from "rxjs";
import {map} from "rxjs/operators";
import {UserService} from "../../../../services/user.service";
import {AuthenticationService} from "../../../../services/authentication.service";
import {UserDataService} from "../../../../services/user-data.service";

@Component({
  selector: 'app-user-update-form',
  templateUrl: './user-update-form.component.html',
  styleUrls: ['./user-update-form.component.scss']
})
export class UserUpdateFormComponent implements OnInit {

  form: FormGroup = new FormGroup({});

  @Output()
  onUpdateEmmiter = new EventEmitter<RegistrationDto>()

  @Output()
  onDeleteEmitter = new EventEmitter<string>()

  @Input()
    // @ts-ignore
  userIntialState: UserData;

  @Input()
  countries: Country[] = [{alpha2Code: "RS", name: "Serbia"}];

  constructor(private authenticationService: AuthenticationService, private userData: UserDataService) {
  }

  matcher = new MyErrorStateMatcher();
  roleSelectDtos: SelectDto[] = [{value: "ROLE_ADMIN", display: "Admin"}, {value: "ROLE_USER", display: "User"}];

  ngOnInit(): void {
    // this.form.addControl('password', new FormControl(this.userIntialState.));
    this.form.addControl('email', new FormControl(this.userIntialState.email));
    this.form.addControl('country', new FormControl(this.userIntialState.address.countryAlpha2Code));
    this.form.addControl('fullName', new FormControl(this.userIntialState.fullName));
    this.form.addControl('address', new FormControl(this.userIntialState.address.addressValue));
    this.form.addControl('city', new FormControl(this.userIntialState.address.cityName));
    this.form.addControl('zipCode', new FormControl(this.userIntialState.address.zipCode));
    this.form.addControl('role', new FormControl(this.userIntialState.grantedAuthorities.find(a => a.includes("ROLE_ADMIN") || a.includes("ROLE_USER"))));
    this.form.addControl('isDeleted', new FormControl(this.userIntialState.isDeleted));
    this.form.addControl('isEnabled', new FormControl(this.userIntialState.isEnabled));
    this.form.controls.email.addAsyncValidators(this.validateEmailNotTaken.bind(this))
    if (this.userData.getUser()?.email === this.userIntialState.email) {
      this.form.disable();
    }
  }

  validateEmailNotTaken(control: AbstractControl): Observable<{ emailIsTaken: boolean }|null> {
    if (control.value === null || this.userIntialState.email === control.value) {
      // this.form.markAsPristine();
      // this.form.markAsUntouched();
      return of(null)
    }
    return this.authenticationService.checkIfEmailIsTaken(control.value).pipe(
      map(res => {
          return res.isEmailTaken ? { emailIsTaken: true } : null;
        }
      ))
  }

  onSubmit() {
    if (this.form.valid) {
      const dto: RegistrationDto = {
        id: this.userIntialState.id,
        // @ts-ignore
        password: null,
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
      this.onUpdateEmmiter.emit(dto);
    }
  }

  onReset() {
    this.form.reset()
    this.form.markAsPristine()
    this.form.markAsUntouched()
    this.form.setValue({
      email: this.userIntialState.email,
      country: this.userIntialState.address.countryAlpha2Code,
      fullName: this.userIntialState.fullName,
      address: this.userIntialState.address.addressValue,
      city: this.userIntialState.address.cityName,
      zipCode: this.userIntialState.address.zipCode,
      role: this.userIntialState.grantedAuthorities.find(a => a.includes("ROLE_ADMIN") || a.includes("ROLE_USER")),
      isDeleted: this.userIntialState.isDeleted,
      isEnabled: this.userIntialState.isEnabled,
    })
  }

  onDelete() {
    this.onDeleteEmitter.emit(this.userIntialState.id)
  }
}
