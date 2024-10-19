import { Component } from '@angular/core';
import { ServerService } from '../../service/server.service';
import { User } from '../../dto/requests/user';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faBars, faUser } from '@fortawesome/free-solid-svg-icons';
import { ComponentService } from '../../service/component.service';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  faBars = faBars;
  faUser = faUser;
  public userData: User | undefined;
  private userId: number;

  constructor(private service: ServerService, private componentService: ComponentService) {
    this.userId = componentService.getUserIdLocalStorage();
  }

  ngOnInit(): void {
    this.service.getUserData(this.userId).subscribe((res) => {
      this.userData = res;
    });
  }

}
