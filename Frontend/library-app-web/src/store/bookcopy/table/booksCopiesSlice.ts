import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import RequestQueryParams from "../../../models/request/RequestQueryParams";
import BooksCopiesState from "./BooksCopiesState";
import { BookCopyDisplay } from "../../../models/bookcopy/BookCopyDisplay";
import { getAllBooksCopiesAvailableInBuildingAsyncThunk } from "./booksCopiesThunks";

const initialState: BooksCopiesState = {
  bookCopies: [] as BookCopyDisplay[],
} as BooksCopiesState;

const booksCopiesSlice = createSlice({
  name: "booksCopies",
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
    builder.addCase(
      getAllBooksCopiesAvailableInBuildingAsyncThunk.pending,
      (state) => {
        state.loading = true;
      }
    );
    builder.addCase(
      getAllBooksCopiesAvailableInBuildingAsyncThunk.fulfilled,
      (state, action) => {
        state.bookCopies = action.payload.bookCopies;
        state.totalPages = action.payload.totalPages;
        state.loading = false;
      }
    );
    builder.addCase(
      getAllBooksCopiesAvailableInBuildingAsyncThunk.rejected,
      (state, action) => {
        state.isError = true;
        state.error = action.payload;
        state.loading = false;
      }
    );
  },
});

export default booksCopiesSlice.reducer;
export const booksCopiesActions = booksCopiesSlice.actions;
