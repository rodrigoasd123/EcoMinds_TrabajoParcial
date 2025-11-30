import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Recompensa, RecompensaRequest } from '../models/recompensa.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RecompensaService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  // Obtener todas las recompensas (USER, ADMIN)
  getAllRecompensas(): Observable<Recompensa[]> {
    return this.http.get<Recompensa[]>(`${this.apiUrl}/recompensas`);
  }

  // Obtener una recompensa por ID (USER, ADMIN)
  getRecompensaById(id: number): Observable<Recompensa> {
    return this.http.get<Recompensa>(`${this.apiUrl}/recompensa/${id}`);
  }

  // Crear recompensa (solo ADMIN)
  createRecompensa(recompensa: RecompensaRequest): Observable<Recompensa> {
    return this.http.post<Recompensa>(
      `${this.apiUrl}/recompensa`,
      recompensa,
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Actualizar recompensa (solo ADMIN)
  updateRecompensa(id: number, recompensa: RecompensaRequest): Observable<Recompensa> {
    return this.http.put<Recompensa>(
      `${this.apiUrl}/recompensa/${id}`,
      recompensa,
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Eliminar recompensa (solo ADMIN)
  deleteRecompensa(id: number): Observable<string> {
    return this.http.delete<string>(
      `${this.apiUrl}/recompensa/${id}`,
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Obtener recompensas disponibles según puntos del usuario (USER, ADMIN)
  getRecompensasDisponibles(puntos: number): Observable<Recompensa[]> {
    return this.http.get<Recompensa[]>(`${this.apiUrl}/recompensas/disponibles/${puntos}`);
  }

  // Ordenar por puntos ascendente (menor a mayor)
  getRecompensasOrdenPuntosAsc(): Observable<Recompensa[]> {
    return this.http.get<Recompensa[]>(`${this.apiUrl}/recompensas/ordenar/puntos-asc`);
  }

  // Ordenar por puntos descendente (mayor a menor)
  getRecompensasOrdenPuntosDesc(): Observable<Recompensa[]> {
    return this.http.get<Recompensa[]>(`${this.apiUrl}/recompensas/ordenar/puntos-desc`);
  }

  // Ordenar alfabéticamente por nombre
  getRecompensasOrdenNombre(): Observable<Recompensa[]> {
    return this.http.get<Recompensa[]>(`${this.apiUrl}/recompensas/ordenar/nombre`);
  }

  // Canjear recompensa (USER, ADMIN)
  canjearRecompensa(recompensaId: number): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}/canjear/${recompensaId}`,
      {},
      { headers: this.authService.getHttpHeaders() }
    );
  }

  // Obtener puntos del usuario autenticado
  obtenerMisPuntos(): Observable<any> {
    return this.http.get<any>(
      `${this.apiUrl}/mis-puntos`,
      { headers: this.authService.getHttpHeaders() }
    );
  }
}
