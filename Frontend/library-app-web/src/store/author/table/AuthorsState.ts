import AuthorShort from "../../../models/author/AuthorShort";
import AlertError from "../../../models/error/AlertError";
import RequestQueryParams from "../../../models/request/RequestQueryParams";

export default interface AuthorsState {
  authors: AuthorShort[];
  totalPages: number;
  totalItemsCount: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
