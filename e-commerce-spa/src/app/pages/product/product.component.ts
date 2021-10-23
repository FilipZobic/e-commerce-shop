import { Component, OnInit } from '@angular/core';
import {LaptopService} from "../../services/laptop.service";
import {LaptopPagingRequest} from "../../model/laptop-paging-request";
import {Laptop} from "../../model/laptop"
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ManufacturerService} from "../../services/manufacturer.service";
import {Manufacturer} from "../../model/manufacturer";
import {PageResponseLaptop} from "../../model/dto/page-response-laptop";
import {LaptopSearchFormDto} from "../../model/search/laptop-search-form-dto";
import {LaptopPaginationSearchFormDto} from "../../model/search/laptop-pagination-search-form-dto";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  laptops: Laptop[] = [];
  manufacturers: Manufacturer[] = []

  constructor(private laptopService: LaptopService, private sanitization: DomSanitizer, private manufacturerService: ManufacturerService) { }

  ngOnInit(): void {
    this.laptopService.fetchAllProducts(this.pagingRequest).subscribe(productPage => {
      this.laptops = productPage.page.content;
      this.numberOfLaptops = productPage.page.totalElements
      this.refreshUi(productPage);
    })
    this.manufacturerService.fetchAllManufacturers().subscribe(manufacturers => {
      this.manufacturers = manufacturers
    })
  }

  roundTo(number: number, places: number) {
    const factor = 10 ** places;
    return Math.round(number * factor) / factor;
  }

  parseImage(image: string): SafeUrl {
    return this.sanitization.bypassSecurityTrustUrl("data:image/png;base64, " + image);
  }

  // Paginator
  ceil: number = 0;
  numberOfLaptops: number = 0;

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

  private refreshUi(next: PageResponseLaptop) {
    this.ceil = next.priceCeiling;
  }

  updateFormSearchValue($event: LaptopSearchFormDto) {
    this.pagingRequest = {...this.pagingRequest, ...$event, page: 0}
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
}
