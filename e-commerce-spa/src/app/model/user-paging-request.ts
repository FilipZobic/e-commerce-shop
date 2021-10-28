import {PaginationFormData} from "./search/pagination-form-data";
import {UserSearchFormDto} from "./search/user-search-form-dto";

export interface UserPagingRequest extends PaginationFormData, UserSearchFormDto{
  sortByProperty: string|null
}
