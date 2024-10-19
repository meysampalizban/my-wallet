import { messagesRes } from "./messages-res";


export interface Response {
    statusCode: number,
    statusType: string,
    messages: messagesRes;
    timestamp: number,
    
}