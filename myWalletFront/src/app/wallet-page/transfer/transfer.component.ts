import { Component, OnInit } from '@angular/core';
import { WalletPageComponent } from "../wallet-page.component";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Transfer } from '../../dto/transfer';
import { Account } from '../../dto/account';
import { Wallet } from '../../dto/wallet';
import { ServerService } from '../../server.service';
import { User } from '../../dto/user';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [WalletPageComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent implements OnInit {

  transferForm !: FormGroup;
  userId !: number;
  userdata !: User;
  constructor(
    private form: FormBuilder,
    private service: ServerService,
    private router:Router
  ) {
    const user = localStorage.getItem("userId");
    let userId: string = user == null ? "" : user.toString();
    this.userId = parseInt(userId);
  }

  amount = new FormControl();
  description = new FormControl();
  toAccount = new FormControl();
  ngOnInit(): void {
    this.getUserData();
  }


  getUserData() {
    this.service.getUserData(this.userId).subscribe(res => {
      this.userdata = res
    })
  }

  transferSubmit(): void {
    let toAccount: Account = {
      accNumber: this.toAccount.value
    };

    let transfer: Transfer = {
      amount: this.amount.value,
      description: this.description.value,
      fromWallet: this.userdata.wallet,
      toAccount: toAccount
    }

    this.service.withdrawalWallet(transfer).subscribe(res => {
      Swal.fire({
        title: "موفق",
        text: `${res.messages?.['success']}`,
        icon: 'success',
        confirmButtonText: 'تمام'
      });
      
      this.router.navigate(["/wallet"]);
    })
  }


}
