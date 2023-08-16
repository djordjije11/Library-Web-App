import { Card, IconButton, Tooltip } from "@material-tailwind/react";
import BackgroundImage from "../home/BackgroundImage";
import MemberTable from "./MemberTable";
import MemberUpdateState from "../../store/member/update/MemberUpdateState";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import { useState } from "react";
import { getMemberAsyncThunk } from "../../store/member/update/memberUpdateThunks";
import {
  handleMemberDeleteError,
  handleRecordNotFoundError,
} from "../../services/alert/errorHandler";
import { questionAlertIsSureAsync } from "../../services/alert/questionAlert";
import { deleteMemberAsync } from "../../request/member/memberRequests";
import { successAlert } from "../../services/alert/successHandler";
import { PencilIcon, TrashIcon } from "@heroicons/react/24/outline";
import MemberShort from "../../models/member/MemberShort";
import { Row } from "react-table";
import { getMembersAsyncThunk } from "../../store/member/table/membersThunks";
import { Box, Modal } from "@mui/material";
import MemberUpdateContainer from "./MemberUpdateContainer";
import Loader from "../shared/Loader";

export default function MemberListPage() {
  const memberUpdateState: MemberUpdateState = useAppSelector(
    (state) => state.memberUpdate
  );
  const dispatch = useAppDispatch();
  const [showModal, setShowModal] = useState<boolean>(false);

  async function handleClickOnUpdateMember(member: MemberShort) {
    setShowModal(true);
    try {
      await dispatch(getMemberAsyncThunk(member.id)).unwrap();
    } catch (error) {
      setShowModal(false);
      handleRecordNotFoundError();
    }
  }

  async function handleClickOnDeleteMember(member: MemberShort) {
    const confirmed = await questionAlertIsSureAsync(
      "Are you sure you want to delete?",
      `Member: ${member.firstname} ${member.lastname}`
    );
    if (confirmed === false) {
      return;
    }
    try {
      await deleteMemberAsync(member.id);
      successAlert();
      await dispatch(getMembersAsyncThunk());
    } catch (error) {
      handleMemberDeleteError();
    }
  }

  async function closeModalAsync() {
    setShowModal(false);
    await dispatch(getMembersAsyncThunk());
  }

  function rowActions(row: Row<{}>): JSX.Element {
    const member = row.original as MemberShort;
    return (
      <div>
        <Tooltip content="Edit">
          <IconButton
            variant="text"
            onClick={() => handleClickOnUpdateMember(member)}
          >
            <PencilIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
        <Tooltip content="Delete">
          <IconButton
            variant="text"
            onClick={() => handleClickOnDeleteMember(member)}
          >
            <TrashIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
      </div>
    );
  }

  function ModalUpdateMember(): JSX.Element {
    return (
      <Modal
        open={showModal}
        onClose={() => setShowModal(false)}
        className="flex justify-center items-center"
      >
        <Box
          sx={{
            width: "50%",
          }}
        >
          {memberUpdateState.loading ? (
            <div className="w-full flex justify-center">
              <Loader />
            </div>
          ) : (
            <MemberUpdateContainer close={closeModalAsync} />
          )}
        </Box>
      </Modal>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center">
        <div className="w-3/4 my-4">
          <ModalUpdateMember />
          <Card>
            <MemberTable rowActions={rowActions} />
          </Card>
        </div>
      </div>
    </BackgroundImage>
  );
}
