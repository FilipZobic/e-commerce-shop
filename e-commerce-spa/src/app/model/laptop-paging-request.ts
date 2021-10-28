import {PaginationFormData} from "./search/pagination-form-data";
import {LaptopSearchFormDto} from "./search/laptop-search-form-dto";

export interface LaptopPagingRequest extends PaginationFormData, LaptopSearchFormDto{
  sortByProperty: string|null
  shouldReturnImage: boolean|null
}
