import { Injectable } from '@angular/core';

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
}
