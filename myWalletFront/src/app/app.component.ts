import { Component, inject, Input } from "@angular/core";
import { Router, RouterLink, RouterOutlet } from "@angular/router";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from '@angular/material/button';
import { Title } from "@angular/platform-browser";
import { RegisterUserComponent } from "./register-user/register-user.component";
@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterLink, RouterOutlet, CommonModule, MatButtonModule, RegisterUserComponent],
  templateUrl: "./app.component.html",
  styleUrl: "./app.component.css",
})
export class AppComponent  {
  constructor(private title: Title, protected router: Router) {
    title.setTitle("کبف پول من - صفحه اصلی");
  }

}
