import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { color } from "@material-tailwind/react/types/components/alert";
import { MouseEventHandler } from "react";
import ItemCard from "../shared/card/ItemCard";

export interface BookCopyCardProps {
  bookCopy: BookCopyDisplay;
  onClose: MouseEventHandler<HTMLButtonElement>;
  color?: color;
}

export default function BookCopyCard(props: BookCopyCardProps) {
  const { bookCopy, onClose, color } = props;

  return (
    <ItemCard
      color={color}
      onClose={onClose}
      titleRendered={
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
      }
      bodyRendered={<span>ISBN: {bookCopy.isbn}</span>}
    />
  );
}
