import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Manufacturer} from "../../../../model/manufacturer";
import {ManufacturerRequestDto} from "../../../../model/manufacturer-request-dto";
import {MatTable} from "@angular/material/table";

@Component({
  selector: 'app-manufacturer-tab-table',
  templateUrl: './manufacturer-tab-table.component.html',
  styleUrls: ['./manufacturer-tab-table.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ManufacturerTabTableComponent implements OnInit {

  @Output()
  onUpdateEmitter = new EventEmitter<ManufacturerRequestDto>()

  @Input()
  manufacturers: Manufacturer[] = [];
  columnsToDisplay = ['id'];
  expandedElement: Manufacturer | null | undefined;


  @ViewChild('myTable') myTable!: MatTable<any>;

  @Output()
  onDeleteEmitter = new EventEmitter<string>()

  constructor(private changeDetectorRefs: ChangeDetectorRef) { }

  ngOnInit(): void {
  }

  updateManufacturer($event: ManufacturerRequestDto) {
    this.onUpdateEmitter.emit({...$event})
  }
}
