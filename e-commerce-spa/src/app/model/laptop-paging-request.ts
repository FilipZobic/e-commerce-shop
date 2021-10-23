import {LaptopPaginationSearchFormDto} from "./search/laptop-pagination-search-form-dto";
import {LaptopSearchFormDto} from "./search/laptop-search-form-dto";

export interface LaptopPagingRequest extends LaptopPaginationSearchFormDto, LaptopSearchFormDto{
  sortByProperty: string|null
  shouldReturnImage: boolean|null
}
