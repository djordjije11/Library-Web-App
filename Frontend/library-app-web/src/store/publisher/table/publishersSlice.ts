import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import PublishersState from "./PublishersState";
import { getPublishersAsyncThunk } from "./publishersThunks";
import RequestQueryParams from "../../../models/request/RequestQueryParams";
import PublisherShort from "../../../models/publisher/PublisherShort";

const initialState = { publishers: [] as PublisherShort[] } as PublishersState;

const publishersSlice = createSlice({
  name: "publishers",
  initialState,
  reducers: {
    setRequestQueryParams: (
      state,
      action: PayloadAction<RequestQueryParams>
    ) => {
      state.requestQueryParams = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getPublishersAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(getPublishersAsyncThunk.fulfilled, (state, action) => {
      state.loading = false;
      state.totalPages = action.payload.totalPages;
      state.publishers = action.payload.publishers;
    });
    builder.addCase(getPublishersAsyncThunk.rejected, (state, action) => {
      state.loading = false;
      state.isError = true;
      state.error = action.error;
    });
  },
});

export default publishersSlice.reducer;
export const publishersActions = publishersSlice.actions;
