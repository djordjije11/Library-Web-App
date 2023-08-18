import { Book } from "../../../../models/book/Book";
import { BookCopyDisplay } from "../../../../models/bookcopy/BookCopyDisplay";
import { BookCopyStatus } from "../../../../models/bookcopy/BookCopyStatus";
import AlertError from "../../../../models/error/AlertError";
import RequestQueryParams from "../../../../models/request/RequestQueryParams";

export default interface BookCopiesState {
  book: Book;
  availableCopiesInBuildingCount: number;
  bookCopies: BookCopyDisplay[];
  totalPages: number;
  requestQueryParams: RequestQueryParams;
  status?: BookCopyStatus;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
