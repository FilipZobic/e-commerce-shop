export interface LaptopPagingRequest {
  manufacturerId: string|null
  productName: string|null
  minPrice: number|null
  maxPrice: number|null
  sortByProperty: string|null
  shouldReturnImage: boolean|null
  size: number|null
  page: number|null
}
