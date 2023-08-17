import AlertError from "../../../../models/error/AlertError";
import { LendingsReturn } from "../../../../models/lending/LendingsReturn";
import RequestQueryParams from "../../../../models/request/RequestQueryParams";

export interface LendingsUnreturnedState {
  lendingsReturn: LendingsReturn;
  totalPages: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
