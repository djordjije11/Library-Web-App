import { Card, CardBody } from "@material-tailwind/react";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { XCircleIcon } from "@heroicons/react/24/outline";
import { color } from "@material-tailwind/react/types/components/alert";
import { MouseEventHandler } from "react";

export interface BookCopyCardProps {
  bookCopy: BookCopyDisplay;
  onClose: MouseEventHandler<HTMLButtonElement>;
  color?: color;
}

export default function BookCopyCard(props: BookCopyCardProps) {
  const { bookCopy, onClose, color } = props;

  function CardHeader() {
    return (
      <div
        className="font-bold"
        style={{
          display: "grid",
          gridAutoFlow: "column",
          gridTemplateColumns: "auto 8%",
        }}
      >
        <div
          style={{
            display: "grid",
            gridAutoFlow: "row",
            gridTemplateRows: "50% 50%",
            whiteSpace: "nowrap",
          }}
        >
          <span>
            {bookCopy.book.title} - {bookCopy.book.authors}
          </span>
          <span>published by {bookCopy.book.publisher.name}</span>
        </div>
        <div
          className="ml-2"
          style={{
            display: "grid",
            gridAutoFlow: "row",
            gridTemplateRows: "50% 50%",
          }}
        >
          <button className="w-fit h-fit" type="button" onClick={onClose}>
            <XCircleIcon width={"20px"} />
          </button>
          <div></div>
        </div>
      </div>
    );
  }

  return (
    <Card color={color} className="w-fit text-xs border border-gray-200">
      <CardBody className="p-3">
        <CardHeader />
        <div>
          <span>ISBN: {bookCopy.isbn}</span>
        </div>
      </CardBody>
    </Card>
  );
}
