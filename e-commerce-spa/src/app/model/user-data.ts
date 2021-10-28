import {AddressDto} from "./dto/address-dto";

export interface UserData {
  "id": string,
  "fullName": string,
  "email": string,
  "isDeleted": boolean,
  "isEnabled": boolean,
  "grantedAuthorities": string[],
  "accessToken": string,
  "address": AddressDto
  "cart": CartItem[] | null;
}

export interface CartItemId {
  userId: string;
  laptopId: string;
}

export interface CartItem {
  id: CartItemId;
  amount: number;
}
