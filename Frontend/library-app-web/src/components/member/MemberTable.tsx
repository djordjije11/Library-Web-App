import { useMemo } from "react";
import { Column } from "react-table";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MembersState from "../../store/member/table/MembersState";
import { getMembersAsyncThunk } from "../../store/member/table/membersThunks";
import CompleteTable from "../shared/table/CompleteTable";
import MemberShort from "../../models/member/MemberShort";
import { membersActions } from "../../store/member/table/membersSlice";
import ModelTableProps from "../shared/table/ModelTableProps";

export default function MemberTable(props: ModelTableProps) {
  const { rowActions, onSelectedRow, renderHeaderChildren } = props;
  const membersState: MembersState = useAppSelector((state) => state.members);
  const dispatch = useAppDispatch();

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
    await dispatch(getMembersAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={membersState.loading}
      setRequestQueryParamsAction={membersActions.setRequestQueryParams}
      totalPages={membersState.totalPages}
      rowActions={rowActions}
      onSelectedRow={onSelectedRow}
      renderHeaderChildren={renderHeaderChildren}
    />
  );
}
