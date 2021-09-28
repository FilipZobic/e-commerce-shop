import {Injectable, OnInit} from '@angular/core';
import {UserData} from "../model/user-data";
import {Router} from "@angular/router";
import {UserDataUtil} from "../util/user-data-util";
import {UserService} from "./user.service";
import {parseSelectorToR3Selector} from "@angular/compiler/src/core";

@Injectable({
  providedIn: 'root'
})
export class UserDataService implements OnInit {

  constructor(private router: Router, private userService: UserService) {
    const localStorageValue: null | string = localStorage.getItem("e-commerce-website-user");
    let possibleUser: UserData | null = null;
    if (localStorageValue) {
      possibleUser = JSON.parse(localStorageValue);
    } else return;
    if (possibleUser) {
      this.accessToken = possibleUser.accessToken;
      this.userService.fetchUserById(possibleUser.id).subscribe(userData => {
        // @ts-ignore
        this.setUser({...userData, accessToken: possibleUser.accessToken});
        console.log("GET USER")
        console.log(userData)
        this.router.navigate(['/'])
      }, error => {
        this.accessToken = null;
        localStorage.removeItem("e-commerce-website-user");
      });
    }
  }

  ngOnInit(): void {
  }

  private user: UserData | null = null;

  public displayProfileName: String | null = null;

  public accessToken: string | null = null;

  public getUser(): UserData | null {
    return this.user;
  }

  // try to load user data from json if it works send request if 200 then redirect if any error then leave at login page also do it for home page based on UUID

  public setUser(userData: UserData | null): void {
  if (userData === null){
    this.accessToken = null;
    this.user = null;
    this.displayProfileName = "";
    localStorage.removeItem("e-commerce-website-user");
    this.router.navigate(['/authentication'])
    return;
  }
    this.accessToken = userData.accessToken
    this.user = userData;
    this.displayProfileName = UserDataUtil.generateName(userData)
    localStorage.setItem("e-commerce-website-user", JSON.stringify(this.user));
    this.router.navigate(['/'])

    this.userService.fetchUserById(this.user.id).subscribe(userData => {
      console.log("Token Works");
      console.log(userData);
      })

    this.userService.fetchAllUsers(this.user.id).subscribe(userData => {
      userData.forEach((a) => console.log(a))
    })
  }
}
