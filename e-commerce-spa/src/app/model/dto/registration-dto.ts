import {AddressDto} from "./address-dto";

export interface RegistrationDto {
  password: string,
  email: string,
  fullName: string,
  address: AddressDto
}
