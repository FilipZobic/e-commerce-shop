import {AfterViewInit, Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatTabChangeEvent} from "@angular/material/tabs";
import {Laptop} from "../../../model/laptop";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {LaptopPagingRequest} from "../../../model/laptop-paging-request";
import {LaptopService} from "../../../services/laptop.service";
import {Manufacturer} from "../../../model/manufacturer";
import {ManufacturerService} from "../../../services/manufacturer.service";
import {PageResponseLaptop} from "../../../model/dto/page-response-laptop";
import {LaptopSearchFormDto} from "../../../model/search/laptop-search-form-dto";
import {LaptopPaginationSearchFormDto} from "../../../model/search/laptop-pagination-search-form-dto";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {SelectDto} from "../../../model/select-dto";
import {capitalize} from "lodash";

@Component({
  selector: 'app-laptop-tab',
  templateUrl: './laptop-tab.component.html',
  styleUrls: ['./laptop-tab.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class LaptopTabComponent implements OnInit {

  selectedTabIndex: number = 0;
  laptops: Laptop[] = [];
  manufacturers: Manufacturer[] = [];
  ceil: number = 0;

  manufacturersSelectDtos: SelectDto[] = [];
  panelSelectDtos: SelectDto[] = [];
  ramSelectDtos: SelectDto[] = [];

  // @ts-ignore
  @ViewChild('paginator') paginator: MatPaginator;

  pagingRequest: LaptopPagingRequest = {
    productName: null,
    manufacturerId: null,
    maxPrice: null,
    minPrice: null,
    shouldReturnImage: true,
    sortByProperty: null,
    page: 0,
    size: 10
  }

  columnsToDisplay = ['id'];
  expandedElement: Manufacturer | null | undefined;
  numberOfLaptops: number = 0;

  constructor(private laptopService: LaptopService, private manufacturerService: ManufacturerService) {
    laptopService.fetchAllProducts(this.pagingRequest).subscribe(next => {
      this.laptops = next.page.content
      this.numberOfLaptops = next.page.totalElements
      this.refreshUi(next);
    }, error => {}, ()=> {

    })
    this.manufacturerService.fetchAllManufacturers().subscribe(next => {
      this.manufacturers = next;
      this.manufacturersSelectDtos = this.manufacturers.map(m => {return {value: m.id, display: `${m.name} (${m.numberOfProducts})`}})
    })

    this.laptopService.fetchAllPanels().subscribe(next => {
      this.panelSelectDtos = next['panels'].map(m => {return {value: m.tag, display: `${capitalize(m.name)}`}})
    })

    this.laptopService.fetchAllRam().subscribe(next => {
      this.ramSelectDtos = next['ram'].map(m => {return {value: m, display: `${m} GB`}})
    })
  }

  ngOnInit(): void {
  }

  onCreateHandler($event: Laptop) {
    this.laptopService.createProduct($event).subscribe(next => {

    }, () => {}, () => {
      this.fetchLaptopsAgain();
    })
  }

  onUpdateHandler($event: Laptop) {
    this.laptopService.updateProduct($event).subscribe(next => {

    }, () => {}, () => {
      this.fetchLaptopsAgain();
    })
  }

  tabChange($event: MatTabChangeEvent) {
    this.selectedTabIndex = $event.index
  }

  private refreshUi(next: PageResponseLaptop) {
    this.ceil = next.priceCeiling;
  }

  updateFormSearchValue($event: LaptopSearchFormDto) {
    this.pagingRequest = {...this.pagingRequest, ...$event, page: 0}
    this.paginator.firstPage()
    console.log(this.pagingRequest)
    this.fetchLaptopsAgain()
  }
  updatePaginationSearchValue($event: LaptopPaginationSearchFormDto) {
    this.pagingRequest = {...this.pagingRequest, ...$event}
    console.log(this.pagingRequest)
    this.fetchLaptopsAgain()
  }

  getPaginatorData($event: PageEvent) {
    this.updatePaginationSearchValue({page: $event.pageIndex, size: $event.pageSize});
  }

  fetchLaptopsAgain() {
    this.laptopService.fetchAllProducts(this.pagingRequest).subscribe(next => {
      this.laptops = next.page.content
      this.numberOfLaptops = next.page.totalElements
      this.refreshUi(next);
    }, error => {}, ()=> {

    })
  }

  onPaginationChange() {

  }
}
