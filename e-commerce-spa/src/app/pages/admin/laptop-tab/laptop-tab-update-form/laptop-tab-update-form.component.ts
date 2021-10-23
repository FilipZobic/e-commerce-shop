import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Laptop} from "../../../../model/laptop";
import {SelectDto} from "../../../../model/select-dto";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-laptop-tab-update-form',
  templateUrl: './laptop-tab-update-form.component.html',
  styleUrls: ['./laptop-tab-update-form.component.scss']
})
export class LaptopTabUpdateFormComponent implements OnInit {

  public image: string | null = null;
  form: FormGroup = new FormGroup({});

  @Output()
  onUpdateEmmiter = new EventEmitter<Laptop>()

  @Input()
    // @ts-ignore
  laptopInitialState: Laptop;

  constructor() {
  }


  ngOnInit(): void {
    this.form.addControl('name', new FormControl(this.laptopInitialState.name));
    this.form.addControl('manufacturerId', new FormControl(this.laptopInitialState.manufacturer.id));
    this.form.addControl('panelType', new FormControl(this.laptopInitialState.panelType));
    this.form.addControl('ram', new FormControl(this.laptopInitialState.ram));
    this.form.addControl('price', new FormControl(this.laptopInitialState.price));
    this.form.addControl('stock', new FormControl(this.laptopInitialState.stock));
    this.form.addControl('diagonal', new FormControl(this.laptopInitialState.diagonal));
    this.image = this.laptopInitialState.image;
  }

  onSubmit() {
    if (this.form.valid && this.image !== null) {
      const form = {
        id: this.laptopInitialState.id,
        ...this.form.value,
        image: this.image
      }
      this.form.markAsPristine()
      this.form.markAsUntouched()
      this.onUpdateEmmiter.emit(form)
    }
  }

  onReset() {
    this.form.setValue({
      "name": this.laptopInitialState.name,
      "manufacturerId": this.laptopInitialState.manufacturer.id,
      "panelType": this.laptopInitialState.panelType,
      "ram": this.laptopInitialState.ram,
      "price": this.laptopInitialState.price,
      "stock": this.laptopInitialState.stock,
      "diagonal": this.laptopInitialState.diagonal,
    })
    this.image = this.laptopInitialState.image;
    this.form.markAsPristine()
    this.form.markAsUntouched()
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
