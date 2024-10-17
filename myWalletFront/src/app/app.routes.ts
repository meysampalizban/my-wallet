import { Routes } from "@angular/router";
import { RegisterUserComponent } from "./register-user/register-user.component";
import { AppComponent } from "./app.component";
import { WalletPageComponent } from "./wallet-page/wallet-page.component";
import { ChargeComponent } from "./wallet-page/charge/charge.component";
import { TransferComponent } from "./wallet-page/transfer/transfer.component";
import { ListTransactionComponent } from "./wallet-page/list-transaction/list-transaction.component";
export const routes: Routes = [
  { path: "register", component: RegisterUserComponent },
  { path: "wallet", component: WalletPageComponent },
  { path: "charge", component: ChargeComponent },
  { path: "transfer", component: TransferComponent },
  { path: "list-transaction", component: ListTransactionComponent }
];
