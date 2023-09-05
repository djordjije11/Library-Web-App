import { Card } from "@material-tailwind/react";
import { Box, Modal } from "@mui/material";
import BooksCopiesTable from "./BooksCopiesTable";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";

export interface ModalSelectBookCopyProps {
  show: boolean;
  close: () => void;
  handleSelectedBookCopy: (bookCopy: BookCopyDisplay) => void;
}

export default function ModalSelectBookCopy(props: ModalSelectBookCopyProps) {
  const { show, close, handleSelectedBookCopy } = props;
  return (
    <Modal
      open={show}
      onClose={close}
      className="flex justify-center items-center"
    >
      <Box
        sx={{
          width: "80%",
          border: "none",
          minWidth: "min-content",
          height: "58%",
        }}
      >
        <Card className="h-full w-full">
          <BooksCopiesTable
            onSelectedRow={(event, row) =>
              handleSelectedBookCopy(row.original as BookCopyDisplay)
            }
          />
        </Card>
      </Box>
    </Modal>
  );
}
