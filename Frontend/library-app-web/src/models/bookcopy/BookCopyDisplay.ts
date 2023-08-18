import {
  BookShort,
  BookShortFromServer,
  constructBookShort,
} from "../book/BookShort";
import { BookCopyStatus } from "./BookCopyStatus";

export interface BookCopyDisplayFromServer {
  rowVersion: number;
  id: number;
  isbn: string;
  status: BookCopyStatus;
  book: BookShortFromServer;
  building: string;
}

export interface BookCopyDisplay {
  rowVersion: number;
  id: number;
  isbn: string;
  status: BookCopyStatus;
  book: BookShort;
  building: string;
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
    book: constructBookShort(bookCopy.book),
  };
}
