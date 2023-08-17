import MemberShort from "../member/MemberShort";
import { LendingIncludingBookCopy } from "./LendingIncludingBookCopy";

export interface LendingsReturn {
  lendings: LendingIncludingBookCopy[];
  member: MemberShort;
}

export interface LendingsReturnFromServer {
  lendingsIds: number[];
  memberId: number;
}

export function constructLendingsReturnFromServer(
  lendingsReturn: LendingsReturn
): LendingsReturnFromServer {
  return {
    memberId: lendingsReturn.member.id,
    lendingsIds: extractIdsFromLendings(lendingsReturn.lendings),
  };
}

function extractIdsFromLendings(
  lendings: LendingIncludingBookCopy[]
): number[] {
  return lendings.map((lending) => lending.id);
}
