export interface ResponseErrorData {
    error: string
}

export interface ResponseErrorResponse {
    status: number,
    data: ResponseErrorData 
}

export default interface ResponseError {
    response: ResponseErrorResponse
}