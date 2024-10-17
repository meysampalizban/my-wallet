import { Component, OnInit } from '@angular/core';
import { ServerService } from '../../service/server.service';
import { Account } from '../../dto/account';
import { User } from '../../dto/user';
import { CommonModule } from '@angular/common';
import { SeparateDashPipe } from '../../pipes/separate-dash.pipe';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { ComponentService } from '../../service/component.service';
@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, SeparateDashPipe],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit {
  accounts!: Account;
  userId !: number;
  userData: User | undefined;
  myAccounts: Account[] | undefined = [];
  constructor(private service: ServerService, private componentService: ComponentService) {
    this.userId = this.componentService.getUserIdLocalStorage();
  }

  ngOnInit(): void {
    this.myAccounts = this.service.getMyAccounts(this.userId);
    this.userData = this.service.getUserData(this.userId);
  }



  createAccount() {
    let accNumber = Math.floor(Math.random() * (9999999999999 - 1000000000000)).toString();
    let shabaNumber = Math.floor(Math.random() * (9999999999999999 - 1000000000000000)).toString();
    let balance = 50000;

    this.accounts = {
      "accNumber": accNumber,
      "shabaNumber": shabaNumber,
      "accBalance": balance,
      "user": this.userData
    };

    this.service.createAccount(this.accounts).subscribe(res => {
      this.service.getMyAccounts(this.userId);
      Swal.fire({
        title: "موفق",
        icon: 'success',
        confirmButtonText: 'تمام'
      });
    });
  }

  deleteMyAccount(accId: number | undefined) {
    let id: number = accId == undefined ? 0 : accId;
    this.service.deleteMyAccount(id).subscribe(res => {
      this.service.getMyAccounts(this.userId);
      Swal.fire({
        title: "موفق",
        icon: 'success',
        confirmButtonText: 'تمام'
      });

    })
  }
}
