import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {UserData} from "../model/user-data";
import {UserLoginDto} from "../model/dto/user-login-dto";
import {UserDataService} from "./user-data.service";
import {UsernameNotTakenDto} from "../model/username-not-taken-dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private uri: string;

  public X_ACCESS_TOKEN_HEADER = "X-ACCESS-TOKEN";

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService, private userDataService: UserDataService) {
    this.uri = resource.baseUri;
  }

  public attemptLogin(body: UserLoginDto): void {
    this.httpClient.post<HttpResponse<UserData>>(this.uri + "login", body, {observe: 'response'}).subscribe(
      response => {
        const accessToken = response.headers.get(this.X_ACCESS_TOKEN_HEADER);
        console.log(response.headers)
        // @ts-ignore
        this.userDataService.setUser({...response.body, accessToken});
      }, error => {
        // to be continued
        console.log(error)
      });
  }

  public logout(accessToken: string) {
    console.log(accessToken)
    this.httpClient.delete<HttpResponse<any>>(this.uri + "logout",
      {
        observe: 'response',
        headers: {"X-ACCESS-TOKEN": accessToken},
        withCredentials:  true
      }
    ).subscribe()
    this.userDataService.setUser(null);
  }

  checkIfEmailIsTaken(email: string): Observable<UsernameNotTakenDto> {
    return this.httpClient.post<UsernameNotTakenDto>(this.uri + "isEmailTaken", {email});
  }
}
