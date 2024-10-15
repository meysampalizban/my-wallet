import { Component, OnInit } from '@angular/core';
import { WalletPageComponent } from "../wallet-page.component";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';
import { ServerService } from '../../server.service';
import { Account } from '../../dto/account';
import { Wallet } from '../../dto/wallet';
import { Charge } from '../../dto/charge';
import { User } from '../../dto/user';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';


@Component({
  selector: 'app-charge',
  standalone: true,
  imports: [WalletPageComponent, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './charge.component.html',
  styleUrl: './charge.component.css'
})
export class ChargeComponent implements OnInit {
  chargeForm !: FormGroup;
  listAccounts!: Account[];
  userId !: number;
  userdata !: User;
  constructor(
    private  router : Router,
    private form: FormBuilder,
    private service: ServerService
  ) {
    const user = localStorage.getItem("userId");
    let userId: string = user == null ? "" : user.toString();
    this.userId = parseInt(userId);
  }

  amount = new FormControl();
  description = new FormControl();
  fromAccount = new FormControl();

  ngOnInit(): void {
    this.getMyAccounts();
    this.getUserData();
  }

  getUserData() {
    this.service.getUserData(this.userId).subscribe(res => {
      this.userdata = res
    })
  }

  getMyAccounts() {
    this.service.getMyAccount(this.userId).subscribe(res => {
      this.listAccounts = res;
    })
  }

  chargeSubmit(): void {
    let fromAccount: Account = this.fromAccount.value;
    let toWallet: Wallet | undefined = this.userdata.wallet;
    let amount: number = this.amount.value;
    let description: string = this.description.value;

    let charge: Charge = {
      "fromAccount": fromAccount,
      "toWallet": toWallet,
      "amount": amount,
      "description": description
    };

    this.service.chargeWallet(charge).subscribe(res => {
      Swal.fire({
        title: "موفق",
        text: `${res.messages?.['success']}`,
        icon: 'success',
        confirmButtonText: 'تمام'
      });
      
      this.router.navigate(["/wallet"]);
    });

  }

}
