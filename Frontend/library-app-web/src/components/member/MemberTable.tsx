import { useMemo, useState, useEffect } from "react";
import { Column, Row } from "react-table";
import Member from "../../models/member/Member";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MembersState from "../../store/member/MembersState";
import { getMembersAsyncThunk } from "../../store/member/membersThunks";
import RequestQueryParams from "../../models/request/RequestQueryParams";
import CompleteTable from "../table/CompleteTable";
import { IconButton, Tooltip } from "@material-tailwind/react";
import { PencilIcon } from "@heroicons/react/24/outline";
import { Modal, Box } from "@mui/material";
import MemberUpdateContainer from "./MemberUpdateContainer";

export default function MemberTable() {
  const membersState: MembersState = useAppSelector((state) => state.members);
  const dispatch = useAppDispatch();
  const [showModal, setShowModal] = useState<boolean>(false);
  const [memberForUpdate, setMemberForUpdate] = useState<Member>({} as Member);

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

  const data: Member[] = useMemo(
    () => membersState.members,
    [membersState.members]
  );

  async function loadDataAsync(requestQueryParams: RequestQueryParams) {
    await dispatch(getMembersAsyncThunk(requestQueryParams));
  }

  function rowActions(row: Row<{}>): JSX.Element {
    const member: Member = row.original as Member;
    return (
      <>
        <Tooltip content="Edit">
          <IconButton
            variant="text"
            onClick={() => {
              setMemberForUpdate(member);
              setShowModal(true);
            }}
          >
            <PencilIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
      </>
    );
  }

  return (
    <>
      <Modal
        open={showModal}
        onClose={() => setShowModal(false)}
        className="flex justify-center"
      >
        <Box sx={{ width: "50%" }}>
          <MemberUpdateContainer
            member={memberForUpdate}
            setMember={setMemberForUpdate}
            close={() => setShowModal(false)}
          />
        </Box>
      </Modal>
      <CompleteTable
        columns={columns}
        data={data}
        loadDataAsync={loadDataAsync}
        totalPages={membersState.totalPages}
        rowActions={rowActions}
      />
    </>
  );
}
