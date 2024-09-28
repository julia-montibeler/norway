import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login } from '../models/login';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = "http://localhost:8080/auth";

  constructor(private httpClient: HttpClient) { }

  login(login: Login): Observable<any> {
    return this.httpClient.post<any>(this.url + '/login', login);
  }  
  
 
  register(user: User): Observable<any> {
    return this.httpClient.post<any>(this.url + '/register', user);
  }

  logout(user: User): Observable<any> {
    return this.httpClient.post<any>(this.url + '/logout', user);
  }

}
