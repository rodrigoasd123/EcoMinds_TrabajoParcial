// Modelo que coincide con la entidad Evento del backend
export interface Evento {
  idEvento?: number;
  nombre: string;
  descripcion: string;
  fecha: string; // LocalDate del backend se convierte a string ISO
  hora: string;  // LocalTime del backend se convierte a string
  lugar: string;
  organizador?: {
    idOrganizador: number;
    nombreOrganizador: string;
  };
}

// DTO para crear eventos (usado por frontend)
export interface CrearEventoRequest {
  nombre: string;
  descripcion: string;
  fecha: string;
  hora: string;
  lugar: string;
}

// DTO para editar eventos (usado por frontend)
export interface EditarEventoRequest {
  nombre: string;
  descripcion: string;
  fecha: string;
  hora: string;
  lugar: string;
}

// Respuesta envuelta del backend para lista de eventos
export interface EventosResponse {
  success: boolean;
  message: string;
  eventos: Evento[];
  total: number;
}
