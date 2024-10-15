import { Component } from '@angular/core';
import { ServerService } from '../../server.service';
import { User } from '../../dto/user';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faBars, faUser } from '@fortawesome/free-solid-svg-icons';
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
  public user: User | undefined;
  private userId: number;

  constructor(private service: ServerService) {
    const user = localStorage.getItem("userId");
    let userId: string = user == null ? "" : user.toString();
    this.userId = parseInt(userId);
  }

  ngOnInit(): void {
    this.service.getUserData(this.userId).subscribe(res => {
      this.user = res
    })
  }

}
