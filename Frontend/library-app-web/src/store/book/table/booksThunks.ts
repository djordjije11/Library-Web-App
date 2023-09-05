import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import { BookShort } from "../../../models/book/BookShort";
import { StoreState } from "../../config/store";
import { getBooksAsync } from "../../../request/book/bookRequests";
import ResponseError from "../../../request/ResponseError";

export const getBooksAsyncThunk = createAsyncThunk<
  { books: BookShort[]; totalPages: number; totalItemsCount: number },
  void,
  { rejectValue: AlertError }
>("books/getBooksAsync", async (_, { getState, rejectWithValue }) => {
  const state = getState() as StoreState;
  try {
    return await getBooksAsync(state.books.requestQueryParams);
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});
