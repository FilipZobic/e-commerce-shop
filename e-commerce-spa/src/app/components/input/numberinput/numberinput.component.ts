import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";

@Component({
  selector: 'app-numberinput',
  templateUrl: './numberinput.component.html',
  styleUrls: ['./numberinput.component.scss']
})
export class NumberinputComponent implements OnInit {

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
  minValue: number | null = null;

  @Input()
  maxValue: number | null = null;

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
    if (this.maxValue !== null) {
      validators.push(Validators.max(this.maxValue));
    }
    if (this.minValue !== null) {
      validators.push(Validators.min(this.minValue));
    }
    this.formGroup.controls[this.formControlNameInput].setValidators(validators);
  }

}
