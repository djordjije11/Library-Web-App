import AlertError from "../../../../../models/error/AlertError";
import { LendingsByMember } from "../../../../../models/lending/LendingsByMember";
import RequestQueryParams from "../../../../../models/request/RequestQueryParams";

export interface LendingsByMemberUnreturnedState {
  lendingsByMember: LendingsByMember;
  totalPages: number;
  totalItemsCount: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
