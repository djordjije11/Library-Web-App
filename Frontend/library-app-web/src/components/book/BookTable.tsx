import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import { BookShort } from "../../models/book/BookShort";
import CompleteTable from "../shared/table/CompleteTable";
import BooksState from "../../store/book/table/BooksState";
import { getBooksAsyncThunk } from "../../store/book/table/booksThunks";
import { booksActions } from "../../store/book/table/booksSlice";

export default function BookTable(props: ModelTableProps) {
  const { rowActions, onSelectedRow, renderHeaderChildren } = props;
  const booksState: BooksState = useAppSelector((state) => state.books);
  const dispatch = useAppDispatch();

  const columns = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "Title",
        accessor: "title",
      },
      {
        Header: "Authors",
        accessor: "authors",
      },
      {
        Header: "Publisher",
        accessor: "publisher.name",
      },
      {
        Header: "Pages",
        accessor: "pagesNumber",
      },
    ],
    []
  );

  const data: BookShort[] = useMemo(() => booksState.books, [booksState.books]);

  async function loadDataAsync() {
    await dispatch(getBooksAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={booksState.loading}
      setRequestQueryParamsAction={booksActions.setRequestQueryParams}
      totalPages={booksState.totalPages}
      totalItemsCount={booksState.totalItemsCount}
      rowActions={rowActions}
      onSelectedRow={onSelectedRow}
      columnSortOptions={[true, true, false, true, true]}
      renderHeaderChildren={renderHeaderChildren}
    />
  );
}
