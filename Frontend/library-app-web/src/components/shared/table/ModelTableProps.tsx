import { MouseEvent } from "react";
import { Row } from "react-table";

export default interface ModelTableProps {
  rowActions?: (row: Row<{}>) => JSX.Element;
  onSelectedRow?: (
    event: MouseEvent<HTMLTableRowElement>,
    row: Row<{}>
  ) => void;
}
