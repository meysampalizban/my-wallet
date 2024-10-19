import { Injectable } from '@angular/core';
import { ServerService } from './server.service';
import { User } from '../dto/requests/user';

@Injectable({
  providedIn: 'root'
})
export class ComponentService {

  constructor() { }

  public getUserIdLocalStorage(): number {
    const user = localStorage.getItem("userId");
    let userId: string = user == null ? "" : user.toString();
    return parseInt(userId);
  }

  public getUserToken(): string {
    const user = localStorage.getItem("_token");
    let userId: string = user == null ? "" : user.toString();
    return userId;
  }


}
