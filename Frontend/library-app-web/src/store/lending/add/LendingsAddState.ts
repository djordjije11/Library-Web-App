import AlertError from "../../../models/error/AlertError";
import { LendingsAdd } from "../../../models/lending/LendingsAdd";

export default interface LendingsAddState {
  lendingsAdd: LendingsAdd;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
