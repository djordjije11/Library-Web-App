import AlertError from "../../../models/error/AlertError";
import { LendingsByMember } from "../../../models/lending/LendingsByMember";

export interface LendingsReturnState {
  lendingsByMember: LendingsByMember;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
