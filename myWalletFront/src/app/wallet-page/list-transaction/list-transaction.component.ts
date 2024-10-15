import { Component, OnInit } from '@angular/core';
import { WalletPageComponent } from '../wallet-page.component';
import { ServerService } from '../../server.service';
import { Deposit } from '../../dto/deposit';
import { Withdrawal } from '../../dto/withdrawal';
import { History } from './history';
import { CommonModule } from '@angular/common';
import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

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

  arr: History[] = [];


  constructor(private service: ServerService) { }

  ngOnInit(): void {
    this.service.getDepositHistory(6).forEach(res => {
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

    this.service.getWithdrawalHistory(6).subscribe(res => {
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


    this.arr = this.arr.sort((a, b) => {
      return a.createdAt.getTime() - b.createdAt.getTime();
    });
  }


}
