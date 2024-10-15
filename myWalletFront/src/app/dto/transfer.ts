import { Account } from "./account";
import { Wallet } from "./wallet"

export interface Transfer {
    fromWallet?: Wallet;
    toAccount?: Account;
    amount?: number;
    description?: string;

}