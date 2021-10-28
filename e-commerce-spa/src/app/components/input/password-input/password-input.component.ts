import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-password-input',
  templateUrl: './password-input.component.html',
  styleUrls: ['./password-input.component.scss']
})
export class PasswordInputComponent implements OnInit {

  @Input()
  placeholder = 'Enter your password';

  @Input()
  formControlNameInput = 'password';

  @Input()// @ts-ignore
  formGroup: FormGroup;

  @Input()
  minLength = 6;

  hide = true;
  // matcher = new MyErrorStateMatcher();
  //
  constructor() { }

  ngOnInit(): void {
    // @ts-ignore
    // this.formGroup.addControl(this.formControlName, this.passwordFormControl);
    this.formGroup.controls[this.formControlNameInput].setValidators([
      Validators.required,
      Validators.minLength(this.minLength)
    ]);
  }

  toggleHide(): void {
    this.hide = !this.hide;
  }

}
