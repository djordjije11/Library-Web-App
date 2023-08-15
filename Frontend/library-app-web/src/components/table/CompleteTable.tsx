import { useState, useEffect, useMemo } from "react";
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
import { SortByColumn } from "../../models/request/SortBy";
import RequestQueryParams from "../../models/request/RequestQueryParams";

export interface CompleteTableProps {
  columns: Column[];
  data: any[];
  loadDataAsync: (requestQueryParams: RequestQueryParams) => Promise<void>;
  totalPages: number;
  rowActions: (row: Row<{}>) => JSX.Element;
}

export default function CompleteTable(props: CompleteTableProps) {
  const { columns, data, loadDataAsync, totalPages, rowActions } = props;
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [pageSize, setPageSize] = useState<number>(5);
  const [search, setSearch] = useState<string>("");

  const tableInstance = useTable(
    {
      columns,
      data,
      manualSortBy: true,
    } as any,
    useSortBy
  );

  const sortBy: SortByColumn[] = (tableInstance.state as any).sortBy;

  const memoizedRequestQueryParams: RequestQueryParams = useMemo(() => {
    return {
      pageable: { pageNumber, pageSize },
      search,
      sortBy,
    };
  }, [pageNumber, pageSize, search, sortBy]);

  const requestQueryParams = {
    pageable: { pageNumber, pageSize },
    search,
    sortBy,
  };

  useEffect(() => {
    loadDataAsync(memoizedRequestQueryParams);
  }, [pageNumber, sortBy]);

  const searchDebounce = useAsyncDebounce(() => {
    if (pageNumber !== 1) {
      setPageNumber(1);
      return;
    }
    loadDataAsync(memoizedRequestQueryParams);
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
      <ReactTable tableInstance={tableInstance} rowActions={rowActions} />
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
