import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import RequestQueryParams from "../../../../models/request/RequestQueryParams";
import { BookCopyDisplay } from "../../../../models/bookcopy/BookCopyDisplay";
import BookCopiesState from "./BookCopiesState";
import {
  getBookAsyncThunk,
  getBookCopiesInAllBuildingsAsyncThunk,
} from "./bookCopiesThunks";
import { Book } from "../../../../models/book/Book";
import { BookCopyStatus } from "../../../../models/bookcopy/BookCopyStatus";

const initialState: BookCopiesState = {
  bookCopies: [] as BookCopyDisplay[],
} as BookCopiesState;

const bookCopiesSlice = createSlice({
  name: "bookCopies",
  initialState,
  reducers: {
    setBook: (state, action: PayloadAction<Book>) => {
      state.book = action.payload;
    },
    setRequestQueryParams: (
      state,
      action: PayloadAction<RequestQueryParams>
    ) => {
      state.requestQueryParams = action.payload;
    },
    setBookCopyStatus: (
      state,
      action: PayloadAction<BookCopyStatus | undefined>
    ) => {
      state.status = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getBookAsyncThunk.fulfilled, (state, action) => {
      state.availableCopiesInBuildingCount =
        action.payload.availableCopiesInBuildingCount;
      state.book = action.payload.book;
    });
    builder.addCase(getBookAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
    });
    builder.addCase(getBookCopiesInAllBuildingsAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(
      getBookCopiesInAllBuildingsAsyncThunk.fulfilled,
      (state, action) => {
        state.bookCopies = action.payload.bookCopies;
        state.totalPages = action.payload.totalPages;
        state.loading = false;
      }
    );
    builder.addCase(
      getBookCopiesInAllBuildingsAsyncThunk.rejected,
      (state, action) => {
        state.isError = true;
        state.error = action.payload;
        state.loading = false;
      }
    );
  },
});

export default bookCopiesSlice.reducer;
export const bookCopiesActions = bookCopiesSlice.actions;
