import MemberAdd from "../../../models/member/MemberAdd";
import AlertError from "../../../models/error/AlertError";

export default interface MemberAddState {
  loading: boolean;
  member: MemberAdd;
  isError: boolean;
  error?: AlertError;
}
