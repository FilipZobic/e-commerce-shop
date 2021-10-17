import { Component, OnInit } from '@angular/core';
import {UserDataService} from "../../services/user-data.service";
import {Router} from "@angular/router";
import {LocalHostService} from "../../services/local-host.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  displayProfileName: string = "";
  headerDisplay: string = "Home";
  activeButton: string = "HOME";

  constructor(public userDataService: UserDataService, private router: Router, public localHostService: LocalHostService) { }


  logoutEventHandler() {
    this.userDataService.logout();
  }


  ngOnInit(): void {
    if (this.userDataService.displayProfileName != null) {
      this.displayProfileName = this.userDataService.displayProfileName
    }
  }

  adminEventHandler(): void {
    this.activeButton = "ADMIN"
    this.headerDisplay = "Admin Console"
    this.router.navigate(['/admin'])
  }

  homeEventHandler(): void {
    this.activeButton = "HOME"
    this.headerDisplay = "Home"
    this.router.navigate(['/'])
  }
}
