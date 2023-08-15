import AlertError from "../../../models/error/AlertError";
import MemberDetail from "../../../models/member/MemberDetail";

export default interface MemberUpdateState {
  loading: boolean;
  member: MemberDetail;
  isError: boolean;
  error?: AlertError;
}
