import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import { StoreState } from "../../config/store";
import ResponseError from "../../../request/ResponseError";
import { getPublishersAsync } from "../../../request/publisher/publishersRequests";
import PublisherShort from "../../../models/publisher/PublisherShort";

export const getPublishersAsyncThunk = createAsyncThunk<
  { publishers: PublisherShort[]; totalPages: number },
  void,
  { rejectValue: AlertError }
>("publishers/getPublishersAsync", async (_, { getState, rejectWithValue }) => {
  const state = getState() as StoreState;
  try {
    return await getPublishersAsync(state.publishers.requestQueryParams);
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});
