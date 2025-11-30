import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RecompensaService } from '../../services/recompensa.service';
import { AuthService } from '../../services/auth.service';
import { PuntoAcopioService } from '../../services/punto-acopio.service';
import { Recompensa } from '../../models/recompensa.model';
import { PuntoAcopio } from '../../models/punto-acopio.model';

@Component({
  selector: 'app-recompensas',
  templateUrl: './recompensas.component.html',
  styleUrls: ['./recompensas.component.css']
})
export class RecompensasComponent implements OnInit {
  recompensas: Recompensa[] = [];
  recompensasFiltradas: Recompensa[] = [];
  ordenamiento: 'asc' | 'desc' | 'none' = 'none';
  searchTerm: string = '';
  isAdmin: boolean = false;
  puntosUsuario: number = 0;
  canjeoEnProceso: { [key: number]: boolean } = {};

  // Modal de canje
  showCanjeModal: boolean = false;
  recompensaSeleccionada: Recompensa | null = null;
  puntosAcopio: PuntoAcopio[] = [];
  puntoSeleccionado: number | null = null;
  loadingPuntos: boolean = false;

  constructor(
    private recompensaService: RecompensaService,
    private authService: AuthService,
    private puntoAcopioService: PuntoAcopioService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.cargarRecompensas();
    if (!this.isAdmin) {
      this.cargarPuntosUsuario();
    }
  }

  cargarPuntosUsuario(): void {
    // Solo cargar puntos si hay un token válido
    const token = this.authService.getToken();
    if (!token) {
      console.warn('No hay token de autenticación para cargar puntos');
      return;
    }

    this.recompensaService.obtenerMisPuntos().subscribe({
      next: (data) => {
        this.puntosUsuario = data.puntos || 0;
      },
      error: (error) => {
        console.error('Error al cargar puntos del usuario:', error);
        // Si hay error 403, puede ser token inválido
        if (error.status === 403) {
          this.snackBar.open('Sesión expirada. Por favor inicia sesión nuevamente', 'Cerrar', {
            duration: 4000
          });
        }
      }
    });
  }

  cargarRecompensas(): void {
    // Usar el endpoint de ordenamiento si está activo
    if (this.ordenamiento === 'asc') {
      this.recompensaService.getRecompensasOrdenPuntosAsc().subscribe({
        next: (data) => {
          this.recompensas = data;
          this.aplicarFiltros();
        },
        error: (error: any) => {
          console.error('Error al cargar recompensas:', error);
          this.snackBar.open('Error al cargar las recompensas', 'Cerrar', {
            duration: 3000
          });
        }
      });
    } else if (this.ordenamiento === 'desc') {
      this.recompensaService.getRecompensasOrdenPuntosDesc().subscribe({
        next: (data) => {
          this.recompensas = data;
          this.aplicarFiltros();
        },
        error: (error: any) => {
          console.error('Error al cargar recompensas:', error);
          this.snackBar.open('Error al cargar las recompensas', 'Cerrar', {
            duration: 3000
          });
        }
      });
    } else {
      this.recompensaService.getAllRecompensas().subscribe({
        next: (data) => {
          this.recompensas = data;
          this.aplicarFiltros();
        },
        error: (error: any) => {
          console.error('Error al cargar recompensas:', error);
          this.snackBar.open('Error al cargar las recompensas', 'Cerrar', {
            duration: 3000
          });
        }
      });
    }
  }

  // Ordenar por puntos usando endpoints del backend
  ordenarPorPuntos(orden: 'asc' | 'desc'): void {
    if (this.ordenamiento === orden) {
      this.ordenamiento = 'none';
    } else {
      this.ordenamiento = orden;
    }
    this.cargarRecompensas();
  }

  // Buscar por término
  buscar(event: any): void {
    this.searchTerm = event.target.value.toLowerCase();
    this.aplicarFiltros();
  }

  // Aplicar filtro de búsqueda
  aplicarFiltros(): void {
    let resultado = [...this.recompensas];

    // Filtro por búsqueda
    if (this.searchTerm) {
      resultado = resultado.filter(r =>
        r.nombre.toLowerCase().includes(this.searchTerm) ||
        r.descripcion.toLowerCase().includes(this.searchTerm)
      );
    }

    this.recompensasFiltradas = resultado;
  }

