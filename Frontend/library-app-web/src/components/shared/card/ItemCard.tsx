import { XCircleIcon } from "@heroicons/react/24/outline";
import { Card, CardBody } from "@material-tailwind/react";
import { color } from "@material-tailwind/react/types/components/alert";
import { MouseEventHandler } from "react";

export interface ItemCardProps {
  titleRendered: JSX.Element;
  bodyRendered: JSX.Element;
  onClose: MouseEventHandler<HTMLButtonElement>;
  color?: color;
}

export default function ItemCard(props: ItemCardProps) {
  const { titleRendered, bodyRendered, onClose, color } = props;

  function CardHeader() {
    return (
      <div
        className="font-bold"
        style={{
          display: "grid",
          gridAutoFlow: "column",
          gridTemplateColumns: "auto 8%",
          gap: "0.5rem",
        }}
      >
        <div>{titleRendered}</div>
        <div
          className="max-h-full"
          style={{
            display: "grid",
            gridAutoFlow: "row",
            gridTemplateRows: "auto 50%",
          }}
        >
          <button
            className="w-4 h-4 max-w-fit max-h-fit"
            type="button"
            onClick={onClose}
          >
            <XCircleIcon width={"100%"} />
          </button>
          <div />
        </div>
      </div>
    );
  }

  return (
    <Card color={color} className="w-fit text-xs border border-gray-200">
      <CardBody className="p-3">
        <CardHeader />
        <div>{bodyRendered}</div>
      </CardBody>
    </Card>
  );
}
