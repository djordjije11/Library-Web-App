import { XMarkIcon } from "@heroicons/react/24/outline";
import MemberDetail from "../../models/member/MemberDetail";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MemberUpdateState from "../../store/member/update/MemberUpdateState";
import { updateMemberAsyncThunk } from "../../store/member/update/memberUpdateThunks";
import MemberForm from "./MemberForm";

export interface MemberUpdateContainerProps {
  close: () => void;
}

export default function MemberUpdateContainer(
  props: MemberUpdateContainerProps
) {
  const memberUpdateState: MemberUpdateState = useAppSelector(
    (state) => state.memberUpdate
  );
  const dispatch = useAppDispatch();
  const { close } = props;

  async function onSubmitAsync(member: MemberDetail): Promise<MemberDetail> {
    try {
      return await dispatch(updateMemberAsyncThunk(member)).unwrap();
    } finally {
      close();
    }
  }

  function MemberUpdateFormHeader(): JSX.Element {
    return (
      <div
        className="font-bold"
        style={{
          display: "grid",
          gridAutoFlow: "column",
          gridTemplateColumns: "8% auto 8%",
        }}
      >
        <div></div>
        <div className="flex justify-center">
          <h3>Edit a member</h3>
        </div>
        <div className="flex justify-center">
          <button type="button" onClick={close}>
            <XMarkIcon width={"18px"} />
          </button>
        </div>
      </div>
    );
  }

  return (
    <MemberForm
      member={memberUpdateState.member}
      onSubmitAsync={onSubmitAsync}
      clearOnSubmit={false}
      formHeader={<MemberUpdateFormHeader />}
    />
  );
}
