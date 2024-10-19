import { User } from "./user";

export interface Account {
    id?: number;
    accNumber?: string;
    shabaNumber?: string;
    accBalance?: number;
    user?: User
}
