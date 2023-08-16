import { BookShort, BookShortFromServer } from "../book/BookShort";
import { BookCopyStatus } from "./BookCopyStatus";

export interface BookCopyDisplayFromServer {
  rowVersion: number;
  id: number;
  isbn: string;
  status: BookCopyStatus;
  book: BookShortFromServer;
}

export interface BookCopyDisplay {
  rowVersion: number;
  id: number;
  isbn: string;
  status: BookCopyStatus;
  book: BookShort;
}

export function constructBookCopyDisplayArray(
  bookCopies: BookCopyDisplayFromServer[]
): BookCopyDisplay[] {
  return bookCopies.map((bc) => constructBookCopyDisplay(bc));
}

export function constructBookCopyDisplay(
  bookCopy: BookCopyDisplayFromServer
): BookCopyDisplay {
  return {
    ...bookCopy,
    book: {
      ...bookCopy.book,
      authors: formatAuthors(bookCopy.book.authors),
      publisher: { name: bookCopy.book.publisher },
    },
  };
}

function formatAuthors(authors: string): string {
  const authorsArray: string[] = authors.split(";", 4);
  console.log(authorsArray);
  if (authorsArray.length === 4) {
    return authorsArray.join(";").concat("...");
  }
  return authors;
}
