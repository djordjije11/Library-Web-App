import { BookCopyDisplay } from "../../../../models/bookcopy/BookCopyDisplay";
import AlertError from "../../../../models/error/AlertError";
import RequestQueryParams from "../../../../models/request/RequestQueryParams";

export default interface BooksCopiesState {
  bookCopies: BookCopyDisplay[];
  totalPages: number;
  totalItemsCount: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
