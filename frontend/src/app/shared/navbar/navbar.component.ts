import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RecompensaService } from '../../services/recompensa.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  username = '';
  puntos = 0;
  showUserMenu = false;

  constructor(
    private authService: AuthService,
    private recompensaService: RecompensaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    if (this.isLoggedIn) {
      const currentUser = this.authService.currentUserValue;
      this.username = currentUser?.username || '';
      this.cargarPuntos();
    }
  }

  cargarPuntos(): void {
    // Solo cargar puntos si hay un token válido
    const token = this.authService.getToken();
    if (!token) {
      console.warn('No hay token de autenticación para cargar puntos');
      return;
    }

    this.recompensaService.obtenerMisPuntos().subscribe({
      next: (data) => {
        this.puntos = data.puntos || 0;
      },
      error: (error) => {
        console.error('Error al cargar puntos:', error);
        // Si hay error 403, cerrar sesión porque el token puede ser inválido
        if (error.status === 403) {
          console.warn('Token inválido o expirado, cerrando sesión');
          this.authService.logout();
          this.isLoggedIn = false;
        }
      }
    });
  }

  toggleUserMenu(): void {
    this.showUserMenu = !this.showUserMenu;
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
