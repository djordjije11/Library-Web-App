import { PencilSquareIcon } from "@heroicons/react/24/outline";
import { Button, IconButton, Tooltip } from "@material-tailwind/react";

export interface FormTableSelectProps {
  isSelected: boolean;
  onSelectClick: () => void;
  selectButtonText: string;
  selectedItemText: string;
}

export default function FormTableSelect(props: FormTableSelectProps) {
  const { isSelected, onSelectClick, selectButtonText, selectedItemText } =
    props;

  if (isSelected === false) {
    return (
      <Button onClick={onSelectClick} color="blue-gray">
        {selectButtonText}
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
        <span>{selectedItemText}</span>
      </div>
      <div className="flex items-center">
        <Tooltip content={selectButtonText}>
          <IconButton
            color="white"
            style={{
              border: "solid 1px gray",
              height: "100%",
            }}
            onClick={onSelectClick}
          >
            <PencilSquareIcon color="blue-gray" width={"20px"} />
          </IconButton>
        </Tooltip>
      </div>
    </div>
  );
}
