import { Component, OnInit } from '@angular/core';
import {MatTabChangeEvent} from "@angular/material/tabs";

@Component({
  selector: 'app-laptop-tab',
  templateUrl: './laptop-tab.component.html',
  styleUrls: ['./laptop-tab.component.scss']
})
export class LaptopTabComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  tabChange($event: MatTabChangeEvent) {

  }
}
