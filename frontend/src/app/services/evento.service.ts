import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Evento, CrearEventoRequest, EditarEventoRequest, EventosResponse } from '../models/evento.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class EventoService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  // Obtener todos los eventos (público)
  getAllEventos(): Observable<Evento[]> {
    return this.http.get<EventosResponse>(`${this.apiUrl}/eventos`).pipe(
      map(response => {
        const eventos = response.eventos || [];
        // Mapear el campo 'id' del backend a 'idEvento' del frontend
        return eventos.map(evento => ({
          ...evento,
          idEvento: (evento as any).id || evento.idEvento
        }));
      })
    );
  }

  // Obtener un evento por ID (requiere autenticación)
  getEventoById(id: number): Observable<Evento> {
    return this.http.get<any>(`${this.apiUrl}/evento/${id}`,
      { headers: this.authService.getHttpHeaders() }
    ).pipe(
      map(response => {
        // El backend devuelve { success: true, evento: {...} }
        return response.evento || response;
      })
    );
  }

  // Crear evento (solo ORGANIZADOR)
  createEvento(evento: CrearEventoRequest): Observable<Evento> {
    return this.http.post<Evento>(
      `${this.apiUrl}/evento`,
      evento,
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Actualizar evento (solo ORGANIZADOR propietario o ADMIN)
  updateEvento(id: number, evento: EditarEventoRequest): Observable<Evento> {
    return this.http.put<Evento>(
      `${this.apiUrl}/evento/${id}`,
      evento,
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Eliminar evento (solo ORGANIZADOR propietario)
  deleteEvento(id: number): Observable<string> {
    return this.http.delete<string>(
      `${this.apiUrl}/evento/${id}`,
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Inscribirse a un evento (USER, ADMIN)
  inscribirseEvento(eventoId: number): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}/evento/${eventoId}/inscribirse`,
      {},
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Obtener eventos en los que el usuario está inscrito (USER, ADMIN)
  getMisEventos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(
      `${this.apiUrl}/mis-eventos`,
      { headers: this.authService.getHttpHeaders() }
    );
  }
}
