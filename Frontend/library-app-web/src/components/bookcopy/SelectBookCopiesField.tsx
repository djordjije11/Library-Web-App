import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import BookCopyCard from "./BookCopyCard";
import FormTableSelectMultiple from "../shared/form/FormTableSelectMultiple";

export interface SelectBookCopiesFieldProps {
  bookCopies: BookCopyDisplay[];
  onSelectBookCopyClick: () => void;
  handleRemoveBookCopy: (bookCopy: BookCopyDisplay) => void;
}

export default function SelectBookCopiesField(
  props: SelectBookCopiesFieldProps
) {
  const { bookCopies, handleRemoveBookCopy, onSelectBookCopyClick } = props;

  return (
    <FormTableSelectMultiple
      isAnySelected={bookCopies.length !== 0}
      onSelectClick={onSelectBookCopyClick}
      selectButtonText="Add a book"
      selectedItemsRendered={
        <>
          {bookCopies.map((bookCopy) => (
            <BookCopyCard
              key={bookCopy.id}
              bookCopy={bookCopy}
              onClose={() => handleRemoveBookCopy(bookCopy)}
            />
          ))}
        </>
      }
    />
  );
}
