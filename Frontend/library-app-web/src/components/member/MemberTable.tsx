import { useMemo, useState, useRef } from "react";
import { Column, Row } from "react-table";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MembersState from "../../store/member/table/MembersState";
import { getMembersAsyncThunk } from "../../store/member/table/membersThunks";
import CompleteTable, { CompleteTableRef } from "../table/CompleteTable";
import { IconButton, Tooltip } from "@material-tailwind/react";
import { PencilIcon, TrashIcon } from "@heroicons/react/24/outline";
import { Modal, Box } from "@mui/material";
import MemberUpdateContainer from "./MemberUpdateContainer";
import MemberShort from "../../models/member/MemberShort";
import { getMemberAsyncThunk } from "../../store/member/update/memberUpdateThunks";
import MemberUpdateState from "../../store/member/update/MemberUpdateState";
import Loader from "../shared/Loader";
import {
  handleMemberDeleteError,
  handleRecordNotFoundError,
} from "../../services/alert/errorHandler";
import { questionAlertIsSureAsync } from "../../services/alert/questionAlert";
import { deleteMemberAsync } from "../../request/member/memberRequests";
import { successAlert } from "../../services/alert/successHandler";

export default function MemberTable() {
  const membersState: MembersState = useAppSelector((state) => state.members);
  const memberUpdateState: MemberUpdateState = useAppSelector(
    (state) => state.memberUpdate
  );
  const dispatch = useAppDispatch();
  const [showModal, setShowModal] = useState<boolean>(false);
  const tableRef = useRef<CompleteTableRef>({} as CompleteTableRef);

  const columns = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "ID Card Number",
        accessor: "idCardNumber",
      },
      {
        Header: "Firstname",
        accessor: "firstname",
      },
      {
        Header: "Lastname",
        accessor: "lastname",
      },
      {
        Header: "Email",
        accessor: "email",
      },
    ],
    []
  );

  const data: MemberShort[] = useMemo(
    () => membersState.members,
    [membersState.members]
  );

  async function loadDataAsync() {
    if (tableRef.current === undefined) {
      return;
    }
    await dispatch(getMembersAsyncThunk(tableRef.current.requestQueryParams));
  }

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
      await loadDataAsync();
    } catch (error) {
      handleMemberDeleteError();
    }
  }

  async function closeModalAsync() {
    setShowModal(false);
    await loadDataAsync();
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

  return (
    <>
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
      <CompleteTable
        ref={tableRef}
        columns={columns}
        data={data}
        loadDataAsync={loadDataAsync}
        totalPages={membersState.totalPages}
        rowActions={rowActions}
      />
    </>
  );
}
