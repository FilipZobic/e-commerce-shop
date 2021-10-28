import { Component, OnInit } from '@angular/core';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {CartService} from "../../services/cart.service";
import {UserDataService} from "../../services/user-data.service";
import {CartResponseDto} from "../../model/cart-response-dto";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  constructor(private sanitization: DomSanitizer, private cartService: CartService, private userDataService: UserDataService) {
    this.getCurrentUserCartItem()
  }

  laptops: CartResponseDto[] = []

  ngOnInit(): void {
  }


  roundTo(number: number, places: number) {
    const factor = 10 ** places;
    return Math.round(number * factor) / factor;
  }

  parseImage(image: string): SafeUrl {
    return this.sanitization.bypassSecurityTrustUrl("data:image/png;base64, " + image);
  }


  getCurrentUserCartItem() {
    this.cartService.getUserCartInfo({}).subscribe(next => {
      this.laptops = next;
    })
  }

  addLaptopToCart(id: string) {
    this.cartService.addToCart({laptopId: id}).subscribe(next => {
      this.userDataService.updateCartSubject();
      this.getCurrentUserCartItem()
    })
  }

  removeLaptopFromCart(id: string) {
    this.cartService.removeFromCart({laptopId: id}).subscribe(next => {
      this.userDataService.updateCartSubject();
      this.getCurrentUserCartItem()
    })
  }
}
