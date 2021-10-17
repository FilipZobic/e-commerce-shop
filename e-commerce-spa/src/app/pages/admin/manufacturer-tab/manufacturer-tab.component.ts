import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ManufacturerService} from "../../../services/manufacturer.service";
import {Manufacturer} from "../../../model/manufacturer";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {FormControl, FormGroup} from "@angular/forms";
import {RegistrationDto} from "../../../model/dto/registration-dto";
import {InputValidationService} from "../../../services/input-validation.service";
import {ManufacturerRequestDto} from "../../../model/manufacturer-request-dto";
import {MatTable} from "@angular/material/table";
import {MatTabChangeEvent} from "@angular/material/tabs";

@Component({
  selector: 'app-manufacturer-tab',
  templateUrl: './manufacturer-tab.component.html',
  styleUrls: ['./manufacturer-tab.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ManufacturerTabComponent implements OnInit {

  manufacturers: Manufacturer[] = [];
  selectedTabIndex: number = 0;

  constructor(private manufacturerService: ManufacturerService) {
    manufacturerService.fetchAllManufacturers().subscribe(next => {
      this.manufacturers = next;
    });
  }

  ngOnInit(): void {
  }

  updateManufacturer($event: ManufacturerRequestDto) {
    this.manufacturerService.updateManufacturer($event).subscribe(next => {
      this.manufacturerService.fetchAllManufacturers().subscribe(next => {
        this.manufacturers = next;
      });
    });
  }

  createManufacturer($event: ManufacturerRequestDto) {
    this.manufacturerService.createManufacturer($event).subscribe(next => {
      this.manufacturerService.fetchAllManufacturers().subscribe(next => {
        this.manufacturers = next;
      });
    });
  }

  deleteManufacturer(id: string) {
    this.manufacturerService.deleteManufacturer(id).subscribe(next => {
      this.manufacturerService.fetchAllManufacturers().subscribe(next => {
        this.manufacturers = next;
      });
    });
  }


  tabChange($event: MatTabChangeEvent) {
    this.selectedTabIndex = $event.index
  }

}
