import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Manufacturer} from "../../../../model/manufacturer";
import {FormControl, FormGroup} from "@angular/forms";
import {InputValidationService} from "../../../../services/input-validation.service";
import {ManufacturerRequestDto} from "../../../../model/manufacturer-request-dto";

@Component({
  selector: 'app-manufacturer-tab-create-form',
  templateUrl: './manufacturer-tab-create-form.component.html',
  styleUrls: ['./manufacturer-tab-create-form.component.scss']
})
export class ManufacturerTabCreateFormComponent implements OnInit {

  @Output()
  onCreateEmitter = new EventEmitter<ManufacturerRequestDto>()

  form: FormGroup = new FormGroup({});


  constructor(private inputValidationService: InputValidationService) {
  }

  ngOnInit(): void {
    this.form.addControl('name', new FormControl(null));
    this.form.controls.name.addAsyncValidators(this.inputValidationService.validateManufacturerNameNotTaken.bind(this.inputValidationService))
  }

  onSubmit() {
    if (this.form.valid) {
      this.onCreateEmitter.emit(this.form.value);
      this.form.reset();
    }
  }
}
