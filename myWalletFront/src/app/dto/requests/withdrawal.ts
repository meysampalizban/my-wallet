export interface Withdrawal {
    id: number;
    withdrawalAmount: number;
    refNumber?: number;
    desciption?: string;
    createdAt: Date;
}