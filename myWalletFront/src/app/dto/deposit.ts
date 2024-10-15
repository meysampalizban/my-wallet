export interface Deposit {
    id: number;
    depositAmount: number;
    refNumber?: number;
    desciption?: string;
    createdAt: Date;
}