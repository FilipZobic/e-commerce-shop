import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {CartResponseDto} from "../model/cart-response-dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private readonly uri: string;
  private readonly baseUri: string;

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService) {
    this.baseUri = resource.baseUri;
    this.uri = resource.baseUri + "products/laptops" + '/cart';
  }

  public getUserCartInfo(paramsToChange: any): Observable<CartResponseDto[]> {
    let params = Object.keys(paramsToChange)
      // @ts-ignore
      .filter((k) => paramsToChange[k] != null)
      // @ts-ignore
      .reduce((a, k) => ({ ...a, [k]: paramsToChange[k] }), {});

    return this.httpClient.get<CartResponseDto[]>(this.uri, {params});
  }

  addToCart(paramsToChange: {laptopId: string}): Observable<CartResponseDto>  {
    let params = Object.keys(paramsToChange)
      // @ts-ignore
      .filter((k) => paramsToChange[k] != null)
      // @ts-ignore
      .reduce((a, k) => ({ ...a, [k]: paramsToChange[k] }), {});

    return this.httpClient.post<CartResponseDto>(this.uri, null, {params});
  }

  removeFromCart(paramsToChange: {laptopId: string}): Observable<CartResponseDto>  {
    let params = Object.keys(paramsToChange)
      // @ts-ignore
      .filter((k) => paramsToChange[k] != null)
      // @ts-ignore
      .reduce((a, k) => ({ ...a, [k]: paramsToChange[k] }), {});

    return this.httpClient.delete<CartResponseDto>(this.uri, {params});
  }
}
