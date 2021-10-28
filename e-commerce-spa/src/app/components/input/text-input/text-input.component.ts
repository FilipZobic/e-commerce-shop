import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";

@Component({
  selector: 'app-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss']
})
export class TextInputComponent implements OnInit {

  @Input()// @ts-ignore
  formControlNameInput: string;

  @Input()// @ts-ignore
  label: string;

  @Input()// @ts-ignore
  formGroup: FormGroup;

  @Input()// @ts-ignore
  errorName: string;

  @Input()
  isRequired = false;

  @Input()
  pattern: string = '';

  @Input()
  valueIsTakenMessage: string = '';

  @Input()
  valueIsTakenMessageCapital: string = '';



  constructor() { }

  ngOnInit(): void {

    const validators: ((control: AbstractControl) => (ValidationErrors | null))[] = []

    if (this.isRequired) {
      validators.push(Validators.required);
    }
    if (this.pattern !== null) {
      validators.push(Validators.pattern(this.pattern));
    }
    this.formGroup.controls[this.formControlNameInput].setValidators(validators);
  }
}
