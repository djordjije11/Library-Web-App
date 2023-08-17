import axios from "axios";
import {
  LendingsAdd,
  constructLendingsAddFromServer,
} from "../../models/lending/LendingsAdd";
import {
  ADD_LENDINGS_URL,
  GET_UNRETURNED_LENDINGS_BY_MEMBER,
  RETURN_LENDINGS_URL,
} from "../apiUrls";
import { extractTotalPagesFromHeaders, getHeaders } from "../requestHeaders";
import {
  LendingsReturn,
  constructLendingsReturnFromServer,
} from "../../models/lending/LendingsReturn";
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
  lendingsReturn: LendingsReturn
): Promise<void> {
  const response = await axios.put(
    RETURN_LENDINGS_URL,
    constructLendingsReturnFromServer(lendingsReturn),
    { headers: getHeaders() }
  );
}

export async function getUnreturnedLendingsByMemberAsync(
  memberId: number,
  requestQueryParams: RequestQueryParams
): Promise<{ lendings: LendingIncludingBookCopy[]; totalPages: number }> {
  console.log(
    GET_UNRETURNED_LENDINGS_BY_MEMBER(memberId) +
      constructRequestQuery(requestQueryParams)
  );
  const response = await axios.get(
    GET_UNRETURNED_LENDINGS_BY_MEMBER(memberId) +
      constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  console.log(response);
  const lendings = response.data as LendingIncludingBookCopy[];
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  return { lendings, totalPages };
}
