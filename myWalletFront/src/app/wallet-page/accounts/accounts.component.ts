import { Component, OnInit } from '@angular/core';
import { ServerService } from '../../service/server.service';
import { Account } from '../../dto/requests/account';
import { User } from '../../dto/requests/user';
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
  userId !: number;
  userData: User | undefined;
  myAccounts: Account[] | undefined = [];
  constructor(private service: ServerService, private componentService: ComponentService) {
    this.userId = this.componentService.getUserIdLocalStorage();
  }

  ngOnInit(): void {
    this.service.getMyAccounts(this.userId).subscribe((res) => {
      this.myAccounts = res;
    });

    this.service.getUserData(this.userId).subscribe((res) => {
      this.userData = res;
    });
  }



  createAccount() {
    let accNumber = Math.floor(Math.random() * (9999999999999 - 1000000000000)).toString();
    let shabaNumber = Math.floor(Math.random() * (9999999999999999 - 1000000000000000)).toString();
    let balance = 100000000;


    let account: Account = {
      "accNumber": accNumber,
      "shabaNumber": shabaNumber,
      "accBalance": balance,
      "user": this.userData
    };


    this.service.createAccount(account).subscribe(res => {

      this.ngOnInit();
      Swal.fire({
        title: "موفق",
        text: res.messages.success[0],
        icon: 'success',
        confirmButtonText: 'تمام'
      });
    });
  }


  deleteMyAccount(accId: number | undefined) {
    let id: number = accId == undefined ? 0 : accId;
    this.service.deleteMyAccount(id).subscribe(res => {
      this.ngOnInit();
      Swal.fire({
        title: "موفق",
        icon: 'success',
        confirmButtonText: 'تمام'
      });

    })
  }


}
