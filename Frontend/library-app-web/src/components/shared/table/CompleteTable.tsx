import { useState, useEffect, MouseEvent } from "react";
import ReactTable from "./ReactTable";
import TablePagination from "./TablePagination";
import TableSearchInput from "./TableSearchInput";
import {
  Column,
  Row,
  useAsyncDebounce,
  useSortBy,
  useTable,
} from "react-table";
import { SortByColumn } from "../../../models/request/SortBy";
import RequestQueryParams from "../../../models/request/RequestQueryParams";
import { ActionCreatorWithPayload } from "@reduxjs/toolkit";
import { useAppDispatch } from "../../../store/config/hooks";

export interface CompleteTableRef {
  requestQueryParams: RequestQueryParams;
}

export interface CompleteTableProps {
  columns: Column[];
  data: any[];
  loadDataAsync: () => Promise<void>;
  setRequestQueryParamsAction: ActionCreatorWithPayload<RequestQueryParams>;
  totalPages: number;
  rowActions?: (row: Row<{}>) => JSX.Element;
  onSelectedRow?: (
    event: MouseEvent<HTMLTableRowElement>,
    row: Row<{}>
  ) => void;
  columnSortOptions: boolean[];
}

export default function CompleteTable(props: CompleteTableProps) {
  const {
    columns,
    data,
    loadDataAsync,
    setRequestQueryParamsAction,
    totalPages,
    rowActions,
    onSelectedRow,
    columnSortOptions,
  } = props;
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [pageSize, setPageSize] = useState<number>(5);
  const [search, setSearch] = useState<string>("");
  const dispatch = useAppDispatch();

  const tableInstance = useTable(
    {
      columns,
      data,
      manualSortBy: true,
    } as any,
    useSortBy
  );

  const sortBy: SortByColumn[] = (tableInstance.state as any).sortBy;

  useEffect(() => {
    dispatch(
      setRequestQueryParamsAction({
        pageable: { pageNumber, pageSize },
        search,
        sortBy,
      })
    );
  }, [pageNumber, pageSize, search, sortBy]);

  useEffect(() => {
    loadDataAsync();
  }, [pageNumber, sortBy]);

  const searchDebounce = useAsyncDebounce(() => {
    if (pageNumber !== 1) {
      setPageNumber(1);
      return;
    }
    loadDataAsync();
  }, 300);

  const onSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
    searchDebounce();
  };

  return (
    <div className="flex flex-col items-stretch">
      <div className="mt-2">
        <TableSearchInput search={search} onSearchChange={onSearchChange} />
      </div>
      <ReactTable
        tableInstance={tableInstance}
        rowActions={rowActions}
        onSelectedRow={onSelectedRow}
        columnSortOptions={columnSortOptions}
      />
      <div className="my-2">
        <TablePagination
          currentPage={pageNumber}
          setCurrentPage={setPageNumber}
          pagesCount={totalPages}
        />
      </div>
    </div>
  );
}
