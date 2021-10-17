import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {Observable} from "rxjs";
import {PageResponseLaptop} from "../model/dto/page-response-laptop";
import {LaptopPagingRequest} from "../model/laptop-paging-request";

@Injectable({
  providedIn: 'root'
})
export class LaptopService {

  private uri: string;

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService) {
    this.uri = resource.baseUri + "products/laptops";
  }

  public fetchAllProducts(paramsToChange: LaptopPagingRequest): Observable<PageResponseLaptop> {
    let params = Object.keys(paramsToChange)
      // @ts-ignore
      .filter((k) => paramsToChange[k] != null)
      // @ts-ignore
      .reduce((a, k) => ({ ...a, [k]: paramsToChange[k] }), {});

    // @ts-ignore
    return this.httpClient.get<PageResponseLaptop>(this.uri, {params});
  }
}
