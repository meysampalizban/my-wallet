

export interface Response {
    statusCode: number,
    statusType: string,
    messages: Map<string, Array<any>>;
    timestamp: Date,
}