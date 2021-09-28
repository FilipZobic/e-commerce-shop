import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {UserDataService} from "./user-data.service";
import {UserData} from "../model/user-data";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly uri: string;

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService) {
    this.uri = resource.baseUri + "users";
  }

  public fetchUserById(id: string): Observable<UserData> {
    return this.httpClient.get<UserData>(this.uri + "/" + id, );
  }

  public fetchAllUsers(id: string): Observable<UserData[]> {
    return this.httpClient.get<UserData[]>(this.uri);
  }

  // disableAUser
  //
  // updateUser
  //
  // createNewUser
  //
  // fetchAllUserPaging
}
