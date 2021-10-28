import {Component, Input, OnInit} from '@angular/core';
import {Country} from "../../../model/country";
import {FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-country-select',
  templateUrl: './country-select.component.html',
  styleUrls: ['./country-select.component.scss']
})
export class CountrySelectComponent implements OnInit {

  constructor() { }

  @Input()
  countries: Country[] = []

  @Input()// @ts-ignore
  formGroup: FormGroup;

  ngOnInit(): void {
    this.formGroup.controls.country.setValidators([Validators.required]);
  }

}
