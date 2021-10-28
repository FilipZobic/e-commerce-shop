import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {

  @Input()
  displayProfileName: string = "";

  @Input()
  activeButton: string = "HOME";

  @Input()
  numberOfItemsInCart: number = 0

  @Output()
  signOutButtonClickedEmitter = new EventEmitter();

  @Output()
  adminButtonClickedEmitter = new EventEmitter();

  @Output()
  homeButtonClickedEmitter = new EventEmitter();

  @Output()
  openDialogClickedEmitter = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

}
