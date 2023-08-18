import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import { LendingIncludingBookCopy } from "../../models/lending/LendingIncludingBookCopy";
import { getUnreturnedLendingsByMemberAsyncThunk } from "../../store/lending/table/unreturned/lendingsUnreturnedThunks";
import CompleteTable from "../shared/table/CompleteTable";
import { LendingsUnreturnedState } from "../../store/lending/table/unreturned/LendingsUnreturnedState";
import { lendingsUnreturnedActions } from "../../store/lending/table/unreturned/lendingsUnreturnedSlice";

export default function LendingUnreturnedTable(props: ModelTableProps) {
  const { onSelectedRow, rowActions } = props;
  const lendingsUnreturnedState: LendingsUnreturnedState = useAppSelector(
    (state) => state.lendingsUnreturned
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
    () => lendingsUnreturnedState.lendingsReturn.lendings,
    [lendingsUnreturnedState.lendingsReturn.lendings]
  );

  async function loadDataAsync() {
    await dispatch(getUnreturnedLendingsByMemberAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={lendingsUnreturnedState.loading}
      setRequestQueryParamsAction={
        lendingsUnreturnedActions.setRequestQueryParams
      }
      totalPages={lendingsUnreturnedState.totalPages}
      onSelectedRow={onSelectedRow}
      rowActions={rowActions}
    />
  );
}
