import axios from "axios";
import {
  ADD_BOOK_COPY_URL,
  DISCARD_BOOK_COPY_URL,
  GET_ALL_BOOKS_COPIES_AVAILABLE_IN_BUILDING,
  GET_BOOK_COPIES_IN_ALL_BUILDINGS_URL,
  UPDATE_BOOK_COPY_URL,
} from "../apiUrls";
import { extractTotalPagesFromHeaders, getHeaders } from "../requestHeaders";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import {
  BookCopyDisplay,
  BookCopyDisplayFromServer,
  constructBookCopyDisplay,
  constructBookCopyDisplayArray,
} from "../../models/bookcopy/BookCopyDisplay";
import { BookCopyStatus } from "../../models/bookcopy/BookCopyStatus";

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

export async function getBookCopiesInAllBuildingsAsync(
  bookId: number,
  requestQueryParams: RequestQueryParams,
  status?: BookCopyStatus
): Promise<{ bookCopies: BookCopyDisplay[]; totalPages: number }> {
  var url =
    GET_BOOK_COPIES_IN_ALL_BUILDINGS_URL(bookId) +
    constructRequestQuery(requestQueryParams);
  if (status !== undefined) {
    url += `&status=${status}`;
  }
  const response = await axios.get(url, { headers: getHeaders() });
  const bookCopies = constructBookCopyDisplayArray(
    response.data as BookCopyDisplayFromServer[]
  );
  const totalPages: number = extractTotalPagesFromHeaders(response.headers);
  return { bookCopies, totalPages };
}

export async function addBookCopyAsync(
  bookCopy: BookCopyDisplay
): Promise<BookCopyDisplay> {
  console.log("HEJ");
  const response = await axios.post(
    ADD_BOOK_COPY_URL(bookCopy.book.id),
    {
      bookId: bookCopy.book.id,
      isbn: bookCopy.isbn,
      status: BookCopyStatus.AVAILABLE,
    },
    { headers: getHeaders() }
  );
  return constructBookCopyDisplay(response.data as BookCopyDisplayFromServer);
}

export async function updateBookCopyAsync(
  bookCopy: BookCopyDisplay
): Promise<BookCopyDisplay> {
  const response = await axios.put(
    UPDATE_BOOK_COPY_URL(bookCopy.book.id, bookCopy.id),
    {
      rowVersion: bookCopy.rowVersion,
      isbn: bookCopy.isbn,
    },
    { headers: getHeaders() }
  );
  return constructBookCopyDisplay(response.data as BookCopyDisplayFromServer);
}

export async function discardBookCopyAsync(bookCopy: BookCopyDisplay) {
  const response = await axios.put(
    DISCARD_BOOK_COPY_URL(bookCopy.book.id, bookCopy.id),
    { rowVersion: bookCopy.rowVersion },
    { headers: getHeaders() }
  );
}
