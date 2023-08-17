import axios from "axios";
import { GET_ALL_BOOKS_COPIES_AVAILABLE_IN_BUILDING } from "../apiUrls";
import { extractTotalPagesFromHeaders, getHeaders } from "../requestHeaders";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import {
  BookCopyDisplay,
  BookCopyDisplayFromServer,
  constructBookCopyDisplayArray,
} from "../../models/bookcopy/BookCopyDisplay";

export async function getAllBooksCopiesAvailableInBuildingAsync(
  requestQueryParams: RequestQueryParams
): Promise<{
  bookCopies: BookCopyDisplay[];
  totalPages: number;
}> {
  const response = await axios.get(
    GET_ALL_BOOKS_COPIES_AVAILABLE_IN_BUILDING +
      constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const bookCopies = constructBookCopyDisplayArray(
    response.data as BookCopyDisplayFromServer[]
  );

  const totalPages: number = extractTotalPagesFromHeaders(response.headers);
  return {
    bookCopies,
    totalPages,
  };
}
