import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Place } from '../models/place';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {
  private url = 'http://localhost:8080/place';

  constructor(private http:HttpClient) { }

  getPlaces():Observable<Place[]>{
    return this.http.get<Place[]>(this.url);
  }

  getPlaceById(id:string|null): Observable<Place>{
    return this.http.get<Place>(`${this.url}/id/${id}`);
  }

  getPlaceByCity(city:string|null): Observable<Place[]>{
    return this.http.get<Place[]>(`${this.url}/searchCity/${city}`);
  }

  createPlace(place: Place): Observable<Place> {
    return this.http.post<Place>(`${this.url}/register`, place);
  }

  deletePlace(id:string):Observable<any>{
    return this.http.delete<any>(`${this.url}/id/${id}`);
  }
}

