import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { bookCopiesActions } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesSlice";
import CompleteTable from "../shared/table/CompleteTable";
import BookCopiesState from "../../store/bookcopy/table/by-book-all-buildings/BookCopiesState";
import { getBookCopiesInAllBuildingsAsyncThunk } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesThunks";

export default function BookCopiesTable(props: ModelTableProps) {
  const { rowActions, onSelectedRow, renderHeaderChildren } = props;
  const bookCopiesState: BookCopiesState = useAppSelector(
    (state) => state.bookCopies
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
        Header: "Status",
        accessor: "status",
      },
      {
        Header: "Building",
        accessor: "building",
      },
    ],
    []
  );

  const data: BookCopyDisplay[] = useMemo(
    () => bookCopiesState.bookCopies,
    [bookCopiesState.bookCopies]
  );

  async function loadDataAsync() {
    await dispatch(
      getBookCopiesInAllBuildingsAsyncThunk(bookCopiesState.book.id)
    );
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={bookCopiesState.loading}
      setRequestQueryParamsAction={bookCopiesActions.setRequestQueryParams}
      totalPages={bookCopiesState.totalPages}
      rowActions={rowActions}
      onSelectedRow={onSelectedRow}
      renderHeaderChildren={renderHeaderChildren}
      columnSortOptions={[true, true, true, false]}
      dependencies={[bookCopiesState.status]}
    />
  );
}
