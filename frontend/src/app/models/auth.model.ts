export interface User {
  id?: number;
  username: string;
  nombre: string;
  apellido: string;
  correo: string;
  pesoReciclado?: number;
  puntos?: number;
  roles?: Role[];
}

export interface Role {
  id?: number;
  name: string;
}

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  jwt: string;
  username: string;
  roles: string[];
}

export interface RegistroRequest {
  username: string;
  password: string;
  nombre: string;
  apellido: string;
  correo: string;
}
