import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Register } from "../dto/requests/register";
import { Response } from "../dto/responses/response";
import { catchError, Observable, retry, throwError } from "rxjs";
import { User } from "../dto/requests/user";
import { Account } from "../dto/requests/account";
import { Charge } from "../dto/requests/charge";
import { Deposit } from "../dto/requests/deposit";
import { Withdrawal } from "../dto/requests/withdrawal";
import { Transfer } from "../dto/requests/transfer";
import { ComponentService } from "./component.service";

@Injectable({
  providedIn: "root",
})
export class ServerService implements OnInit {
  public URL: string = "http://localhost:8080/";
  private headers;

  constructor(private Http: HttpClient, private componentSrvice: ComponentService) {
    this.headers = {
      headers: new HttpHeaders({
        "Accept": "application/json",
        "Content-Type": "application/json",
        "Authorization": this.componentSrvice.getUserToken(),
        "userId": this.componentSrvice.getUserIdLocalStorage()
      })
    };
  }

  ngOnInit(): void {
  }

  private handleError(error: HttpErrorResponse): Observable<Response> {
    let response: Response;
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      response = {
        statusCode: error.error.statusCode,
        statusType: error.error.statusType,
        messages: error.error.messages,
        timestamp: error.error.timestamp,
      };
    }
    return throwError(() => response);
  }

  public createUser(user: Register): Observable<Response> {
    let createUserUrl = this.URL + "api/user/createuser";

    const headers = {
      headers: new HttpHeaders({
        "Accept": "application/json",
        "Content-Type": "application/json"
      })
    };
    return this.Http.post<Response>(createUserUrl, user, headers).pipe(retry(2), catchError(this.handleError.bind(this)));
  }


  getDepositHistory(walletId: number) {
    let getDepositHistoryUrl = this.URL + "api/transaction/getdeposithistory/" + walletId;
    return this.Http.get<Deposit[]>(getDepositHistoryUrl, this.headers);
  }
  getWithdrawalHistory(walletId: number) {
    let getWithdrawalHistory = this.URL + "api/transaction/getwithdrawalhistory/" + walletId;
    return this.Http.get<Withdrawal[]>(getWithdrawalHistory, this.headers);
  }


  getMyAccounts(userId: number) {
    let getMyAccountsUrl = this.URL + "api/account/getaccounts/" + userId;
    return this.Http.get<Account[]>(getMyAccountsUrl, this.headers);
  }

  public getUserData(userId: number) {
    let getUser = this.URL + "api/user/getuser/" + userId;
    return this.Http.get<User>(getUser, this.headers);
  }




  public createAccount(account: Account): Observable<Response> {
    let createAccountUrl = this.URL + "api/account/createaccount";
    return this.Http.post<Response>(createAccountUrl, account, this.headers);
  }

  public deleteMyAccount(accId: number): Observable<Response> {
    let deleteMyaccount = this.URL + "api/account/deleteaccount/" + accId;
    return this.Http.delete<Response>(deleteMyaccount, this.headers);
  }

  public chargeWallet(charge: Charge): Observable<Response> {
    let chargeUrl = this.URL + "api/transaction/chargewallet";
    return this.Http.post<Response>(chargeUrl, charge, this.headers);
  }

  public withdrawalWallet(transfer: Transfer): Observable<Response> {
    let withdrawalUrl = this.URL + "api/transaction/transferfromwallet";
    return this.Http.post<Response>(withdrawalUrl, transfer, this.headers);
  }

}
