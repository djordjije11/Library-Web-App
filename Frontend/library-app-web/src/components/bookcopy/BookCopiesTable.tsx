import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Cell, Column } from "react-table";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { bookCopiesActions } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesSlice";
import CompleteTable from "../shared/table/CompleteTable";
import BookCopiesState from "../../store/bookcopy/table/by-book-all-buildings/BookCopiesState";
import { getBookCopiesInAllBuildingsAsyncThunk } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesThunks";
import { Chip } from "@material-tailwind/react";
import { BookCopyStatus } from "../../models/bookcopy/BookCopyStatus";
import { colors } from "@material-tailwind/react/types/generic";

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

  function renderStatusCell(cell: Cell<{}, any>): JSX.Element {
    var color: colors, iconBackgroundColor: string;
    switch (cell.value as BookCopyStatus) {
      case BookCopyStatus.AVAILABLE:
        color = "green";
        iconBackgroundColor = "bg-green-900";
        break;
      case BookCopyStatus.LENT:
        color = "red";
        iconBackgroundColor = "bg-red-900";
        break;
      case BookCopyStatus.LOST:
        color = "gray";
        iconBackgroundColor = "bg-gray-900";
        break;
      default:
        throw Error(
          `renderStatusCell - cell.value=${cell.value} NOT SPECIFIED`
        );
    }
    return (
      <Chip
        variant="ghost"
        color={color}
        className="w-fit"
        size="sm"
        value={cell.value}
        icon={
          <span
            className={`mx-auto mt-1 block h-2 w-2 rounded-full ${iconBackgroundColor} content-['']`}
          />
        }
      />
    );
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={bookCopiesState.copiesLoading}
      setRequestQueryParamsAction={bookCopiesActions.setRequestQueryParams}
      totalPages={bookCopiesState.totalPages}
      rowActions={rowActions}
      onSelectedRow={onSelectedRow}
      renderHeaderChildren={renderHeaderChildren}
      columnSortOptions={[true, true, true, false]}
      cellWrappers={[{ column: 2, renderCell: renderStatusCell }]}
      filterDependencies={[bookCopiesState.status]}
    />
  );
}
