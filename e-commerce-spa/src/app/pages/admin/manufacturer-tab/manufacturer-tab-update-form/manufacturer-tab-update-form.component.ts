import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {Manufacturer} from "../../../../model/manufacturer";
import {InputValidationService} from "../../../../services/input-validation.service";
import {map} from "rxjs/operators";
import {ManufacturerService} from "../../../../services/manufacturer.service";
import {Observable, of} from "rxjs";
import {ManufacturerRequestDto} from "../../../../model/manufacturer-request-dto";
import {MatTable} from "@angular/material/table";

@Component({
  selector: 'app-manufacturer-tab-update-form',
  templateUrl: './manufacturer-tab-update-form.component.html',
  styleUrls: ['./manufacturer-tab-update-form.component.scss']
})
export class ManufacturerTabUpdateFormComponent implements OnInit {

  form: FormGroup = new FormGroup({});

  @Input()
  // @ts-ignore
  manufacturer: Manufacturer;

  @Output()
  onUpdateEmitter = new EventEmitter<ManufacturerRequestDto>()
  @Output()
  onDeleteEmitter = new EventEmitter<string>()

  constructor(private manufacturerService: ManufacturerService) { }

  ngOnInit(): void {
    this.form.addControl('name', new FormControl(this.manufacturer.name));
    this.form.controls.name.addAsyncValidators(this.validateManufacturerNameNotTaken.bind(this))
  }

  validateManufacturerNameNotTaken(control: AbstractControl): Observable<{ valueIsTaken: boolean }|null> {
    if (control.value === null || this.manufacturer.name === control.value) {
      this.form.markAsPristine();
      this.form.markAsUntouched();
      return of(null)
    }
    return this.manufacturerService.checkIfNameIsTaken(control.value).pipe(
      map(res => {
          return res.isManufacturerNameTaken ? { valueIsTaken: true } : null;
        }
      ))
  }

  onSubmit() {
    if (this.form.valid) {
      this.onUpdateEmitter.emit({name: this.form.value.name, id: this.manufacturer.id});
    }
  }

  onDelete(id: string) {
    this.onDeleteEmitter.emit(id);
  }
}
