import { Card } from "@material-tailwind/react";
import { Box, Modal } from "@mui/material";
import MemberTable from "./MemberTable";
import MemberShort from "../../models/member/MemberShort";

export interface ModalSelectMemberProps {
  show: boolean;
  close: () => void;
  handleSelectedMember: (member: MemberShort) => void;
}

export default function ModalSelectMember(
  props: ModalSelectMemberProps
): JSX.Element {
  const { show, close, handleSelectedMember } = props;
  return (
    <Modal
      open={show}
      onClose={close}
      className="flex justify-center items-center"
    >
      <Box
        sx={{
          width: "50%",
          border: "none",
        }}
      >
        <Card>
          <MemberTable
            onSelectedRow={(event, row) =>
              handleSelectedMember(row.original as MemberShort)
            }
          />
        </Card>
      </Box>
    </Modal>
  );
}
