import {Laptop} from "./laptop";
import {CartItem} from "./user-data";

export interface ExpandedLaptopCart extends Laptop{

  cartInfo: CartItem;
}
