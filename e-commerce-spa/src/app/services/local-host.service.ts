import { Injectable } from '@angular/core';
import {UserData} from "../model/user-data";
import {UserService} from "./user.service";
import {UserDataService} from "./user-data.service";

@Injectable({
  providedIn: 'root'
})
export class LocalHostService {

  accessToken: string | null | undefined;

  constructor(private userService: UserService, private userDatService: UserDataService) {
    // const localStorageValue: null | string = localStorage.getItem("e-commerce-website-user");
    // let possibleUser: UserData | null = null;
    // console.log(localStorageValue)
    // if (localStorageValue != null || localStorageValue != undefined) {
    //   possibleUser = JSON.parse(localStorageValue);
    // } else return;
    // if (possibleUser) {
    //   this.accessToken = possibleUser.accessToken;
    //   this.userService.fetchUserById(possibleUser.id).subscribe(userData => {
    //     // @ts-ignore
    //     userDatService.setUser({...userData, accessToken: possibleUser.accessToken});
    //   }, error => {
    //     this.accessToken = null;
    //     localStorage.removeItem("e-commerce-website-user");
    //   });
    // }
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
        this.userDatService.setUser({...userData, accessToken: possibleUser.accessToken});
      }, error => {
        accessToken = null;
        localStorage.removeItem("e-commerce-website-user");
      });
    }
  }
}
