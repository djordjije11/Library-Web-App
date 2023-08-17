import { Box, Modal } from "@mui/material";
import { LendingIncludingBookCopy } from "../../models/lending/LendingIncludingBookCopy";
import { Card } from "@material-tailwind/react";
import LendingUnreturnedTable from "./LendingUnreturnedTable";

export interface ModalSelectLendingReturnProps {
  show: boolean;
  close: () => void;
  handleSelectedLending: (lending: LendingIncludingBookCopy) => void;
}

export default function ModalSelectLendingReturn(
  props: ModalSelectLendingReturnProps
) {
  const { show, close, handleSelectedLending } = props;
  return (
    <Modal
      open={show}
      onClose={close}
      className="flex justify-center items-center"
    >
      <Box sx={{ width: "60%", border: "none" }}>
        <Card>
          <LendingUnreturnedTable
            onSelectedRow={(event, row) =>
              handleSelectedLending(row.original as LendingIncludingBookCopy)
            }
          />
        </Card>
      </Box>
    </Modal>
  );
}
