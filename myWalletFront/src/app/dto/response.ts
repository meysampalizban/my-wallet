

export interface Response {
    timestamp: Date,
    statusCode: number,
    statusType: string,
    messages: Record<string, any>;
}