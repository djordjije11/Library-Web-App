import { createAsyncThunk } from "@reduxjs/toolkit";
import { getAllBooksCopiesAvailableInBuildingAsync } from "../../../../request/bookcopy/bookCopyRequests";
import { BookCopyDisplay } from "../../../../models/bookcopy/BookCopyDisplay";
import AlertError, {
  constructAlertError,
} from "../../../../models/error/AlertError";
import ResponseError from "../../../../request/ResponseError";
import { StoreState } from "../../../config/store";

export const getAllBooksCopiesAvailableInBuildingAsyncThunk = createAsyncThunk<
  {
    bookCopies: BookCopyDisplay[];
    totalPages: number;
  },
  void,
  { rejectValue: AlertError }
>(
  "booksCopies/getAllBooksCopiesAvailableInBuildingAsync",
  async (_, { getState, rejectWithValue }) => {
    const state = getState() as StoreState;
    try {
      console.log(state.booksCopies.requestQueryParams);
      return await getAllBooksCopiesAvailableInBuildingAsync(
        state.booksCopies.requestQueryParams
      );
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
