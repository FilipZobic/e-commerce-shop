import {Laptop} from "./laptop";
import {CartItem} from "./user-data";

export interface CartResponseDto extends Laptop{

  cartInfo: CartItem;
}
