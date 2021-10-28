import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {ResourceAPIService} from "./resource-api.service";
import {Observable} from "rxjs";
import {Manufacturer} from "../model/manufacturer";
import {UsernameNotTakenDto} from "../model/username-not-taken-dto";
import {ManufacturerRequestDto} from "../model/manufacturer-request-dto";

@Injectable({
  providedIn: 'root'
})
export class ManufacturerService {

  uri: string;

  constructor(private httpClient: HttpClient, resource: ResourceAPIService) {
    this.uri = resource.baseUri + "manufacturers";
  }

  public fetchAllManufacturers(): Observable<Manufacturer[]> {
    return this.httpClient.get<Manufacturer[]>(this.uri);
  }

  checkIfNameIsTaken(name: string): Observable<UsernameNotTakenDto> {
    return this.httpClient.post<UsernameNotTakenDto>(this.uri + "/isNameTaken", {name});
  }

  updateManufacturer(body: ManufacturerRequestDto): Observable<HttpResponse<HttpResponse<Manufacturer>>> {
    return this.httpClient.put<HttpResponse<Manufacturer>>(this.uri + `/${body.id}`, body, {observe: 'response'});
  }

  createManufacturer(body: ManufacturerRequestDto) {
    return this.httpClient.post<HttpResponse<Manufacturer>>(this.uri , body, {observe: 'response'});
  }

  deleteManufacturer(id: string) {
    return this.httpClient.delete<HttpResponse<Manufacturer>>(this.uri + `/${id}`, {observe: 'response'});
  }
}
