import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResourceAPIService {

  baseUri = 'http://localhost:8080/api/';

  constructor() { }
}
