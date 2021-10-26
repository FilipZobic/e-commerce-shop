import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {UserData} from "../model/user-data";
import {Observable} from "rxjs";
import {RegistrationDto} from "../model/dto/registration-dto";
import {UserPage} from "../model/user-page";
import {UserPagingRequest} from "../model/user-paging-request";

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

  public fetchAllUsers(paramsToChange: UserPagingRequest): Observable<UserPage> {
    let params = Object.keys(paramsToChange)
      // @ts-ignore
      .filter((k) => paramsToChange[k] != null && paramsToChange[k].length !== 0)
      // @ts-ignore
      .reduce((a, k) => ({ ...a, [k]: paramsToChange[k] }), {});
    // @ts-ignore
    return this.httpClient.get<UserPage>(this.uri, {params});
  }

  logout(accessToken: any) {
    this.httpClient.delete<HttpResponse<any>>(this.logoutUrl,
      {
        observe: 'response',
        headers: {"X-ACCESS-TOKEN": accessToken},
        withCredentials:  true
      }
    ).subscribe()
  }

  postUser(body: RegistrationDto) {
    return this.httpClient.post<HttpResponse<UserData>>(this.uri , body, {observe: 'response'});
  }

  putUser(body: RegistrationDto) {
    return this.httpClient.put<HttpResponse<UserData>>(this.uri + `/${body.id}` , body, {observe: 'response'});
  }
}
