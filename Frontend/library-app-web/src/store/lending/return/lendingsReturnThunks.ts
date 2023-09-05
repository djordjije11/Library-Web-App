import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import { StoreState } from "../../config/store";
import { returnLendingsAsync } from "../../../request/lending/lendingRequests";
import ResponseError from "../../../request/ResponseError";

export const returnLendingsAsyncThunk = createAsyncThunk<
  void,
  void,
  { rejectValue: AlertError }
>(
  "lendingsReturn/returnLendingsAsync",
  async (_, { getState, rejectWithValue }) => {
    const state = getState() as StoreState;
    try {
      await returnLendingsAsync(state.lendingsReturn.lendingsByMember);
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
