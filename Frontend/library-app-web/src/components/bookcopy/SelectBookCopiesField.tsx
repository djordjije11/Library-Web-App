import { Button } from "@material-tailwind/react";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import BookCopyCard from "./BookCopyCard";
import CardsContainer from "../shared/CardsContainer";

export interface SelectBookCopiesFieldProps {
  bookCopies: BookCopyDisplay[];
  onSelectBookCopyClick: () => void;
  handleRemoveBookCopy: (bookCopy: BookCopyDisplay) => void;
}

export default function SelectBookCopiesField(
  props: SelectBookCopiesFieldProps
) {
  const { bookCopies, handleRemoveBookCopy, onSelectBookCopyClick } = props;

  function SelectBookCopyButton(): JSX.Element {
    return (
      <Button onClick={onSelectBookCopyClick} color="blue-gray">
        Add a book
      </Button>
    );
  }

  if (bookCopies.length === 0) {
    return <SelectBookCopyButton />;
  }

  return (
    <>
      <SelectBookCopyButton />
      <CardsContainer>
        <>
          {bookCopies.map((bookCopy) => (
            <BookCopyCard
              key={bookCopy.id}
              bookCopy={bookCopy}
              onClose={() => handleRemoveBookCopy(bookCopy)}
            />
          ))}
        </>
      </CardsContainer>
    </>
  );
}
