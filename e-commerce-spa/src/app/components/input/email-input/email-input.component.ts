import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-email-input',
  templateUrl: './email-input.component.html',
  styleUrls: ['./email-input.component.scss']
})
export class EmailInputComponent implements OnInit {
  @Input()
  placeholder = 'Enter your email';

  formControlName = 'email';

  @Input()// @ts-ignore
  formGroup: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.formGroup.controls.email.setValidators([Validators.required,
      Validators.email]);
  }

}
