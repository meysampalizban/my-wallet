import { Component, OnInit } from '@angular/core';
import { ServerService } from '../../server.service';
import { Account } from '../../dto/account';
import { User } from '../../dto/user';
import { CommonModule } from '@angular/common';
import { SeparateDashPipe } from '../../pipes/separate-dash.pipe';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
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
  userData !: User ;
  myAccounts: Account[] = [];
  constructor(private service: ServerService) {
    const user = localStorage.getItem("userId");
    let userId: string = user == null ? "" : user.toString();
    this.userId = parseInt(userId);
  }

  ngOnInit(): void {
    this.getMyAccounts();

    this.service.getUserData(this.userId).subscribe(res => {
      this.userData = res
    })
  }
  getMyAccounts() {
    this.service.getMyAccount(this.userId).subscribe(res => {
      this.myAccounts = res;
    })
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
      this.getMyAccounts();
      Swal.fire({
        title: "موفق",
        text: `${res.messages?.['success']}`,
        icon: 'success',
        confirmButtonText: 'تمام'
      });
    });
  }

  deleteMyAccount(accId: number | undefined) {
    let id: number = accId == undefined ? 0 : accId;
    this.service.deleteMyAccount(id).subscribe(res => {
      this.getMyAccounts();
      Swal.fire({
        title: "موفق",
        text: `${res.messages?.['success']}`,
        icon: 'success',
        confirmButtonText: 'تمام'
      });

    })
  }
}
