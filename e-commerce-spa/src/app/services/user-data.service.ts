import {Injectable} from '@angular/core';
import {CartItem, UserData} from "../model/user-data";
import {Router} from "@angular/router";
import {UserDataUtil} from "../util/user-data-util";
import {UserService} from "./user.service";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserDataService  {

  constructor(private router: Router, private userService: UserService) {

  }


  private user: UserData | null = null;

  public displayProfileName: string | null = null;

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
    this.updateCartSubject();
    localStorage.setItem("e-commerce-website-user", JSON.stringify(this.user));

    if (this.user.grantedAuthorities.find(a => a === "ROLE_ADMIN")) {
      this.router.navigate(['/admin'])
    } else {
      this.router.navigate(['/'])
    }

    this.userService.fetchUserById(this.user.id).subscribe(userData => {
      console.log("Token Works");
      console.log(userData);
      })
  }

  logout() {
    if (this.accessToken !== undefined) {
      this.userService.logout(this.accessToken)
      this.setUser(null)
    }
  }

  cartItems: BehaviorSubject<CartItem[]> = new BehaviorSubject<CartItem[]>([]);

  updateCartSubject() {
    let user = this.user;
    if (user != null) {
      this.userService.fetchUserById(user.id).subscribe(next => {
        this.cartItems.next((next.cart ? next.cart : []));
      })
    }
  }
}
