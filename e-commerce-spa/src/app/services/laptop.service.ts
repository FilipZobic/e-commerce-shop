import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {Observable} from "rxjs";
import {PageResponseLaptop} from "../model/dto/page-response-laptop";
import {LaptopPagingRequest} from "../model/laptop-paging-request";
import {Manufacturer} from "../model/manufacturer";
import {Panel} from "../model/panel";
import {ResponsePanelsAndRam} from "../model/response-panels-and-ram";
import {Laptop} from "../model/laptop";
import {UserData} from "../model/user-data";

@Injectable({
  providedIn: 'root'
})
export class LaptopService {

  private readonly uri: string;
  private readonly baseUri: string;

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService) {
    this.baseUri = resource.baseUri;
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

  public createProduct(body: Laptop) {
    return this.httpClient.post<HttpResponse<Laptop>>(this.uri, body, {observe: 'response'})
  }

  public fetchAllPanels() {
    return this.httpClient.get<ResponsePanelsAndRam>(this.baseUri + 'panels');
  }

  public fetchAllRam() {
    return this.httpClient.get<ResponsePanelsAndRam>(this.baseUri + 'ram');
  }

  updateProduct(body: Laptop) {
    return this.httpClient.put<HttpResponse<Laptop>>(this.uri + `/${body.id}`, body, {observe: 'response'})
  }

  deleteProduct(id: string) {
    return this.httpClient.delete<HttpResponse<Laptop>>(this.uri + `/${id}`, {observe: 'response'})
  }
}
