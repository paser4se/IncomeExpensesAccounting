import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './shared/components';
import { AuthGuardService } from './shared/services';
import { HomeComponent } from './pages/home/home.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { DisplayDataComponent } from './pages/display-data/display-data.component';
import { DxDataGridModule, DxFormModule, DxButtonModule, DxTextBoxModule, DxValidatorModule, DxValidationGroupModule } from 'devextreme-angular';
import { PaymentsComponent } from './pages/payments/payments.component';
import { EvaluationComponent } from './pages/evaluation/evaluation.component';
import { RegisterFormComponent } from './shared/components/register-form/register-form.component';
import { MatStepperModule, MatButtonModule, MatTableModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { NgxDropzoneModule } from 'ngx-dropzone';

const routes: Routes = [
  {
    path: 'register',
    component: RegisterFormComponent
  },
  {
    path: 'evaluation',
    component: EvaluationComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'payments',
    component: PaymentsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'display-data',
    component: DisplayDataComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'login',
    component: LoginFormComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: '**',
    redirectTo: 'home',
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    DxDataGridModule,
    DxFormModule,
    MatStepperModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule,
    CommonModule,
    RouterModule,
    DxButtonModule,
    DxTextBoxModule,
    DxValidatorModule,
    DxValidationGroupModule,
    NgxDropzoneModule,
    MatTableModule
  ],
  providers: [AuthGuardService],
  exports: [RouterModule],
  declarations: [HomeComponent, ProfileComponent, DisplayDataComponent, PaymentsComponent, EvaluationComponent, RegisterFormComponent]
})
export class AppRoutingModule { }
