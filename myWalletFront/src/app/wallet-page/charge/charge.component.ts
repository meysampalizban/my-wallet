import { Component, OnInit } from '@angular/core';
import { WalletPageComponent } from "../wallet-page.component";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';
import { ServerService } from '../../service/server.service';
import { Account } from '../../dto/account';
import { Wallet } from '../../dto/wallet';
import { Charge } from '../../dto/charge';
import { User } from '../../dto/user';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { ComponentService } from '../../service/component.service';


@Component({
  selector: 'app-charge',
  standalone: true,
  imports: [WalletPageComponent, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './charge.component.html',
  styleUrl: './charge.component.css'
})
export class ChargeComponent implements OnInit {
  chargeForm !: FormGroup;
  listAccounts: Account[] | undefined = [];
  userId !: number;
  userdata: User | undefined;

  constructor(
    private router: Router,
    private service: ServerService,
    private componentService: ComponentService
  ) {
    this.userId = this.componentService.getUserIdLocalStorage();
  }

  amount = new FormControl();
  description = new FormControl();
  fromAccount = new FormControl();

  ngOnInit(): void {
    this.listAccounts = this.service.getMyAccounts(this.userId);
    this.userdata = this.service.getUserData(this.userId);
  }





  chargeSubmit(): void {
    let fromAccount: Account = this.fromAccount.value;
    let toWallet: Wallet | undefined = this.userdata?.wallet;
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
        icon: 'success',
        confirmButtonText: 'تمام'
      });

      this.router.navigate(["/wallet"]);
    });

  }

}
