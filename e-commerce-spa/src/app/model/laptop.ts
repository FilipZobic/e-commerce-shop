import {Manufacturer} from "./manufacturer";

export interface Laptop {
  "id": string,
  "name": string,
  "image": string,
  "manufacturer": Manufacturer,
  "price": number,
  "diagonal": number,
  "panelType": string,
  "ram": number,
  "stock": number
}
