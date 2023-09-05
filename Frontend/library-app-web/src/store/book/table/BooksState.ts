import { BookShort } from "../../../models/book/BookShort";
import AlertError from "../../../models/error/AlertError";
import RequestQueryParams from "../../../models/request/RequestQueryParams";

export default interface BooksState {
  books: BookShort[];
  totalPages: number;
  totalItemsCount: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
