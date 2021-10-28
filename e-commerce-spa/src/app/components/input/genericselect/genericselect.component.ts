import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from "@angular/forms";
import {SelectDto} from "../../../model/select-dto";

@Component({
  selector: 'app-genericselect',
  templateUrl: './genericselect.component.html',
  styleUrls: ['./genericselect.component.scss']
})
export class GenericselectComponent implements OnInit {



  constructor() { }

  @Input()// @ts-ignore
  formControlNameInput: string;

  @Input()
  selectDtos: SelectDto[] = []

  @Input()// @ts-ignore
  formGroup: FormGroup;

  @Input()// @ts-ignore
  label: string;

  ngOnInit(): void {
    this.formGroup.controls[this.formControlNameInput].setValidators([Validators.required]);
  }

}
