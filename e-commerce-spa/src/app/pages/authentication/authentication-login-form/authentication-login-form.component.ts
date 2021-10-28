import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {AuthenticationService} from '../../../services/authentication.service';
import {UserLoginDto} from "../../../model/dto/user-login-dto";
@Component({
  selector: 'app-authentication-login-form',
  templateUrl: './authentication-login-form.component.html',
  styleUrls: ['./authentication-login-form.component.scss']
})
export class AuthenticationLoginFormComponent implements OnInit {

  form: FormGroup = new FormGroup({});

  @Output()
  submitLoginEmitter: EventEmitter<UserLoginDto> = new EventEmitter<UserLoginDto>();

  constructor() { }

  ngOnInit(): void {
    this.form.addControl('password', new FormControl(null));
    this.form.addControl('email', new FormControl(null));

    // this.form.controls.password.addValidators([Validators.minLength(1), Validators.maxLength(30)]);
  }
  onSubmit(): void {
    if (this.form.valid) {
      this.submitLoginEmitter.emit(this.form.value);
    }
  }
}
