import ResponseError, { ResponseErrorResponse } from "../../request/ResponseError";

export default interface AlertError {
    statusCode?: number,
    error?: string
}

export function constructAlertError(responseError : ResponseError) : AlertError {
    const response : ResponseErrorResponse = responseError.response;
    return {
        statusCode: response.status,
        error: response.data.error
    }
}