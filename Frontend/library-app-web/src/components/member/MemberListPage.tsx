import { Button, Card, IconButton, Tooltip } from "@material-tailwind/react";
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
  BookOpenIcon,
  PencilIcon,
  TrashIcon,
  UserPlusIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";
import MemberShort from "../../models/member/MemberShort";
import { Row } from "react-table";
import { getMembersAsyncThunk } from "../../store/member/table/membersThunks";
import { Box, Modal } from "@mui/material";
import MemberUpdateContainer from "./MemberUpdateContainer";
import { useNavigate } from "react-router-dom";
import { ADD_MEMBER_PAGE } from "../routes/AppRouter";
import GrayLoader from "../shared/loader/GrayLoader";
import { lendingsByMemberActions } from "../../store/lending/table/by-member/all/lendingsByMemberSlice";
import LendingByMemberTable from "../lending/LendingByMemberTable";
import { LendingsByMemberState } from "../../store/lending/table/by-member/all/LendingsByMemberState";

export default function MemberListPage() {
  const memberUpdateState: MemberUpdateState = useAppSelector(
    (state) => state.memberUpdate
  );
  const [memberForLendings, setMemberForLendings] = useState<MemberShort>(
    {} as MemberShort
  );
  const dispatch = useAppDispatch();
  const [showUpdateMemberModal, setShowUpdateMemberModal] =
    useState<boolean>(false);
  const [showLendingsByMemberModal, setShowLendingsByMemberModal] =
    useState<boolean>(false);
  const navigate = useNavigate();

  async function handleClickOnViewLendingsHistoryAsync(member: MemberShort) {
    setMemberForLendings(member);
    dispatch(lendingsByMemberActions.setMember(member));
    setShowLendingsByMemberModal(true);
  }

  async function handleClickOnUpdateMemberAsync(member: MemberShort) {
    setShowUpdateMemberModal(true);
    try {
      await dispatch(getMemberAsyncThunk(member.id)).unwrap();
    } catch (error) {
      setShowUpdateMemberModal(false);
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
    setShowUpdateMemberModal(false);
    await dispatch(getMembersAsyncThunk());
  }

  function rowActions(row: Row<{}>): JSX.Element {
    const member = row.original as MemberShort;
    return (
      <div>
        <Tooltip content="View lendings history">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => handleClickOnViewLendingsHistoryAsync(member)}
          >
            <BookOpenIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
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

  function ModalLendingsByMember(): JSX.Element {
    return (
      <Modal
        open={showLendingsByMemberModal}
        onClose={() => setShowLendingsByMemberModal(false)}
        className="flex justify-center items-center"
      >
        <Box
          sx={{
            width: "70%",
            border: "none",
            minWidth: "min-content",
            minHeight: "min-content",
            height: "60%",
          }}
        >
          <Card className="w-full h-full">
            <div
              className="font-bold my-3"
              style={{
                display: "grid",
                gridAutoFlow: "column",
                gridTemplateColumns: "8% auto 8%",
              }}
            >
              <div></div>
              <div className="flex justify-center">
                <h3>
                  Member: {memberForLendings?.firstname}{" "}
                  {memberForLendings?.lastname}
                </h3>
              </div>
              <div className="flex justify-center">
                <button
                  type="button"
                  onClick={() => setShowLendingsByMemberModal(false)}
                >
                  <XMarkIcon width={"18px"} />
                </button>
              </div>
            </div>
            <LendingByMemberTable />
          </Card>
        </Box>
      </Modal>
    );
  }

  function ModalUpdateMember(): JSX.Element {
    return (
      <Modal
        open={showUpdateMemberModal}
        onClose={() => setShowUpdateMemberModal(false)}
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
          <ModalLendingsByMember />
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
