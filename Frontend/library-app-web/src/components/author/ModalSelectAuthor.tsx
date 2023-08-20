import { Box, Modal } from "@mui/material";
import AuthorShort from "../../models/author/AuthorShort";
import { Card } from "@material-tailwind/react";
import AuthorTable from "./AuthorTable";

export interface ModalSelectAuthorProps {
  show: boolean;
  close: () => void;
  handleSelectedAuthor: (author: AuthorShort) => void;
}

export default function ModalSelectAuthor(props: ModalSelectAuthorProps) {
  const { show, close, handleSelectedAuthor } = props;
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
          height: "54%",
        }}
      >
        <Card className="h-full w-full">
          <AuthorTable
            onSelectedRow={(event, row) =>
              handleSelectedAuthor(row.original as AuthorShort)
            }
          />
        </Card>
      </Box>
    </Modal>
  );
}
