import Member from "../../models/member/Member";
import { useAppSelector } from "../../store/config/hooks";
import BackgroundImage from "../home/BackgroundImage";
import MemberAddForm from "./MemberAddForm";
import { useEffect } from "react";

export default function MemberAddPage() {
  const member: Member | undefined = useAppSelector(
    (state) => state.memberAdd.member
  );

  useEffect(() => {
    console.log(member);
  }, [member]);

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center">
        <div className="w-2/4">
          <MemberAddForm />
        </div>
      </div>
    </BackgroundImage>
  );
}
