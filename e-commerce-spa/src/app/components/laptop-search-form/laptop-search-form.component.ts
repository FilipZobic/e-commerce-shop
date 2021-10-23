import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Manufacturer} from "../../model/manufacturer";
import {LabelType, Options} from "ng5-slider";
import {FormControl, FormGroup} from "@angular/forms";
import {LaptopSearchFormDto} from "../../model/search/laptop-search-form-dto";

@Component({
  selector: 'app-laptop-search-form',
  templateUrl: './laptop-search-form.component.html',
  styleUrls: ['./laptop-search-form.component.scss']
})
export class LaptopSearchFormComponent implements OnInit, OnChanges {

  constructor() { }


  @Output()
  onEventActivateEmitter = new EventEmitter<LaptopSearchFormDto>()

  form: FormGroup = new FormGroup({});

  @Input()
  manufacturers: Manufacturer[] = []

  ngOnInit(): void {
    this.form.addControl('productName', new FormControl(null));
    this.form.addControl('manufacturerId', new FormControl(null));
  }

  clear(): void {
    this.form.reset()
    this.form.markAsPristine()
    this.form.markAsUntouched()
    this.minPrice = 0;
    this.maxPrice = this.ceil;
  }

  submit(): void {
    this.onEventActivateEmitter.emit({...this.form.value, minPrice: this.minPrice-1, maxPrice: this.maxPrice+1});
  }

  ngOnChanges(changes: SimpleChanges): void {
    const change = changes["ceil"];
    if (change != undefined) {
      if (this.minPrice > change.currentValue) {
        this.minPrice = 0;
      }

      if (this.maxPrice == 0 && change.previousValue !== change.currentValue) {
        this.maxPrice = change.currentValue
      }
      this.options = {
        ...this.options,
        floor: 0,
        ceil: change.currentValue
      }
    }
  }

  minPrice: number = 0;
  @Input()
  ceil = 0;
  maxPrice: number = this.ceil;
  options: Options = {
    floor: 0,
    ceil: this.ceil,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:
          return '<b>Min price:</b> ' + value;
        case LabelType.High:
          return '<b>Max price:</b> ' + value;
        default:
          return value + " $";
      }
    }
  };
}
