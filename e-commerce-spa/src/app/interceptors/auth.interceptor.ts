import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserDataService} from "../services/user-data.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private userData: UserDataService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const accessToken = this.userData.accessToken;
    if (accessToken != null) {
      const newRequest = request.clone({
        // @ts-ignore
        headers: request.headers.set("X-ACCESS-TOKEN", accessToken),
        withCredentials: true
      });
      return next.handle(newRequest);
    }
    return next.handle(request);
  }
}
