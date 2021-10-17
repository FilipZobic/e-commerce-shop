import { Component, OnInit } from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTabChangeEvent} from "@angular/material/tabs";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class AdminComponent implements OnInit {
  selectedTab: string = 'USER';
  selectedTabIndex: number = 0;

  constructor() { }

  ngOnInit(): void {
  }

  setTab(type: string) {
    this.selectedTab = type
  }

  tabChange($event: MatTabChangeEvent) {
    this.selectedTabIndex = $event.index
  }
}
