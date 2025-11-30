import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CrearRecoleccionRequest, RegistrarRecoleccionResponse, MaterialReciclaje } from '../models/recoleccion.model';

@Injectable({
  providedIn: 'root'
})
export class RecoleccionService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  registrarRecoleccion(data: CrearRecoleccionRequest): Observable<RegistrarRecoleccionResponse> {
    return this.http.post<RegistrarRecoleccionResponse>(`${this.apiUrl}/recolecciones/registrar`, data);
  }

  obtenerMateriales(): Observable<MaterialReciclaje[]> {
    return this.http.get<MaterialReciclaje[]>(`${this.apiUrl}/materiales`);
  }
}
