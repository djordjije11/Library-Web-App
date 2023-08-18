import {
  ChevronDownIcon,
  ChevronUpDownIcon,
  ChevronUpIcon,
} from "@heroicons/react/24/outline";
import { Typography } from "@material-tailwind/react";
import { MouseEvent } from "react";
import { Cell, Row, TableInstance } from "react-table";

export interface CellWrapperOption {
  column: number;
  renderCell: (cell: Cell<{}, any>) => JSX.Element;
}

export interface ReactTableProps {
  tableInstance: TableInstance;
  rowActions?: (row: Row<{}>) => JSX.Element;
  onSelectedRow?: (
    event: MouseEvent<HTMLTableRowElement>,
    row: Row<{}>
  ) => void;
  columnSortOptions?: boolean[];
  cellWrappers?: CellWrapperOption[];
}

function getTrueArray(length: number): boolean[] {
  const array: boolean[] = [];
  for (let i = 0; i < length; i++) {
    array.push(true);
  }
  return array;
}

export default function ReactTable(props: ReactTableProps) {
  const { tableInstance, rowActions, onSelectedRow, cellWrappers } = props;
  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
    tableInstance;
  const columnSortOptions =
    props.columnSortOptions || getTrueArray(headerGroups[0].headers.length);

  function renderTableCell(
    cell: Cell<{}, any>,
    columnIndex: number
  ): JSX.Element {
    const cellWrapper = cellWrappers?.find(
      (cellWrapper) => cellWrapper.column === columnIndex
    );
    if (cellWrapper !== undefined) {
      return cellWrapper.renderCell(cell);
    }
    return (
      <Typography variant="small" color="blue-gray" className="font-normal">
        {cell.render("Cell")}
      </Typography>
    );
  }

  function renderTableBodyContent(): JSX.Element {
    if (rows.length === 0) {
      return (
        <tr>
          <td colSpan={headerGroups[0].headers.length} align="center">
            <div className="mt-2 text-lg">
              There is not any records in the database.
            </div>
          </td>
        </tr>
      );
    }
    return (
      <>
        {rows.map((row, index) => {
          prepareRow(row);
          return (
            <tr
              {...row.getRowProps()}
              {...(onSelectedRow === undefined
                ? null
                : {
                    className: "cursor-pointer hover:bg-blue-gray-100",
                    onClick: (event: MouseEvent<HTMLTableRowElement>) =>
                      onSelectedRow(event, row),
                  })}
            >
              {row.cells.map((cell, index) => {
                return (
                  <td
                    {...cell.getCellProps()}
                    className={`p-4 ${
                      index === rows.length - 1
                        ? "border-b border-blue-gray-200"
                        : "border-b border-blue-gray-100"
                    }`}
                  >
                    {renderTableCell(cell, index)}
                  </td>
                );
              })}
              {rowActions === undefined ? (
                <></>
              ) : (
                <td
                  className={`p-4 ${
                    index === rows.length - 1
                      ? "border-b border-blue-gray-200"
                      : "border-b border-blue-gray-100"
                  }`}
                >
                  {rowActions(row)}
                </td>
              )}
            </tr>
          );
        })}
      </>
    );
  }

  return (
    <table
      {...getTableProps()}
      className="mt-2 w-full min-w-max table-auto text-left"
    >
      <thead>
        {headerGroups.map((headerGroup) => (
          <tr {...headerGroup.getHeaderGroupProps()}>
            {headerGroup.headers.map((column, index) => (
              <th
                {...(columnSortOptions[index]
                  ? column.getHeaderProps(
                      (column as any).getSortByToggleProps()
                    )
                  : column.getHeaderProps())}
                className="cursor-pointer border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors hover:bg-blue-gray-50"
              >
                <Typography
                  variant="small"
                  color="blue-gray"
                  className="flex items-center justify-between gap-2 font-normal leading-none opacity-70"
                >
                  {column.render("Header")}
                  {columnSortOptions[index] ? (
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
                  ) : (
                    <></>
                  )}
                </Typography>
              </th>
            ))}
            {rowActions === undefined ? (
              <></>
            ) : (
              <th className="border-y border-blue-gray-100 bg-blue-gray-50/50 p-4 transition-colors"></th>
            )}
          </tr>
        ))}
      </thead>
      <tbody {...getTableBodyProps()}>{renderTableBodyContent()}</tbody>
    </table>
  );
}
