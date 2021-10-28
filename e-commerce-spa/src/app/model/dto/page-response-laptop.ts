import {Laptop} from "../laptop";

export interface PageResponseLaptop {
  "page": {
    content: Laptop[],
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
  "priceCeiling": number
}
