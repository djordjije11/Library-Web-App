import { useMemo } from "react";
import MemberShort from "../../models/member/MemberShort";
import FormTableSelect from "../shared/form/FormTableSelect";

export interface MemberSelectFieldProps {
  member: MemberShort;
  onSelectClick: () => void;
}

export default function MemberSelectField(props: MemberSelectFieldProps) {
  const { member, onSelectClick } = props;

  function renderSelectedItemText(): string {
    if (member === undefined) {
      return "";
    }
    return `${member.firstname} ${member.lastname}`;
  }

  const selectedItemText = useMemo(() => renderSelectedItemText(), [member]);

  return (
    <FormTableSelect
      isSelected={member !== undefined}
      onSelectClick={onSelectClick}
      selectButtonText="Select a member"
      selectedItemText={selectedItemText}
    />
  );
}
