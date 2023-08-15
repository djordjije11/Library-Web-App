export interface ResponseErrorData {
  error: string;
  message?: string;
  messages?: any;
}

export interface ResponseErrorResponse {
  status: number;
  data: ResponseErrorData;
}

export default interface ResponseError {
  response: ResponseErrorResponse;
}
