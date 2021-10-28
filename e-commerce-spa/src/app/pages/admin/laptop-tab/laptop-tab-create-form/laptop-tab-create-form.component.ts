import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SelectDto} from "../../../../model/select-dto";
import {Laptop} from "../../../../model/laptop";

@Component({
  selector: 'app-laptop-tab-create-form',
  templateUrl: './laptop-tab-create-form.component.html',
  styleUrls: ['./laptop-tab-create-form.component.scss']
})
export class LaptopTabCreateFormComponent implements OnInit {

  public image: string | null = null;
  form: FormGroup = new FormGroup({});

  @Output()
  onCreateEmitter = new EventEmitter<Laptop>()

  constructor() {
  }


  ngOnInit(): void {
    this.form.addControl('name', new FormControl(null));
    this.form.addControl('manufacturerId', new FormControl(null));
    this.form.addControl('panelType', new FormControl(null));
    this.form.addControl('ram', new FormControl(null));
    this.form.addValidators(Validators.required);
    this.form.addControl('price', new FormControl(null));
    this.form.addControl('stock', new FormControl(null));
    this.form.addControl('diagonal', new FormControl(null));
  }

  onSubmit() {
    if (this.form.valid && this.image !== null) {
      const form = {
        ...this.form.value,
        image: this.image
      }
      this.form.reset()
      this.form.markAsPristine()
      this.form.markAsUntouched()
      this.image = null;
      this.onCreateEmitter.emit(form)
    }
  }

  @Input()
  manufacturersSelectDtos: SelectDto[] = [];
  @Input()
  panelSelectDtos: SelectDto[] = [];
  @Input()
  ramSelectDtos: SelectDto[] = [];


  handleImage(file: string): void {
    this.image = file;
  }
}
