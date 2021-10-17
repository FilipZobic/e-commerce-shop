import { Injectable } from '@angular/core';
import {AbstractControl, FormGroup} from "@angular/forms";
import {map} from "rxjs/operators";
import {AuthenticationService} from "./authentication.service";
import {ManufacturerService} from "./manufacturer.service";

@Injectable({
  providedIn: 'root'
})
export class InputValidationService {

  constructor(private authenticationService: AuthenticationService, private manufacturerService: ManufacturerService) { }

  validateUsernameNotTaken(control: AbstractControl, value = "") {
    return this.authenticationService.checkIfEmailIsTaken(control.value).pipe(
      map(res => {
          return res.isEmailTaken ? { emailIsTaken: true } : null;
        }
      ))
  }

  validateManufacturerNameNotTaken(control: AbstractControl) {
    return this.manufacturerService.checkIfNameIsTaken(control.value).pipe(
      map(res => {
          return res.isManufacturerNameTaken ? { valueIsTaken: true } : null;
        }
      ))
  }


  passwordMatchValidator(password: string, confirmPassword: string) {
    // @ts-ignore
    return (formGroup: FormGroup) => {
      const passwordControl = formGroup.controls[password];
      const confirmPasswordControl = formGroup.controls[confirmPassword];

      if (!passwordControl || !confirmPasswordControl) {
        return;
      }

      if (
        confirmPasswordControl.errors &&
        !confirmPasswordControl.errors.passwordMismatch
      ) {
        return;
      }

      if (passwordControl.value !== confirmPasswordControl.value) {
        confirmPasswordControl.setErrors({ passwordMismatch: true });
      } else {
        confirmPasswordControl.setErrors(null);
      }
    };
  }
}
