import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {BehaviorSubject, Observable} from "rxjs";
import {Country} from "../model/country";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private readonly uri: string;

  public countries: Country[] = [];

  itemsSubject: BehaviorSubject<Country[]> = new BehaviorSubject<Country[]>([]);

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService) {
    this.uri = resource.baseUri + "countries";
    console.log("FETCHING COUNTRIES")
    this.fetchAllCountries().subscribe(countries=> {
      console.log(countries)
      this.countries = countries;
      this.itemsSubject.next(countries);
    })
  }

  public fetchAllCountries(): Observable<Country[]> {
    return this.httpClient.get<Country[]>(this.uri);
  }

}
