import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import { addLendingsAsync } from "../../../request/lending/lendingRequests";
import ResponseError from "../../../request/ResponseError";
import { StoreState } from "../../config/store";

export const addLendingsAsyncThunk = createAsyncThunk<
  void,
  void,
  { rejectValue: AlertError }
>("lendingsAdd/addLendingsAsync", async (_, { getState, rejectWithValue }) => {
  const state = getState() as StoreState;
  try {
    await addLendingsAsync(state.lendingsAdd.lendingsAdd);
  } catch (error) {
    rejectWithValue(constructAlertError(error as ResponseError));
  }
});
