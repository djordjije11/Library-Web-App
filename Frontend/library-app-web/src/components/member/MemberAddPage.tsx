import Member from "../../models/member/Member";
import BackgroundImage from "../home/BackgroundImage";
import { addMemberAsyncThunk } from "../../store/member-add/memberAddThunks";
import { useAppDispatch } from "../../store/config/hooks";
import MemberForm from "./MemberForm";
import { useState } from "react";

export default function MemberAddPage() {
  const [memberInput, setMemberInput] = useState<Member>({} as Member);
  const dispatch = useAppDispatch();

  function clearFormFields() {
    setMemberInput({} as Member);
  }

  async function onSubmitAsync(member: Member): Promise<Member> {
    const result = await dispatch(addMemberAsyncThunk(member)).unwrap();
    clearFormFields();
    return result;
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center">
        <div className="w-2/4">
          <MemberForm
            member={memberInput}
            setMember={setMemberInput}
            onSubmitAsync={onSubmitAsync}
          />
        </div>
      </div>
    </BackgroundImage>
  );
}
