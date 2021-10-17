import {Component, Input, OnInit} from '@angular/core';
import {Manufacturer} from "../../model/manufacturer";

@Component({
  selector: 'app-laptop-search-form',
  templateUrl: './laptop-search-form.component.html',
  styleUrls: ['./laptop-search-form.component.scss']
})
export class LaptopSearchFormComponent implements OnInit {

  constructor() { }

  @Input()
  manufacturers: Manufacturer[] = []

  ngOnInit(): void {
  }

  formatLabel(value: number) {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }

    return value;
  }

}
