import { Deposit } from "./deposit";
import { Withdrawal } from "./withdrawal";

export interface Wallet {
    id: number,
    wbalance: number,
    deposit: Deposit[],
    withdrawals: Withdrawal[],
    createdAt: number,
    updatedAt: number
}