import { Component, OnInit } from '@angular/core';
import { ServerService } from '../service/server.service';
import { User } from '../dto/requests/user';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faMinus, faPlus, faReceipt, faWallet } from '@fortawesome/free-solid-svg-icons';
import { DecimalPipe } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./header/header.component";
import { AccountsComponent } from "./accounts/accounts.component";
import { ComponentService } from '../service/component.service';
@Component({
  selector: 'app-wallet-page',
  standalone: true,
  imports: [FontAwesomeModule, DecimalPipe, RouterLink,RouterOutlet, HeaderComponent, AccountsComponent],
  templateUrl: './wallet-page.component.html',
  styleUrl: './wallet-page.component.css'
})
export class WalletPageComponent implements OnInit {

  public userData: User | undefined;
  private userId: number;
  faMinus = faMinus;
  faPlus = faPlus;
  faWallet = faWallet;
  faReceipt = faReceipt;

  constructor(private service: ServerService, private componentService: ComponentService) {
    this.userId = this.componentService.getUserIdLocalStorage();
  }

  ngOnInit(): void {
    this.service.getUserData(this.userId).subscribe((res) => {
      this.userData = res;
    });
  }


}
