import { Button, IconButton, Tooltip } from "@material-tailwind/react";
import MemberShort from "../../models/member/MemberShort";
import { PencilSquareIcon } from "@heroicons/react/24/outline";

export interface SelectMemberFieldProps {
  member?: MemberShort;
  onSelectMemberClick: () => void;
}

export default function SelectMemberField(props: SelectMemberFieldProps) {
  const { member, onSelectMemberClick } = props;

  if (member === undefined) {
    return (
      <Button onClick={onSelectMemberClick} color="blue-gray">
        Select a member
      </Button>
    );
  }

  return (
    <div
      style={{
        display: "grid",
        gridAutoFlow: "column",
        gridTemplateColumns: "90% auto",
        gap: "10px",
      }}
    >
      <div className="w-full p-3 font-medium border rounded-md border-slate-300 bg-white">
        <span>
          {member.firstname} {member.lastname}
        </span>
      </div>
      <div className="flex items-center">
        <Tooltip content="Select a member">
          <IconButton
            color="white"
            style={{
              border: "solid 1px gray",
              height: "100%",
            }}
            onClick={onSelectMemberClick}
          >
            <PencilSquareIcon color="blue-gray" width={"20px"} />
          </IconButton>
        </Tooltip>
      </div>
    </div>
  );
}
