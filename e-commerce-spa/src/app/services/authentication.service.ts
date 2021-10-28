import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {ResourceAPIService} from "./resource-api.service";
import {UserData} from "../model/user-data";
import {UserLoginDto} from "../model/dto/user-login-dto";
import {UserDataService} from "./user-data.service";
import {UsernameNotTakenDto} from "../model/username-not-taken-dto";
import {Observable} from "rxjs";
import {RegistrationDto} from "../model/dto/registration-dto";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private uri: string;

  public X_ACCESS_TOKEN_HEADER = "X-ACCESS-TOKEN";

  constructor(private httpClient: HttpClient, private router: Router, resource: ResourceAPIService, private userDataService: UserDataService, private userService: UserService) {
    console.log("CREATING LOCAL Auth SERVICE")
    this.uri = resource.baseUri;
    this.attemptLocalHostAuth();
  }

  public attemptLocalHostAuth() {
    const localStorageValue: null | string = localStorage.getItem("e-commerce-website-user");
    let possibleUser: UserData | null = null;
    console.log(localStorageValue)
    if (localStorageValue != null || localStorageValue != undefined) {
      possibleUser = JSON.parse(localStorageValue);
    } else return;
    if (possibleUser) {
      let accessToken: string | null = possibleUser.accessToken;
      this.userService.fetchUserLocalHost(possibleUser.id, accessToken).subscribe(userData => {
        // @ts-ignore
        this.userDataService.setUser({...userData, accessToken: possibleUser.accessToken});
      }, error => {
        accessToken = null;
        localStorage.removeItem("e-commerce-website-user");
      });
    }
  }

  public attemptLogin(body: UserLoginDto): void {
    this.httpClient.post<HttpResponse<UserData>>(this.uri + "login", body, {observe: 'response'}).subscribe(
      response => {
        const accessToken = response.headers.get(this.X_ACCESS_TOKEN_HEADER);
        // @ts-ignore
        this.userDataService.setUser({...response.body, accessToken});
      });
  }

  // public logout(accessToken: string) {
  //   this.httpClient.delete<HttpResponse<any>>(this.uri + "logout",
  //     {
  //       observe: 'response',
  //       headers: {"X-ACCESS-TOKEN": accessToken},
  //       withCredentials:  true
  //     }
  //   ).subscribe()
  //   this.userDataService.setUser(null);
  // }

  checkIfEmailIsTaken(email: string): Observable<UsernameNotTakenDto> {
    return this.httpClient.post<UsernameNotTakenDto>(this.uri + "isEmailTaken", {email});
  }

  attemptRegistration(body: RegistrationDto): void {
    this.httpClient.post<HttpResponse<UserData>>(this.uri + "register", body, {observe: 'response'}).subscribe(
      response => {
        const accessToken = response.headers.get(this.X_ACCESS_TOKEN_HEADER);
        // @ts-ignore
        this.userDataService.setUser({...response.body, accessToken});
      });
  }
}