  // Eliminar recompensa (solo ADMIN)
  eliminarRecompensa(id: number, nombre: string): void {
    if (confirm(`¿Estás seguro de eliminar la recompensa "${nombre}"?`)) {
      this.recompensaService.deleteRecompensa(id).subscribe({
        next: () => {
          this.snackBar.open('Recompensa eliminada correctamente', 'Cerrar', {
            duration: 3000
          });
          this.cargarRecompensas();
        },
        error: (error: any) => {
          console.error('Error al eliminar:', error);
          this.snackBar.open('Error al eliminar la recompensa', 'Cerrar', {
            duration: 3000
          });
        }
      });
    }
  }

  // Navegar a crear/editar
  crearRecompensa(): void {
    this.router.navigate(['/recompensas/crear']);
  }

  editarRecompensa(recompensa: Recompensa): void {
    this.router.navigate(['/recompensas/editar', recompensa.idRecompensa]);
  }

  // Abrir modal de canje
  abrirModalCanje(recompensa: Recompensa): void {
    // Verificar si el usuario tiene suficientes puntos
    if (this.puntosUsuario < recompensa.puntosRequeridos) {
      this.snackBar.open(
        `Necesitas ${recompensa.puntosRequeridos - this.puntosUsuario} puntos más para esta recompensa`,
        'Cerrar',
        {
          duration: 4000,
          panelClass: ['error-snackbar']
        }
      );
      return;
    }

    this.recompensaSeleccionada = recompensa;
    this.showCanjeModal = true;
    this.puntoSeleccionado = null;
    this.cargarPuntosAcopio();
  }

  // Cargar puntos de acopio disponibles
  cargarPuntosAcopio(): void {
    this.loadingPuntos = true;
    this.puntoAcopioService.listarPuntosAcopio().subscribe({
      next: (puntos) => {
        this.puntosAcopio = puntos;
        this.loadingPuntos = false;
      },
      error: (error) => {
        console.error('Error al cargar puntos de acopio:', error);
        this.snackBar.open('Error al cargar los puntos de acopio', 'Cerrar', {
          duration: 3000
        });
        this.loadingPuntos = false;
      }
    });
  }

  // Cerrar modal de canje
  cerrarModalCanje(): void {
    this.showCanjeModal = false;
    this.recompensaSeleccionada = null;
    this.puntoSeleccionado = null;
    this.puntosAcopio = [];
  }

  // Seleccionar punto de acopio
  seleccionarPunto(idPunto: number): void {
    this.puntoSeleccionado = idPunto;
  }

  // Confirmar canje de recompensa
  confirmarCanje(): void {
    if (!this.recompensaSeleccionada || !this.puntoSeleccionado) {
      this.snackBar.open('Por favor selecciona un punto de recogida', 'Cerrar', {
        duration: 3000
      });
      return;
    }

    const id = this.recompensaSeleccionada.idRecompensa!;
    const nombreRecompensa = this.recompensaSeleccionada.nombre;
    const puntoNombre = this.puntosAcopio.find(p => p.idAcopio === this.puntoSeleccionado)?.nombre;
    this.canjeoEnProceso[id] = true;

    this.recompensaService.canjearRecompensa(id).subscribe({
      next: (response) => {
        this.canjeoEnProceso[id] = false;
        this.cerrarModalCanje();

        // Mostrar notificación de éxito
        this.snackBar.open(
          `¡Recompensa "${nombreRecompensa}" canjeada! Recógela en: ${puntoNombre}. Te quedan ${response.puntosRestantes} puntos`,
          'Cerrar',
          {
            duration: 5000,
            panelClass: ['success-snackbar']
          }
        );

        // Recargar la página después de un breve delay para que se vea la notificación
        setTimeout(() => {
          window.location.reload();
        }, 1500);
      },
      error: (error) => {
        console.error('Error al canjear recompensa:', error);
        const mensaje = error.error?.message || 'Error al canjear la recompensa';
        this.snackBar.open(mensaje, 'Cerrar', {
          duration: 4000,
          panelClass: ['error-snackbar']
        });
        this.canjeoEnProceso[id] = false;
      }
    });
  }

  // Verificar si el usuario puede canjear la recompensa
  puedeCanjear(puntosRequeridos: number): boolean {
    return this.puntosUsuario >= puntosRequeridos;
  }
}
