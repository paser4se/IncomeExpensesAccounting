import { Component } from '@angular/core';

@Component({
  templateUrl: 'profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent {
  employee: any;
  colCountByScreen: Object;

  constructor() {
    this.employee = {
      ID: 7,
      FirstName: 'Max',
      LastName: 'Mustermann',
      Prefix: 'Mr.',
      Position: 'Controller',
      BirthDate: new Date('1974/11/15'),
      HireDate: new Date('2005/05/11'),
      /* tslint:disable-next-line:max-line-length */
      Notes: '.....',
      Address: '4600 N Virginia Rd.'
    };
  }
}
