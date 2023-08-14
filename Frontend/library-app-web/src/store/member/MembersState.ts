import AlertError from "../../models/error/AlertError";
import Member from "../../models/member/Member";

export default interface MembersState {
  members: Member[];
  totalPages: number;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
