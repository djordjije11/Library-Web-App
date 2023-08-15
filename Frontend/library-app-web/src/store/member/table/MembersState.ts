import AlertError from "../../../models/error/AlertError";
import MemberShort from "../../../models/member/MemberShort";

export default interface MembersState {
  members: MemberShort[];
  totalPages: number;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
