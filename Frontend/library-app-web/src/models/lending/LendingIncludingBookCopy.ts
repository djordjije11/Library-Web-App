import { BookCopyDisplay } from "../bookcopy/BookCopyDisplay";

export interface LendingIncludingBookCopy {
  rowVersion: number;
  id: number;
  lendingDate: string;
  returnDate: string;
  bookCopy: BookCopyDisplay;
  memberId: number;
}

export function getBookCopiesFromLendings(
  lendings: LendingIncludingBookCopy[]
) {
  return lendings.map((lending) => lending.bookCopy);
}
