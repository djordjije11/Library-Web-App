import { useState, useEffect, MouseEvent } from "react";
import ReactTable, { CellWrapperOption } from "./ReactTable";
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
import { Option, Select, Typography } from "@material-tailwind/react";
import { TablePageSizeSelect } from "./TablePageSizeSelect";

export interface CompleteTableProps {
  columns: Column[];
  data: any[];
  loadDataAsync: () => Promise<void>;
  setRequestQueryParamsAction: ActionCreatorWithPayload<RequestQueryParams>;
  totalPages: number;
  loading: boolean;
  rowActions?: (row: Row<{}>) => JSX.Element;
  onSelectedRow?: (
    event: MouseEvent<HTMLTableRowElement>,
    row: Row<{}>
  ) => void;
  columnSortOptions?: boolean[];
  cellWrappers?: CellWrapperOption[];
  renderHeaderChildren?: (searchInputField: JSX.Element) => JSX.Element;
  filterDependencies?: any[];
}

export default function CompleteTable(props: CompleteTableProps) {
  const {
    columns,
    data,
    loadDataAsync,
    setRequestQueryParamsAction,
    totalPages,
    loading,
    rowActions,
    onSelectedRow,
    columnSortOptions,
    cellWrappers,
    renderHeaderChildren,
  } = props;
  const filterDependencies = props.filterDependencies || [];
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

  useEffect(() => {
    onFilterChangeLoadAsync();
  }, [pageSize, ...filterDependencies]);

  useEffect(() => {
    if (totalPages < pageNumber && totalPages !== 0) {
      setPageNumber(totalPages);
    }
  }, [totalPages]);

  async function onFilterChangeLoadAsync() {
    if (pageNumber !== 1) {
      setPageNumber(1);
      return;
    }
    await loadDataAsync();
  }

  const searchDebounce = useAsyncDebounce(onFilterChangeLoadAsync, 300);

  const onSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
    searchDebounce();
  };

  return (
    <div
      className="overflow-x-auto w-full h-full min-h-fit"
      style={{
        display: "grid",
        gridAutoFlow: "row",
        gridTemplateRows: "85% 15%",
      }}
    >
      <div className="w-full h-full">
        <div className="w-full h-full flex flex-col items-stretch">
          {renderHeaderChildren !== undefined ? (
            renderHeaderChildren(
              <TableSearchInput
                search={search}
                onSearchChange={onSearchChange}
              />
            )
          ) : (
            <div className="mt-2 flex justify-between">
              <TableSearchInput
                search={search}
                onSearchChange={onSearchChange}
              />
            </div>
          )}
          {loading ? (
            <div className="h-full w-full flex justify-center items-center">
              <></>
            </div>
          ) : (
            <div className="w-full h-full overflow-y-auto">
              <ReactTable
                tableInstance={tableInstance}
                rowActions={rowActions}
                onSelectedRow={onSelectedRow}
                columnSortOptions={columnSortOptions}
                cellWrappers={cellWrappers}
              />
            </div>
          )}
        </div>
      </div>
      {loading ? (
        <></>
      ) : (
        <div className="my-2 flex flex-col">
          <div className="flex justify-start w-full">
            <div className="flex gap-2 mx-12">
              <Typography variant="small">
                <span>Page size:</span>
              </Typography>
              <TablePageSizeSelect
                pageSize={pageSize}
                setPageSize={setPageSize}
              />
            </div>
          </div>
          <TablePagination
            currentPage={pageNumber}
            setCurrentPage={setPageNumber}
            pagesCount={totalPages}
          />
        </div>
      )}
    </div>
  );
}
