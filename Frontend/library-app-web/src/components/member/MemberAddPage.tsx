import BackgroundImage from "../home/BackgroundImage";
import { addMemberAsyncThunk } from "../../store/member/add/memberAddThunks";
import { useAppDispatch } from "../../store/config/hooks";
import MemberForm from "./MemberForm";
import MemberDetail from "../../models/member/MemberDetail";
import MemberAdd from "../../models/member/MemberAdd";

export default function MemberAddPage() {
  const dispatch = useAppDispatch();

  async function onSubmitAsync(member: MemberDetail): Promise<MemberDetail> {
    return await dispatch(addMemberAsyncThunk(member as MemberAdd)).unwrap();
  }

  function MemberAddFormHeader(): JSX.Element {
    return (
      <div className="flex justify-center font-bold">
        <h3>Add a new member</h3>
      </div>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center">
        <div className="w-2/4">
          <MemberForm
            onSubmitAsync={onSubmitAsync}
            clearOnSubmit={true}
            formHeader={<MemberAddFormHeader />}
          />
        </div>
      </div>
    </BackgroundImage>
  );
}
