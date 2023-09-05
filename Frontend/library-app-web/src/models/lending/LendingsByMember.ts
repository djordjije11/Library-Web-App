import MemberShort from "../member/MemberShort";
import { LendingIncludingBookCopy } from "./LendingIncludingBookCopy";

export interface LendingsByMember {
  lendings: LendingIncludingBookCopy[];
  member: MemberShort;
}

export interface LendingsReturn {
  lendingsIds: number[];
  memberId: number;
}

export function constructLendingsReturn(
  lendingsByMember: LendingsByMember
): LendingsReturn {
  return {
    memberId: lendingsByMember.member.id,
    lendingsIds: extractIdsFromLendings(lendingsByMember.lendings),
  };
}

function extractIdsFromLendings(
  lendings: LendingIncludingBookCopy[]
): number[] {
  return lendings.map((lending) => lending.id);
}
