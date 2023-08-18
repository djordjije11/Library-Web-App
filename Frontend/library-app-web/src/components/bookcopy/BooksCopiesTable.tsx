import { useMemo } from "react";
import BooksCopiesState from "../../store/bookcopy/table/by-all-books-in-building/BooksCopiesState";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { getAllBooksCopiesAvailableInBuildingAsyncThunk } from "../../store/bookcopy/table/by-all-books-in-building/booksCopiesThunks";
import { booksCopiesActions } from "../../store/bookcopy/table/by-all-books-in-building/booksCopiesSlice";
import CompleteTable from "../shared/table/CompleteTable";

export default function BooksCopiesTable(props: ModelTableProps) {
  const { rowActions, onSelectedRow } = props;
  const booksCopiesState: BooksCopiesState = useAppSelector(
    (state) => state.booksCopies
  );
  const dispatch = useAppDispatch();

  const columns = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "ISBN",
        accessor: "isbn",
      },
      {
        Header: "Title",
        accessor: "book.title",
      },
      {
        Header: "Authors",
        accessor: "book.authors",
      },
      {
        Header: "Publisher",
        accessor: "book.publisher.name",
      },
    ],
    []
  );

  const data: BookCopyDisplay[] = useMemo(
    () => booksCopiesState.bookCopies,
    [booksCopiesState.bookCopies]
  );

  async function loadDataAsync() {
    await dispatch(getAllBooksCopiesAvailableInBuildingAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={booksCopiesState.loading}
      setRequestQueryParamsAction={booksCopiesActions.setRequestQueryParams}
      totalPages={booksCopiesState.totalPages}
      rowActions={rowActions}
      onSelectedRow={onSelectedRow}
      columnSortOptions={[true, true, true, false, true]}
    />
  );
}
