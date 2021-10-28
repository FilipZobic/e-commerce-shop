import {Component, OnInit, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {UserData} from "../../../model/user-data";
import {UserService} from "../../../services/user.service";
import {CountryService} from "../../../services/country.service";
import {Country} from "../../../model/country";
import {RegistrationDto} from "../../../model/dto/registration-dto";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {PaginationFormData} from "../../../model/search/pagination-form-data";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {LaptopSearchFormDto} from "../../../model/search/laptop-search-form-dto";
import {LaptopPagingRequest} from "../../../model/laptop-paging-request";
import {UserPagingRequest} from "../../../model/user-paging-request";
import {UserSearchFormDto} from "../../../model/search/user-search-form-dto";
import {UserDataService} from "../../../services/user-data.service";

@Component({
  selector: 'app-users-tab',
  templateUrl: './users-tab.component.html',
  styleUrls: ['./users-tab.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class UsersTabComponent implements OnInit {
  users: UserData[] = [];
  countriesDtos: Country[] = [];
  columnsToDisplay = ['id'];
  expandedElement: UserData | null | undefined;
  selectedTabIndex: number = 0;
  numberOfUsers: number = 0;
  numberOfItemsInCart: number = 0;
  pagingRequest: UserPagingRequest = {
    sortByProperty: "email",
    page: 0,
    size: 10,
    email: null,
    role: null
  }
  // @ts-ignore
  @ViewChild('paginator') paginator: MatPaginator;

  constructor(private userService: UserService, private countryService: CountryService) {
    this.getAllUsers()
    this.countryService.itemsSubject.subscribe(newCountries => {
      // this.countriesDtos = newCountries.map<SelectDto>(a => {return {value: a.alpha2Code, display: a.name}});
      this.countriesDtos = newCountries;
    })
  }

  ngOnInit(): void {
  }

  createUser($event: RegistrationDto) {
    this.userService.postUser($event).subscribe(next => {}, er => {}, () => {
      this.getAllUsers()
    });
  }

  onUpdate(user: RegistrationDto) {
    this.userService.putUser(user).subscribe(next => {}, er => {}, () => {
      this.getAllUsers()
    });
  }

  getAllUsers() {
    this.userService.fetchAllUsers(this.pagingRequest).subscribe(next => {
      this.users = next.content;
      this.numberOfUsers = next.totalElements
    })
  }

  updateFormSearchValue($event: UserSearchFormDto) {
    this.pagingRequest = {...this.pagingRequest, ...$event, page: 0}
    this.paginator.firstPage()
    console.log(this.pagingRequest)
    this.getAllUsers()
  }

  updatePaginationSearchValue($event: PaginationFormData) {
    this.pagingRequest = {...this.pagingRequest, ...$event}
    console.log(this.pagingRequest)
    this.getAllUsers()
  }

  tabChange($event: MatTabChangeEvent) {
    this.selectedTabIndex = $event.index
  }

  getPaginatorData($event: PageEvent) {
    this.updatePaginationSearchValue({page: $event.pageIndex, size: $event.pageSize});
  }
}
