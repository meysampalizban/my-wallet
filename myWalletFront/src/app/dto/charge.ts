import { Account } from "./account"
import { Wallet } from "./wallet";

export interface Charge {
    fromAccount?: Account;
    toWallet?: Wallet;
    amount?: number;
    description?: string;
}