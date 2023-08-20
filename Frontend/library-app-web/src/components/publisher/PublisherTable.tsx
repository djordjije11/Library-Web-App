import { Column } from "react-table";
import CompleteTable from "../shared/table/CompleteTable";
import { useMemo } from "react";
import PublisherShort from "../../models/publisher/PublisherShort";
import PublishersState from "../../store/publisher/table/PublishersState";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import { getPublishersAsyncThunk } from "../../store/publisher/table/publishersThunks";
import { publishersActions } from "../../store/publisher/table/publishersSlice";
import ModelTableProps from "../shared/table/ModelTableProps";

export default function PublisherTable(props: ModelTableProps) {
  const { onSelectedRow, rowActions, renderHeaderChildren } = props;
  const publishersState: PublishersState = useAppSelector(
    (state) => state.publishers
  );
  const dispatch = useAppDispatch();

  const columns: Column[] = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "Name",
        accessor: "name",
      },
    ],
    []
  );

  const data: PublisherShort[] = useMemo(
    () => publishersState.publishers,
    [publishersState.publishers]
  );

  async function loadDataAsync() {
    await dispatch(getPublishersAsyncThunk());
  }

  return (
    <CompleteTable
      columns={columns}
      data={data}
      loadDataAsync={loadDataAsync}
      totalPages={publishersState.totalPages}
      loading={publishersState.loading}
      setRequestQueryParamsAction={publishersActions.setRequestQueryParams}
      onSelectedRow={onSelectedRow}
      renderHeaderChildren={renderHeaderChildren}
      rowActions={rowActions}
    />
  );
}
