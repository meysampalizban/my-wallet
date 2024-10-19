import { Component, OnInit } from '@angular/core';
import { WalletPageComponent } from '../wallet-page.component';
import { ServerService } from '../../service/server.service';
import { Deposit } from '../../dto/requests/deposit';
import { Withdrawal } from '../../dto/requests/withdrawal';
import { History } from './history';
import { CommonModule } from '@angular/common';
import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ComponentService } from '../../service/component.service';
import { User } from '../../dto/requests/user';

@Component({
  selector: 'app-list-transaction',
  standalone: true,
  imports: [WalletPageComponent, CommonModule, FontAwesomeModule],
  templateUrl: './list-transaction.component.html',
  styleUrl: './list-transaction.component.css'
})

export class ListTransactionComponent implements OnInit {
  faMinus = faMinus;
  faPlus = faPlus;
  deposit !: Deposit[];
  withdrawal!: Withdrawal[];
  userData !: User;
  private userId: number;

  arr: History[] = [];


  constructor(private service: ServerService, private componentService: ComponentService) {
    this.userId = this.componentService.getUserIdLocalStorage();
    this.service.getUserData(this.userId).subscribe((res) => {
      this.userData = res;

      this.service.getDepositHistory(res.wallet.id).forEach(res => {

        res.forEach(depo => {
          let ob: History = {
            id: depo.id,
            desciption: depo.desciption,
            type: "deposit",
            amount: depo.depositAmount,
            refNumber: depo.refNumber,
            createdAt: depo.createdAt
          };
          this.arr.push(ob);
        })
      });

      this.service.getWithdrawalHistory(res.wallet.id).subscribe(res => {
        res.forEach(depo => {
          let ob: History = {
            id: depo.id,
            desciption: depo.desciption,
            type: "withdrawal",
            amount: depo.withdrawalAmount,
            refNumber: depo.refNumber,
            createdAt: depo.createdAt
          };
          this.arr.push(ob);
        })
      });

    });
  }


  ngOnInit(): void {

    this.arr = this.arr.sort((a, b) => {
      return a.createdAt.getTime() - b.createdAt.getTime();
    });


  }



}
