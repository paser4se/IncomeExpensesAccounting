import { Component, OnInit, NgModule } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AppInfoService } from '../../services/app-info.service';
import { Account } from '../../models/account.model';

@Component({
  selector: 'app-register',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {
  account: Account = {
    id: null,
    username: "",
    password: "",
    email: "",
    fullName: ""
  };
  confPassword: string;
  passwordComparison = () => {
    return this.account.password;
  };

  constructor(private authService: AuthService, public appInfo: AppInfoService) {
    this.confPassword = "";
  }

  onRegisterClick(args) {
    if (!args.validationGroup.validate().isValid) {
      return;
    }
  }

  ngOnInit() {
  }

}
