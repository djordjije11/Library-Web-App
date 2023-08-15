import {
  ChevronDownIcon,
  ChevronUpDownIcon,
  ChevronUpIcon,
} from "@heroicons/react/24/outline";
import { Typography } from "@material-tailwind/react";
import { Row, TableInstance } from "react-table";

export interface ReactTableProps {
  tableInstance: TableInstance;
  rowActions: (row: Row<{}>) => JSX.Element;
}

export default function ReactTable(props: ReactTableProps) {
  const { tableInstance, rowActions } = props;
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    state,
  } = tableInstance;
  return (
    <table
      {...getTableProps()}
      className="mt-2 w-full min-w-max table-auto text-left"
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
                        <ChevronDownIcon strokeWidth={2} className="h-4 w-4" />
                      ) : (
                        <ChevronUpIcon strokeWidth={2} className="h-4 w-4" />
                      )
                    ) : (
                      <ChevronUpDownIcon strokeWidth={2} className="h-4 w-4" />
                    )}
                  </span>
                </Typography>
              </th>
            ))}
            <th className="border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors"></th>
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
              <td
                className={`p-4 ${
                  index === rows.length - 1
                    ? "border-b border-blue-gray-200"
                    : "border-b border-blue-gray-100"
                }`}
              >
                {rowActions(row)}
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
}
