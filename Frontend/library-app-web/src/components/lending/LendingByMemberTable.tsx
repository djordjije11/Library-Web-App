import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import { LendingsByMemberState } from "../../store/lending/table/by-member/all/LendingsByMemberState";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import { LendingIncludingBookCopy } from "../../models/lending/LendingIncludingBookCopy";
import CompleteTable from "../shared/table/CompleteTable";
import { getLendingsByMemberAsyncThunk } from "../../store/lending/table/by-member/all/lendingsByMemberThunks";
import { lendingsByMemberActions } from "../../store/lending/table/by-member/all/lendingsByMemberSlice";

export default function LendingByMemberTable(props: ModelTableProps) {
  const { onSelectedRow, rowActions } = props;
  const lendingsByMemberState: LendingsByMemberState = useAppSelector(
    (state) => state.lendingsByMember
  );
  const dispatch = useAppDispatch();

  const columns = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "Lending date",
        accessor: "lendingDate",
      },
      {
        Header: "Return date",
        accessor: "returnDate",
      },
      {
        Header: "ISBN",
        accessor: "bookCopy.isbn",
      },
      {
        Header: "Title",
        accessor: "bookCopy.book.title",
      },
    ],
    []
  );

  const data: LendingIncludingBookCopy[] = useMemo(
    () => lendingsByMemberState.lendingsByMember.lendings,
    [lendingsByMemberState.lendingsByMember.lendings]
  );

  async function loadDataAsync() {
    await dispatch(getLendingsByMemberAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={lendingsByMemberState.loading}
      setRequestQueryParamsAction={
        lendingsByMemberActions.setRequestQueryParams
      }
      totalPages={lendingsByMemberState.totalPages}
      onSelectedRow={onSelectedRow}
      rowActions={rowActions}
    />
  );
}
