import { Card, CardBody } from "@material-tailwind/react";
import FormField from "../shared/form/FormField";
import FormInput from "../shared/form/FormInput";
import { useState } from "react";
import { Box, Modal } from "@mui/material";
import LendingsAddState from "../../store/lending/add/LendingsAddState";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MemberTable from "../member/MemberTable";
import MemberShort from "../../models/member/MemberShort";
import { lendingsAddActions } from "../../store/lending/add/lendingsAddSlice";

export default function LendingAddForm() {
  const lendingsAddState: LendingsAddState = useAppSelector(
    (state) => state.lendingsAdd
  );
  const dispatch = useAppDispatch();
  const [memberInput, setMemberInput] = useState<string>("");
  const [showMemberModal, setShowMemberModal] = useState<boolean>(false);

  function handleSelectedMember(member: MemberShort) {
    dispatch(lendingsAddActions.setMemberId(member.id));
    setMemberInput(`${member.firstname} ${member.lastname}`);
    setShowMemberModal(false);
  }

  function ModalSelectMember(): JSX.Element {
    return (
      <Modal
        open={showMemberModal}
        onClose={() => setShowMemberModal(false)}
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

  return (
    <>
      <ModalSelectMember />
      <Card className="my-4 w-full bg-blue-gray-50">
        <form>
          <CardBody>
            <FormField name="member" label="Member">
              <div
                style={
                  {
                    // display: "grid",
                    // gridAutoFlow: "column",
                    // gridTemplateColumns: "90% auto",
                    // gap: "10px",
                  }
                }
              >
                <FormInput
                  name="member"
                  type="text"
                  placeholder="Select a member"
                  value={memberInput}
                  onClick={() => setShowMemberModal(true)}
                  classes="cursor-pointer hover:bg-gray-50"
                />
                {/* <div className="flex items-center">
                    <IconButton
                      color="white"
                      style={{
                        border: "solid 1px gray",
                        height: "100%",
                      }}
                    >
                      <PencilSquareIcon color="blue-gray" width={"20px"} />
                    </IconButton>
                  </div> */}
              </div>
            </FormField>
          </CardBody>
        </form>
      </Card>
    </>
  );
}
