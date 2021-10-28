import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {LaptopSearchFormDto} from "../../../../model/search/laptop-search-form-dto";
import {FormControl, FormGroup} from "@angular/forms";
import {SelectDto} from "../../../../model/select-dto";
import {UserSearchFormDto} from "../../../../model/search/user-search-form-dto";

@Component({
  selector: 'app-user-search-form',
  templateUrl: './user-search-form.component.html',
  styleUrls: ['./user-search-form.component.scss']
})
export class UserSearchFormComponent implements OnInit {

  constructor() { }

  @Output()
  onEventActivateEmitter = new EventEmitter<UserSearchFormDto>()

  form: FormGroup = new FormGroup({});

  roles: SelectDto[] = [{value: "ROLE_ADMIN", display: "Admin"}, {value: "ROLE_USER", display: "User"}]

  ngOnInit(): void {
    this.form.addControl('email', new FormControl(null));
    this.form.addControl('role', new FormControl(null));
  }

  clear(): void {
    this.form.reset()
    this.form.markAsPristine()
    this.form.markAsUntouched()
  }

  submit(): void {
    this.onEventActivateEmitter.emit(this.form.value);
  }

}
