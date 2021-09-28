import { Injectable } from '@angular/core';
import {UserDataService} from "./user-data.service";

@Injectable({
  providedIn: 'root'
})
export class RolesValidationService {

  constructor(private userDataService: UserDataService) { }

  validateRoles(roles: string[]): boolean {
    const user = this.userDataService.getUser();
    if (user) {
      const userRoles = new Set(user.grantedAuthorities);
      for (const r of roles) {
        if (userRoles.has(r)) {
          return true;
        }
      }
    }
    return false;
  }
}
