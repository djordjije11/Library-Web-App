import { useMemo } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import ModelTableProps from "../shared/table/ModelTableProps";
import { Column } from "react-table";
import CompleteTable from "../shared/table/CompleteTable";
import AuthorShort from "../../models/author/AuthorShort";
import { authorsActions } from "../../store/author/table/authorsSlice";
import { getAuthorsAsyncThunk } from "../../store/author/table/authorsThunks";
import AuthorsState from "../../store/author/table/AuthorsState";

export default function AuthorTable(props: ModelTableProps) {
  const { rowActions, onSelectedRow, renderHeaderChildren } = props;
  const authorsState: AuthorsState = useAppSelector((state) => state.authors);
  const dispatch = useAppDispatch();

  const columns = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "Firstname",
        accessor: "firstname",
      },
      {
        Header: "Lastname",
        accessor: "lastname",
      },
    ],
    []
  );

  const data: AuthorShort[] = useMemo(
    () => authorsState.authors,
    [authorsState.authors]
  );

  async function loadDataAsync() {
    await dispatch(getAuthorsAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      loading={authorsState.loading}
      setRequestQueryParamsAction={authorsActions.setRequestQueryParams}
      totalPages={authorsState.totalPages}
      totalItemsCount={authorsState.totalItemsCount}
      rowActions={rowActions}
      onSelectedRow={onSelectedRow}
      renderHeaderChildren={renderHeaderChildren}
    />
  );
}
