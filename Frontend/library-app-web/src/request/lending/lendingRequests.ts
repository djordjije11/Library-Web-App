import axios from "axios";
import {
  LendingsAdd,
  constructLendingsAddFromServer,
} from "../../models/lending/LendingsAdd";
import {
  ADD_LENDINGS_URL,
  GET_LENDINGS_BY_MEMBER,
  GET_UNRETURNED_LENDINGS_BY_MEMBER,
  RETURN_LENDINGS_URL,
} from "../apiUrls";
import { extractTotalPagesFromHeaders, getHeaders } from "../requestHeaders";
import {
  LendingsByMember,
  LendingsReturn,
  constructLendingsReturn,
} from "../../models/lending/LendingsByMember";
import { LendingIncludingBookCopy } from "../../models/lending/LendingIncludingBookCopy";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";

export async function addLendingsAsync(
  lendingsAdd: LendingsAdd
): Promise<void> {
  const response = await axios.post(
    ADD_LENDINGS_URL,
    constructLendingsAddFromServer(lendingsAdd),
    {
      headers: getHeaders(),
    }
  );
}

export async function returnLendingsAsync(
  lendingsByMember: LendingsByMember
): Promise<void> {
  const response = await axios.put(
    RETURN_LENDINGS_URL,
    constructLendingsReturn(lendingsByMember),
    { headers: getHeaders() }
  );
}

export async function getUnreturnedLendingsByMemberAsync(
  memberId: number,
  requestQueryParams: RequestQueryParams
): Promise<{ lendings: LendingIncludingBookCopy[]; totalPages: number }> {
  const response = await axios.get(
    GET_UNRETURNED_LENDINGS_BY_MEMBER(memberId) +
      constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const lendings = response.data as LendingIncludingBookCopy[];
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  return { lendings, totalPages };
}

export async function getLendingsByMemberAsync(
  memberId: number,
  requestQueryParams: RequestQueryParams
): Promise<{ lendings: LendingIncludingBookCopy[]; totalPages: number }> {
  const response = await axios.get(
    GET_LENDINGS_BY_MEMBER(memberId) +
      constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const lendings = response.data as LendingIncludingBookCopy[];
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  return { lendings, totalPages };
}
