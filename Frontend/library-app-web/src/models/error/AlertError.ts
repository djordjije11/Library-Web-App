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
  const alertError: AlertError = {
    statusCode: response.status,
    error: response.data.error as ErrorType,
  };

  if (response.data.message !== undefined) {
    alertError.message = response.data.message;
  }
  if (response.data.messages !== undefined) {
    alertError.message = parseJsonToString(response.data.messages);
  }

  return alertError;
}

// function parseJsonToString(obj: any): string {
//   if (obj === undefined || obj === null) {
//     throw Error();
//   }
//   var text: string[] = [];
//   Object.keys(obj).forEach((key) => {
//     if (typeof obj[key] === "object") {
//       text.push(`${key}: [ ${parseJsonToString(obj[key])} ]`);
//       return;
//     }
//     text.push(`${key}: ${obj[key]}`);
//   });
//   if (text.length === 0) {
//     throw Error();
//   }
//   if (text.length === 1) {
//     return text[0];
//   }

//   return text.join(", ");
// }

function parseJsonToString(obj: any): string {
  if (obj === undefined || obj === null) {
    throw Error();
  }
  var text: string[] = [];
  Object.keys(obj).forEach((key) => {
    if (typeof obj[key] === "object") {
      text.push(parseJsonToString(obj[key]));
      return;
    }
    text.push(obj[key]);
  });
  if (text.length === 0) {
    throw Error();
  }
  if (text.length === 1) {
    return text[0];
  }

  return text.join(" ");
}
