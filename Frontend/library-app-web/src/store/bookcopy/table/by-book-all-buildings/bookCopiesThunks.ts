import { createAsyncThunk } from "@reduxjs/toolkit";
import { BookCopyDisplay } from "../../../../models/bookcopy/BookCopyDisplay";
import AlertError, {
  constructAlertError,
} from "../../../../models/error/AlertError";
import { StoreState } from "../../../config/store";
import { getBookCopiesInAllBuildingsAsync } from "../../../../request/bookcopy/bookCopyRequests";
import ResponseError from "../../../../request/ResponseError";
import { Book } from "../../../../models/book/Book";
import { getBookAsync } from "../../../../request/book/bookRequests";

export const getBookCopiesInAllBuildingsAsyncThunk = createAsyncThunk<
  {
    bookCopies: BookCopyDisplay[];
    totalPages: number;
    totalItemsCount: number;
  },
  number,
  { rejectValue: AlertError }
>(
  "bookCopies/getBookCopiesInAllBuildingsAsync",
  async (bookId: number, { getState, rejectWithValue }) => {
    const state = getState() as StoreState;
    try {
      return await getBookCopiesInAllBuildingsAsync(
        bookId,
        state.bookCopies.requestQueryParams,
        state.bookCopies.status
      );
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);

export const getBookAsyncThunk = createAsyncThunk<
  { book: Book; availableCopiesInBuildingCount: number },
  number,
  { rejectValue: AlertError }
>("bookCopies/getBookAsync", async (id: number, { rejectWithValue }) => {
  try {
    return await getBookAsync(id);
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});
