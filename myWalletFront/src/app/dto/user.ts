import { Wallet } from "./wallet";

export interface User {
    id?: number;
    email?: string,
    phoneNumber?: number,
    nationalCode?: 5454554,
    birthDate?: string,
    firstName?: string,
    lastName?: string,
    sex?: string,
    militaryStatus?: string,
    accounts?: Array<string>,
    wallet?: Wallet,
    createdAt?: number,
    updatedAt?: number
}