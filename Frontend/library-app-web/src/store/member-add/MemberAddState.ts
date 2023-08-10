import AlertError from "../../models/error/AlertError";
import Member from "../../models/member/Member";

export default interface MemberAddState {
  loading: boolean;
  member: Member;
  isError: boolean;
  error?: AlertError;
}
