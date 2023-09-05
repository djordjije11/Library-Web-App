import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import { StoreState } from "../../config/store";
import { getAuthorsAsync } from "../../../request/author/authorRequests";
import ResponseError from "../../../request/ResponseError";
import AuthorShort from "../../../models/author/AuthorShort";

export const getAuthorsAsyncThunk = createAsyncThunk<
  { authors: AuthorShort[]; totalPages: number; totalItemsCount: number },
  void,
  { rejectValue: AlertError }
>("authors/getAuthorsAsync", async (_, { getState, rejectWithValue }) => {
  const state = getState() as StoreState;
  try {
    return await getAuthorsAsync(state.authors.requestQueryParams);
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});
