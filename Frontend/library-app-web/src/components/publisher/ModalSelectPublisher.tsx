import { Box, Modal } from "@mui/material";
import PublisherShort from "../../models/publisher/PublisherShort";
import { Card } from "@material-tailwind/react";
import PublisherTable from "./PublisherTable";

export interface ModalSelectPublisherProps {
  show: boolean;
  close: () => void;
  handleSelectedPublisher: (publisher: PublisherShort) => void;
}

export default function ModalSelectPublisher(
  props: ModalSelectPublisherProps
): JSX.Element {
  const { show, close, handleSelectedPublisher } = props;
  return (
    <Modal
      open={show}
      onClose={close}
      className="flex justify-center items-center"
    >
      <Box
        sx={{
          border: "none",
          width: "50%",
          minWidth: "min-content",
          height: "56%",
        }}
      >
        <Card className="w-full h-full">
          <PublisherTable
            onSelectedRow={(event, row) =>
              handleSelectedPublisher(row.original as PublisherShort)
            }
          />
        </Card>
      </Box>
    </Modal>
  );
}
