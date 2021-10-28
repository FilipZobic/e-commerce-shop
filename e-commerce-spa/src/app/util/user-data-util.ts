import {UserData} from "../model/user-data";

export class UserDataUtil {

  public static generateName (user: UserData) {
    const atIndex = user.email.indexOf("@");
    const dotIndex = user.email.indexOf(".");
    const dotLastIndex = user.email.lastIndexOf(".");

    if (atIndex === dotLastIndex) {
      return user.email.substr(0, dotIndex)
    } else {
      return user.email.substr(0, atIndex)
    }
  }
}
