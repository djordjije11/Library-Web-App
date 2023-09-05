import axios from "axios";
import {
  BookShort,
  BookShortFromServer,
  constructBookShortArray,
} from "../../models/book/BookShort";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import {
  ADD_BOOK_URL,
  DELETE_BOOK_URL,
  GET_BOOKS_URL,
  GET_BOOK_URL,
  UPDATE_BOOK_URL,
} from "../apiUrls";
import {
  extractTotalItemsCountFromHeaders,
  extractTotalPagesFromHeaders,
  getHeaders,
} from "../requestHeaders";
import { Book } from "../../models/book/Book";
import BookAdd from "../../models/book/BookSave";
import BookSave from "../../models/book/BookSave";

export async function getBooksAsync(
  requestQueryParams: RequestQueryParams
): Promise<{
  books: BookShort[];
  totalPages: number;
  totalItemsCount: number;
}> {
  const response = await axios.get(
    GET_BOOKS_URL + constructRequestQuery(requestQueryParams),
    { headers: getHeaders() }
  );
  const books = constructBookShortArray(response.data as BookShortFromServer[]);
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  const totalItemsCount = extractTotalItemsCountFromHeaders(response.headers);
  return { books, totalPages, totalItemsCount };
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

export async function addBookAsync(book: BookAdd): Promise<Book> {
  const response = await axios.post(ADD_BOOK_URL, book, {
    headers: getHeaders(),
  });
  return response.data as Book;
}

export async function updateBookAsync(book: BookSave): Promise<Book> {
  const response = await axios.put(UPDATE_BOOK_URL(book.id), book, {
    headers: getHeaders(),
  });
  return response.data as Book;
}
