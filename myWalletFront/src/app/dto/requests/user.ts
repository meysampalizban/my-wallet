import { Account } from "./account";
import { Wallet } from "./wallet";

export interface User {
    id?: number,
    email?: string,
    phoneNumber?: string,
    nationalCode?: string,
    birthDate?: string,
    firstName?: string,
    lastName?: string,
    sex?: string,
    militaryStatus?: string,
    accounts?: Account[],
    wallet: Wallet,
    createdAt?: number,
    updatedAt?: number,
    _token?: string
}