import { Component, OnInit } from '@angular/core';
import {LaptopService} from "../../services/laptop.service";
import {LaptopPagingRequest} from "../../model/laptop-paging-request";
import {Laptop} from "../../model/laptop"
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ManufacturerService} from "../../services/manufacturer.service";
import {Manufacturer} from "../../model/manufacturer";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

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

  laptops: Laptop[] = [];
  manufacturers: Manufacturer[] = []

  constructor(private laptopService: LaptopService, private sanitization: DomSanitizer, private manufacturerService: ManufacturerService) { }

  ngOnInit(): void {
    this.laptopService.fetchAllProducts(this.pagingRequest).subscribe(productPage => {
      this.laptops = productPage.content
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
}
