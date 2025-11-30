import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PuntoAcopio, PuntoAcopioResponse } from '../models/punto-acopio.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PuntoAcopioService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  private getHeaders(): HttpHeaders {
    return this.authService.getHttpHeaders();
  }

  listarPuntosAcopio(): Observable<PuntoAcopio[]> {
    return this.http.get<PuntoAcopio[]>(`${this.apiUrl}/puntos-acopio`, {
      headers: this.getHeaders()
    });
  }

  obtenerPuntoAcopio(id: number): Observable<PuntoAcopio> {
    return this.http.get<PuntoAcopio>(`${this.apiUrl}/punto-acopio/${id}`, {
      headers: this.getHeaders()
    });
  }

  crearPuntoAcopio(punto: PuntoAcopio): Observable<PuntoAcopioResponse> {
    return this.http.post<PuntoAcopioResponse>(`${this.apiUrl}/punto-acopio`, punto, {
      headers: this.getHeaders()
    });
  }

  actualizarPuntoAcopio(id: number, punto: PuntoAcopio): Observable<PuntoAcopioResponse> {
    return this.http.put<PuntoAcopioResponse>(`${this.apiUrl}/punto-acopio/${id}`, punto, {
      headers: this.getHeaders()
    });
  }

  eliminarPuntoAcopio(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/punto-acopio/${id}`, {
      headers: this.getHeaders()
    });
  }
}
