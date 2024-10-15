import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Register } from "./dto/register";
import { Response } from "./dto/response";
import { Observable } from "rxjs";
import { User } from "./dto/user";
import { Account } from "./dto/account";
import { Charge } from "./dto/charge";
import { Deposit } from "./dto/deposit";
import { Withdrawal } from "./dto/withdrawal";
import { Transfer } from "./dto/transfer";

@Injectable({
  providedIn: "root",
})
export class ServerService {
  public URL: string = "http://localhost:8080/";
  private headers = new HttpHeaders();

  constructor(private Http: HttpClient) {
    this.headers.set("Accept", "application/json").set("Content-Type", "application/json");
  }

  getDepositHistory(walletId: number) {
    let getDepositHistoryUrl = this.URL + "api/transaction/getdeposithistory/" + walletId;
    return this.Http.get<Deposit[]>(getDepositHistoryUrl, { headers: this.headers });
  }
  getWithdrawalHistory(walletId: number) {
    let getWithdrawalHistory = this.URL + "api/transaction/getwithdrawalhistory/" + walletId;
    return this.Http.get<Withdrawal[]>(getWithdrawalHistory, { headers: this.headers });
  }


  getMyAccount(userId: number): Observable<Account[]> {
    let getMyAccountsUrl = this.URL + "api/account/getaccounts/" + userId;
    return this.Http.get<Account[]>(getMyAccountsUrl, { headers: this.headers });
  }

  public getUserData(userId: number): Observable<User> {
    let getUser = this.URL + "api/user/getuser/" + userId;
    return this.Http.get<User>(getUser, { headers: this.headers });
  }

  public createUser(user: Register): Observable<Response> {
    let createUserUrl = this.URL + "api/user/createuser";

    return this.Http.post<Response>(createUserUrl, user, { headers: this.headers });
  }

  public createAccount(account: Account): Observable<Response> {
    let createAccountUrl = this.URL + "api/account/createaccount";
    return this.Http.post<Response>(createAccountUrl, account, { headers: this.headers });
  }

  public deleteMyAccount(accId: number): Observable<Response> {
    let deleteMyaccount = this.URL + "api/account/deleteaccount/" + accId;
    return this.Http.delete<Response>(deleteMyaccount, { headers: this.headers });
  }

  public chargeWallet(charge: Charge): Observable<Response> {
    console.log(charge);
    let chargeUrl = this.URL + "api/transaction/chargewallet";
    return this.Http.post<Response>(chargeUrl, charge, { headers: this.headers });
  }

  public withdrawalWallet(transfer: Transfer): Observable<Response> {
    console.log(transfer);
    let withdrawalUrl = this.URL + "api/transaction/transferfromwallet";
    return this.Http.post<Response>(withdrawalUrl, transfer, { headers: this.headers });
  }

}
