export interface PuntoAcopio {
  idAcopio?: number;
  nombre: string;
  ubicacion: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data?: T;
}

export interface PuntoAcopioResponse {
  success: boolean;
  message: string;
  puntoAcopio?: PuntoAcopio;
}
