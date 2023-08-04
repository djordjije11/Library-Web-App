import ResponseError, {
  ResponseErrorResponse,
} from "../../request/ResponseError";
import { ErrorType } from "./ErrorType";

export default interface AlertError {
  statusCode?: number;
  error?: ErrorType;
  message?: string;
}

export function constructAlertError(responseError: ResponseError): AlertError {
  const response: ResponseErrorResponse = responseError.response;
  return {
    statusCode: response.status,
    error: response.data.error as ErrorType,
  };
}
