import {AddressDto} from "./address-dto";

export interface RegistrationDto {
  id: string | undefined | null;
  password: string,
  email: string,
  fullName: string,
  address: AddressDto,
  role: string|null,
  isEnabled: boolean|null|undefined,
  isDeleted: boolean|null|undefined
}
