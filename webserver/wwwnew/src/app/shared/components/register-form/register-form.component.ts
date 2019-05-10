import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AppInfoService } from '../../services/app-info.service';

@Component({
  selector: 'app-register',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

  constructor(private authService: AuthService, public appInfo: AppInfoService) { }

  ngOnInit() {
  }

}
