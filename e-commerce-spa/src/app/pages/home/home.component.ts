import { Component, OnInit } from '@angular/core';
import {UserDataService} from "../../services/user-data.service";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public userDataService: UserDataService, public authenticationService: AuthenticationService) { }

  formatLabel(value: number) {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }

    return value;
  }

  logoutEventHandler() {
    const token = this.userDataService.getUser()?.accessToken;
    if (token !== undefined) {
      this.authenticationService.logout(token);
    }
  }


  ngOnInit(): void {

  }

}
