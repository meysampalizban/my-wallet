export interface History {
    id: number;
    amount: number;
    type: string;
    refNumber?: number;
    desciption?: string;
    createdAt: Date;
  }