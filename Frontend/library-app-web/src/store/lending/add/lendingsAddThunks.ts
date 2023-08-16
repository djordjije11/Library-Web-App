import { createAsyncThunk } from "@reduxjs/toolkit";
import LendingsAdd from "../../../models/lending/LendingsAdd";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import { addLendingsAsync } from "../../../request/lending/lendingRequests";
import ResponseError from "../../../request/ResponseError";

export const addLendingsAsyncThunk = createAsyncThunk<
  void,
  LendingsAdd,
  { rejectValue: AlertError }
>(
  "lendingsAdd/addLendingsAsync",
  async (lendingsAdd: LendingsAdd, { rejectWithValue }) => {
    try {
      await addLendingsAsync(lendingsAdd);
    } catch (error) {
      rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
