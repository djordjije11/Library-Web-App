import Member from "../../models/member/Member";
import MemberForm from "./MemberForm";
import { Dispatch, SetStateAction } from "react";

export interface MemberUpdateContainerProps {
  member: Member;
  setMember: Dispatch<SetStateAction<Member>>;
  close: () => void;
}

export default function MemberUpdateContainer(
  props: MemberUpdateContainerProps
) {
  const { member, setMember, close } = props;

  async function onSubmitAsync(member: Member) {
    console.log(member);
    close();
    await Promise.resolve();
    return member;
  }

  return (
    <MemberForm
      member={member}
      setMember={setMember}
      onSubmitAsync={onSubmitAsync}
    />
  );
}
