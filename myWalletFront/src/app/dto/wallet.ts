export interface Wallet {
    id?: number,
    wbalance?: number,
    deposit?: Array<string>,
    withdrawals?: Array<string>,
    createdAt?: number,
    updatedAt?: number
}