import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../models/user.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  searchUsers(search: string): Observable<any> {
    const params = new FormData();
    params.append('search', search);

    return this.http.post<any>(`${this.apiUrl}/user/search-user`, params);
  }

  getUserById(userId: string): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.apiUrl}/${userId}`);
  }

  createUser(user: UserDto): Observable<any> {
    return this.http.post<any>(this.apiUrl, user);
  }

  updateUser(userId: string, user: UserDto): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${userId}`, user);
  }

  deleteUser(userId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${userId}`);
  }
}
