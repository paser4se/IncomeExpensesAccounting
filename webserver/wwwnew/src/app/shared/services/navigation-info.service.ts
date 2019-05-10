import { Injectable, EventEmitter } from '@angular/core';

@Injectable()
export class NavigationInfoService {
  menuChange = new EventEmitter<string>();
  currentMenuItem: string;

  constructor() {
    this.currentMenuItem = '';
  }
}
