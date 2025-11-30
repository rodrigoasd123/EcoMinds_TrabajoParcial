export interface MaterialReciclaje {
  idMateriales: number;
  nombre: string;
  descripcion?: string;
}

export interface CrearRecoleccionRequest {
  idMaterial: number;
  idPunto: number;
  peso: number;
}

export interface RecoleccionDTO {
  idRecoleccion: number;
  fecha: string;
  peso: number;
  puntos: number;
  idMaterial: number;
  nombreMaterial: string;
  idPunto: number;
  nombrePunto: string;
  idUsuario: number;
  nombreUsuario: string;
}

export interface RegistrarRecoleccionResponse {
  success: boolean;
  message: string;
  puntosGanados?: number;
  puntosTotal?: number;
}
