import { Component, OnInit } from '@angular/core';
import {LaptopService} from "../../services/laptop.service";
import {LaptopPagingRequest} from "../../model/laptop-paging-request";
import {Laptop} from "../../model/laptop"
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ManufacturerService} from "../../services/manufacturer.service";
import {Manufacturer} from "../../model/manufacturer";
import {PageResponseLaptop} from "../../model/dto/page-response-laptop";
import {LaptopSearchFormDto} from "../../model/search/laptop-search-form-dto";
import {PaginationFormData} from "../../model/search/pagination-form-data";
import {PageEvent} from "@angular/material/paginator";
import {CartService} from "../../services/cart.service";
import {CartItem, UserData} from "../../model/user-data";
import {UserDataService} from "../../services/user-data.service";
import {BehaviorSubject} from "rxjs";
import {Country} from "../../model/country";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  laptops: Laptop[] = [];
  manufacturers: Manufacturer[] = []
  itemsInCart: {id: string, amount: number}[] = []

  constructor(private laptopService: LaptopService, private sanitization: DomSanitizer, private manufacturerService: ManufacturerService, private cartService: CartService, private userDataService: UserDataService) { }

  ngOnInit(): void {
    // this.cartService.getUserCartInfo(next => {
    //
    // })
    this.laptopService.fetchAllProducts(this.pagingRequest).subscribe(productPage => {
      this.laptops = productPage.page.content;
      this.numberOfLaptops = productPage.page.totalElements
      this.refreshUi(productPage);
      const user = this.userDataService.getUser();
      this.userDataService.cartItems.subscribe(items => {
        this.itemsInCart = items.map<{id: string, amount: number}>(cartItem => {return {id: cartItem.id.laptopId, amount: cartItem.amount}})
      })
      // if (user != null) {
      //   if (user.cart !== null) {
      //   }
      // }
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
    this.fetchLaptopsAgain()
  }
  updatePaginationSearchValue($event: PaginationFormData) {
    this.pagingRequest = {...this.pagingRequest, ...$event}
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

  laptopIsInUserCartChecker(id: string): boolean {
    return this.itemsInCart.some(a => a.id === id)
  }

  getAmountInCart(id: string): number {
    let item = this.itemsInCart.find(a => a.id ===id);
    if (item === undefined) {
      return 0;
    }
    return item.amount
  }

  addLaptopToCart(id: string) {
    this.cartService.addToCart({laptopId: id}).subscribe(next => {
      this.userDataService.updateCartSubject();
    })
  }

  removeLaptopFromCart(id: string) {
    this.cartService.removeFromCart({laptopId: id}).subscribe(next => {
      this.userDataService.updateCartSubject();
    })
  }
}
