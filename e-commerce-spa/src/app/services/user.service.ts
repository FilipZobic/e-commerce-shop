import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {UserDataService} from "./user-data.service";
import {UserData} from "../model/user-data";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly logoutUrl: string;
  private readonly uri: string;

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService) {
    this.logoutUrl = resource.baseUri + "logout";
    this.uri = resource.baseUri + "users";
  }

  public fetchUserById(id: string): Observable<UserData> {
    return this.httpClient.get<UserData>(this.uri + "/" + id, );
  }

  public fetchUserLocalHost(id: string, accessToken: string): Observable<UserData> {
    let headers = new HttpHeaders({"X-ACCESS-TOKEN": accessToken});
    return this.httpClient.get<UserData>(this.uri + "/" + id, {headers: headers});
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
  logout(accessToken: any) {
    this.httpClient.delete<HttpResponse<any>>(this.logoutUrl,
      {
        observe: 'response',
        headers: {"X-ACCESS-TOKEN": accessToken},
        withCredentials:  true
      }
    ).subscribe()
  }
}
