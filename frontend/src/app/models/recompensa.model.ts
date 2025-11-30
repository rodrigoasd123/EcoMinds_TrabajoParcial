// Modelo que coincide con la entidad Recompensa del backend
export interface Recompensa {
  idRecompensa?: number;
  nombre: string;
  descripcion: string;
  puntosRequeridos: number;
}

// Para requests de crear/actualizar
export interface RecompensaRequest {
  nombre: string;
  descripcion: string;
  puntosRequeridos: number;
}

// Respuesta del backend no usa ApiResponse genérico
// Los endpoints devuelven directamente la entidad o lista
