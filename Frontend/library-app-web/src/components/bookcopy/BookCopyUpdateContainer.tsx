import { XMarkIcon } from "@heroicons/react/24/outline";
import BookCopyForm from "./BookCopyForm";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { updateBookCopyAsync } from "../../request/bookcopy/bookCopyRequests";
import { Book } from "../../models/book/Book";

export interface BookCopyUpdateContainerProps {
  book: Book;
  bookCopy: BookCopyDisplay;
  close: () => void;
}

export default function BookCopyUpdateContainer(
  props: BookCopyUpdateContainerProps
) {
  const { book, bookCopy, close } = props;

  async function onSubmitAsync(
    bookCopy: BookCopyDisplay
  ): Promise<BookCopyDisplay> {
    try {
      return await updateBookCopyAsync(bookCopy);
    } finally {
      close();
    }
  }

  function BookCopyUpdateFormHeader(): JSX.Element {
    return (
      <div
        className="font-bold"
        style={{
          display: "grid",
          gridAutoFlow: "column",
          gridTemplateColumns: "8% auto 8%",
        }}
      >
        <div></div>
        <div className="flex justify-center">
          <h3>Edit a book copy</h3>
        </div>
        <div className="flex justify-center">
          <button type="button" onClick={close}>
            <XMarkIcon width={"18px"} />
          </button>
        </div>
      </div>
    );
  }

  return (
    <BookCopyForm
      book={book}
      bookCopy={bookCopy}
      onSubmitAsync={onSubmitAsync}
      clearOnSubmit={false}
      formHeader={<BookCopyUpdateFormHeader />}
    />
  );
}
