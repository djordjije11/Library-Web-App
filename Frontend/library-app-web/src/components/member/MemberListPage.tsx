import {
  Button,
  Card,
  CardBody,
  IconButton,
  Tooltip,
} from "@material-tailwind/react";
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
import {
  PencilIcon,
  TrashIcon,
  UserPlusIcon,
} from "@heroicons/react/24/outline";
import MemberShort from "../../models/member/MemberShort";
import { Row } from "react-table";
import { getMembersAsyncThunk } from "../../store/member/table/membersThunks";
import { Box, Modal } from "@mui/material";
import MemberUpdateContainer from "./MemberUpdateContainer";
import Loader from "../shared/loader/Loader";
import { useNavigate } from "react-router-dom";
import { ADD_MEMBER_PAGE } from "../routes/AppRouter";
import GrayLoader from "../shared/loader/GrayLoader";

export default function MemberListPage() {
  const memberUpdateState: MemberUpdateState = useAppSelector(
    (state) => state.memberUpdate
  );
  const dispatch = useAppDispatch();
  const [showModal, setShowModal] = useState<boolean>(false);
  const navigate = useNavigate();

  async function handleClickOnUpdateMemberAsync(member: MemberShort) {
    setShowModal(true);
    try {
      await dispatch(getMemberAsyncThunk(member.id)).unwrap();
    } catch (error) {
      setShowModal(false);
      handleRecordNotFoundError();
    }
  }

  async function handleClickOnDeleteMemberAsync(member: MemberShort) {
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
            size="sm"
            variant="text"
            onClick={() => handleClickOnUpdateMemberAsync(member)}
          >
            <PencilIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
        <Tooltip content="Delete">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => handleClickOnDeleteMemberAsync(member)}
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
              <GrayLoader />
            </div>
          ) : (
            <MemberUpdateContainer close={closeModalAsync} />
          )}
        </Box>
      </Modal>
    );
  }

  function renderHeaderChildren(searchInputField: JSX.Element): JSX.Element {
    return (
      <div className="mt-2 flex justify-between">
        {searchInputField}
        <Button
          className="mx-2 px-4 border border-blue-gray-100 hover:border-blue-gray-300"
          color="white"
          onClick={() => navigate(ADD_MEMBER_PAGE)}
        >
          <div className="flex justify-between items-center gap-3">
            <UserPlusIcon className="w-4 h-4" />
            <span className="text-xs text-gray-800">Add a new member</span>
          </div>
        </Button>
      </div>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        <div className="w-9/12 my-4 min-w-min h-4/5">
          <ModalUpdateMember />
          <Card className="w-full h-full">
            <div className="flex justify-center items-center mt-2 font-bold text-lg">
              <h3>List of members</h3>
            </div>
            <MemberTable
              rowActions={rowActions}
              renderHeaderChildren={renderHeaderChildren}
            />
          </Card>
        </div>
      </div>
    </BackgroundImage>
  );
}
