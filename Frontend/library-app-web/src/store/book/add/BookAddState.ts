import BookAdd from "../../../models/book/BookSave";
import AlertError from "../../../models/error/AlertError";

export default interface BookAddState {
  loading: boolean;
  book: BookAdd;
  isError: boolean;
  error?: AlertError;
}
