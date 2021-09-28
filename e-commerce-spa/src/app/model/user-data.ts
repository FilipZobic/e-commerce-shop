export interface UserData {
  "id": string,
  "fullName": string,
  "email": string,
  "isDeleted": boolean,
  "isEnabled": boolean,
  "grantedAuthorities": string[],
  "accessToken": string
}
