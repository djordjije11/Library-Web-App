import { XMarkIcon } from "@heroicons/react/24/outline";
import BookCopyForm from "./BookCopyForm";
import { addBookCopyAsync } from "../../request/bookcopy/bookCopyRequests";
import { Book } from "../../models/book/Book";
import { BookShort } from "../../models/book/BookShort";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";

export interface BookCopyAddContainerProps {
  book: Book;
  close: () => void;
}

export default function BookCopyAddContainer(props: BookCopyAddContainerProps) {
  const { book, close } = props;

  async function onSubmitAsync(bookCopy: BookCopyDisplay) {
    bookCopy.book = { id: book.id } as BookShort;
    try {
      return await addBookCopyAsync(bookCopy);
    } catch (error) {
      close();
      throw error;
    }
  }

  function BookCopyAddFormHeader(): JSX.Element {
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
          <h3>Add a book copy</h3>
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
      clearOnSubmit={true}
      formHeader={<BookCopyAddFormHeader />}
      onSubmitAsync={onSubmitAsync}
    />
  );
}
