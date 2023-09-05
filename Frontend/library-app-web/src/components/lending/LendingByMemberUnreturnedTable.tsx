import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import { LendingIncludingBookCopy } from "../../models/lending/LendingIncludingBookCopy";
import { getUnreturnedLendingsByMemberAsyncThunk } from "../../store/lending/table/by-member/unreturned/lendingsByMemberUnreturnedThunks";
import CompleteTable from "../shared/table/CompleteTable";
import { LendingsByMemberUnreturnedState } from "../../store/lending/table/by-member/unreturned/LendingsByMemberUnreturnedState";
import { lendingsByMemberUnreturnedActions } from "../../store/lending/table/by-member/unreturned/lendingsByMemberUnreturnedSlice";

export default function LendingByMemberUnreturnedTable(props: ModelTableProps) {
  const { onSelectedRow, rowActions } = props;
  const lendingsByMemberUnreturnedState: LendingsByMemberUnreturnedState =
    useAppSelector((state) => state.lendingsByMemberUnreturned);
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
    () => lendingsByMemberUnreturnedState.lendingsByMember.lendings,
    [lendingsByMemberUnreturnedState.lendingsByMember.lendings]
  );

  async function loadDataAsync() {
    await dispatch(getUnreturnedLendingsByMemberAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={lendingsByMemberUnreturnedState.loading}
      setRequestQueryParamsAction={
        lendingsByMemberUnreturnedActions.setRequestQueryParams
      }
      totalPages={lendingsByMemberUnreturnedState.totalPages}
      onSelectedRow={onSelectedRow}
      rowActions={rowActions}
    />
  );
}
