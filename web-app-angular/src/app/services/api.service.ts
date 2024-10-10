import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private axiosInstance;
  private api;

  constructor() {
    // Create an axios instance with the base URL
    this.axiosInstance = axios.create({
      baseURL: 'http://localhost:8080'  // Replace with your API base URL
    });

    // You can expose both the axios instance and the API instance if needed
    this.api = this.axiosInstance;
  }

  // Getter for Axios instance
  public getAxiosInstance() {
    return this.axiosInstance;
  }

  // Getter for API instance
  public getApi() {
    return this.api;
  }

  // Example method to perform a GET request
  public getData(endpoint: string) {
    return this.api.get(endpoint);
  }

  // Example method to perform a POST request
  public postData(endpoint: string, data: any) {
    return this.api.post(endpoint, data);
  }
}
