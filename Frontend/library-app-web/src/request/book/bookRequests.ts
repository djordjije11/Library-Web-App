import axios from "axios";
import {
  BookShort,
  BookShortFromServer,
  constructBookShortArray,
} from "../../models/book/BookShort";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import { DELETE_BOOK_URL, GET_BOOKS_URL, GET_BOOK_URL } from "../apiUrls";
import { extractTotalPagesFromHeaders, getHeaders } from "../requestHeaders";
import { Book } from "../../models/book/Book";

export async function getBooksAsync(
  requestQueryParams: RequestQueryParams
): Promise<{ books: BookShort[]; totalPages: number }> {
  const response = await axios.get(
    GET_BOOKS_URL + constructRequestQuery(requestQueryParams),
    { headers: getHeaders() }
  );
  const books = constructBookShortArray(response.data as BookShortFromServer[]);
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  return { books, totalPages };
}

export async function deleteBookAsync(id: number) {
  const response = await axios.delete(DELETE_BOOK_URL(id), {
    headers: getHeaders(),
  });
}

export async function getBookAsync(
  id: number
): Promise<{ book: Book; availableCopiesInBuildingCount: number }> {
  const response = await axios.get(GET_BOOK_URL(id), { headers: getHeaders() });
  return response.data as {
    book: Book;
    availableCopiesInBuildingCount: number;
  };
}
