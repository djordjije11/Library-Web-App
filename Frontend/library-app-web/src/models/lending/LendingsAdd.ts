import { BookCopyDisplay } from "../bookcopy/BookCopyDisplay";
import MemberShort from "../member/MemberShort";

export interface LendingsAdd {
  member: MemberShort;
  bookCopies: BookCopyDisplay[];
}

export interface LendingsAddFromServer {
  memberId: number;
  bookCopiesIds: number[];
}

export function constructLendingsAddFromServer(
  lendingsAdd: LendingsAdd
): LendingsAddFromServer {
  return {
    ...lendingsAdd,
    memberId: lendingsAdd.member.id,
    bookCopiesIds: extractIdsFromBookCopies(lendingsAdd.bookCopies),
  };
}

function extractIdsFromBookCopies(bookCopies: BookCopyDisplay[]): number[] {
  return bookCopies.map((bc) => bc.id);
}
