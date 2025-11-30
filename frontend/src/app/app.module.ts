import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JwtInterceptor } from './interceptors/jwt.interceptor';

// Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';

import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { PuntosAcopioComponent } from './pages/puntos-acopio/puntos-acopio.component';
import { CrearPuntoComponent } from './pages/puntos-acopio/crear-punto/crear-punto.component';
import { SuccessPuntoComponent } from './pages/puntos-acopio/success-punto/success-punto.component';
import { RecompensasComponent } from './pages/recompensas/recompensas.component';
import { DetalleRecompensaComponent } from './pages/recompensas/detalle-recompensa/detalle-recompensa.component';
import { EliminarRecompensaComponent } from './pages/recompensas/eliminar-recompensa/eliminar-recompensa.component';
import { EventosComponent } from './pages/eventos/eventos.component';
import { CrearEventoComponent } from './pages/eventos/crear-evento/crear-evento.component';
import { InscripcionEventoComponent } from './pages/eventos/inscripcion-evento/inscripcion-evento.component';
import { ProgramarRecoleccionComponent } from './pages/programar-recoleccion/programar-recoleccion.component';
import { ResumenSolicitudComponent } from './pages/programar-recoleccion/resumen-solicitud/resumen-solicitud.component';
import { ConfirmacionSolicitudComponent } from './pages/programar-recoleccion/confirmacion-solicitud/confirmacion-solicitud.component';
import { FormRecompensaComponent } from './pages/recompensas/form-recompensa/form-recompensa.component';
import { FormEventoComponent } from './pages/eventos/form-evento/form-evento.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'puntos-acopio', component: PuntosAcopioComponent },
  { path: 'puntos-acopio/crear', component: CrearPuntoComponent },
  { path: 'puntos-acopio/editar/:id', component: CrearPuntoComponent },
  { path: 'puntos-acopio/success', component: SuccessPuntoComponent },
  { path: 'recompensas', component: RecompensasComponent },
  { path: 'recompensas/crear', component: FormRecompensaComponent },
  { path: 'recompensas/editar/:id', component: FormRecompensaComponent },
  { path: 'eventos', component: EventosComponent },
  { path: 'eventos/crear', component: FormEventoComponent },
  { path: 'eventos/editar/:id', component: FormEventoComponent },
  { path: 'programar-recoleccion', component: ProgramarRecoleccionComponent },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    PuntosAcopioComponent,
    CrearPuntoComponent,
    SuccessPuntoComponent,
    RecompensasComponent,
    DetalleRecompensaComponent,
    EliminarRecompensaComponent,
    EventosComponent,
    CrearEventoComponent,
    InscripcionEventoComponent,
    ProgramarRecoleccionComponent,
    ResumenSolicitudComponent,
    ConfirmacionSolicitudComponent,
    FormRecompensaComponent,
    FormEventoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatMenuModule,
    MatSelectModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
