import Member from "../../models/member/Member";

export default interface MembersState{
    members: Member[],
    loading: boolean,
    error: string
}