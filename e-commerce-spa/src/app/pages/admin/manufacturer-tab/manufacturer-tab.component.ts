import {Component, OnInit } from '@angular/core';
import {ManufacturerService} from "../../../services/manufacturer.service";
import {Manufacturer} from "../../../model/manufacturer";
import {ManufacturerRequestDto} from "../../../model/manufacturer-request-dto";
import {MatTabChangeEvent} from "@angular/material/tabs";

@Component({
  selector: 'app-manufacturer-tab',
  templateUrl: './manufacturer-tab.component.html',
  styleUrls: ['./manufacturer-tab.component.scss']
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
    this.manufacturerService.updateManufacturer($event).subscribe({complete: () => {
        this.manufacturerService.fetchAllManufacturers().subscribe(next => {
          this.manufacturers = next;
        });
      }});
  }

  createManufacturer($event: ManufacturerRequestDto) {
    this.manufacturerService.createManufacturer($event).subscribe({complete: () => {
        this.manufacturerService.fetchAllManufacturers().subscribe(next => {
          this.manufacturers = next;
        });
      }});
  }

  deleteManufacturer(id: string) {
    this.manufacturerService.deleteManufacturer(id).subscribe({complete: () => {
        this.manufacturerService.fetchAllManufacturers().subscribe(next => {
          this.manufacturers = next;
        });
      }});
  }


  tabChange($event: MatTabChangeEvent) {
    this.selectedTabIndex = $event.index
  }

}
