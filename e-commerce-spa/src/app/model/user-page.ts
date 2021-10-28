import {UserData} from "./user-data";

export interface UserPage {
  content: UserData[],
  pageable: {
    sort: {
      "sorted": boolean,
      "unsorted": boolean,
      "empty": boolean
    },
    pageNumber: number,
    pageSize: number
  },
  "totalPages": number,
  "totalElements": number,
  "last": true,
  "first": true,
}
