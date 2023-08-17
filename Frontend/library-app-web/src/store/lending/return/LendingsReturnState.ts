import AlertError from "../../../models/error/AlertError";
import { LendingsReturn } from "../../../models/lending/LendingsReturn";

export interface LendingsReturnState {
  lendingsReturn: LendingsReturn;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
