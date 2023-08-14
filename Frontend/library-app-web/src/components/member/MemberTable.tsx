import { useEffect, useMemo, useState } from "react";
import { Column, useSortBy, useTable } from "react-table";
import Member from "../../models/member/Member";
import { Typography } from "@material-tailwind/react";
import {
  ChevronDownIcon,
  ChevronUpDownIcon,
  ChevronUpIcon,
} from "@heroicons/react/24/solid";
import TablePagination from "../table/TablePagination";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MembersState from "../../store/member/MembersState";
import { getMembersAsyncThunk } from "../../store/member/membersThunks";

export default function MemberTable() {
  const membersState: MembersState = useAppSelector((state) => state.members);
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState<number>(1);

  async function loadDataAsync() {
    await dispatch(
      getMembersAsyncThunk({ pageNumber: currentPage, pageSize: 10 })
    );
  }

  useEffect(() => {
    loadDataAsync();
  }, []);

  useEffect(() => {
    loadDataAsync();
  }, [currentPage]);

  const columns = useMemo(
    (): Column[] => [
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: "ID Card Number",
        accessor: "idCardNumber",
      },
      {
        Header: "Firstname",
        accessor: "firstname",
      },
      {
        Header: "Lastname",
        accessor: "lastname",
      },
      {
        Header: "Email",
        accessor: "email",
      },
    ],
    []
  );

  const data: Member[] = useMemo(
    () => membersState.members,
    [membersState.members]
  );

  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
    useTable(
      {
        columns,
        data,
      },
      useSortBy
    );

  return (
    <div className="flex flex-col items-stretch">
      <table
        {...getTableProps()}
        className="mt-4 w-full min-w-max table-auto text-left"
      >
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column) => (
                <th
                  {...column.getHeaderProps(
                    (column as any).getSortByToggleProps()
                  )}
                  className="cursor-pointer border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors hover:bg-blue-gray-50"
                >
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="flex items-center justify-between gap-2 font-normal leading-none opacity-70"
                  >
                    {column.render("Header")}
                    <span>
                      {(column as any).isSorted ? (
                        (column as any).isSortedDesc ? (
                          <ChevronDownIcon
                            strokeWidth={2}
                            className="h-4 w-4"
                          />
                        ) : (
                          <ChevronUpIcon strokeWidth={2} className="h-4 w-4" />
                        )
                      ) : (
                        <ChevronUpDownIcon
                          strokeWidth={2}
                          className="h-4 w-4"
                        />
                      )}
                    </span>
                  </Typography>
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row, index) => {
            prepareRow(row);
            return (
              <tr {...row.getRowProps()}>
                {row.cells.map((cell) => {
                  return (
                    <td
                      {...cell.getCellProps()}
                      className={`p-4 ${
                        index === rows.length - 1
                          ? "border-b border-blue-gray-200"
                          : "border-b border-blue-gray-100"
                      }`}
                    >
                      {
                        <Typography
                          variant="small"
                          color="blue-gray"
                          className="font-normal"
                        >
                          {cell.render("Cell")}
                        </Typography>
                      }
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
      <TablePagination
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        pagesCount={membersState.totalPages}
      />
    </div>
  );
}
