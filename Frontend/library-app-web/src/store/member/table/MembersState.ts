import AlertError from "../../../models/error/AlertError";
import MemberShort from "../../../models/member/MemberShort";
import RequestQueryParams from "../../../models/request/RequestQueryParams";

export default interface MembersState {
  members: MemberShort[];
  totalPages: number;
  totalItemsCount: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
