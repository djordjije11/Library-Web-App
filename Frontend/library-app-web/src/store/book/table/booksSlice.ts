import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { BookShort } from "../../../models/book/BookShort";
import BooksState from "./BooksState";
import { getBooksAsyncThunk } from "./booksThunks";
import RequestQueryParams from "../../../models/request/RequestQueryParams";

const initialState: BooksState = { books: [] as BookShort[] } as BooksState;

const booksSlice = createSlice({
  name: "books",
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
    builder.addCase(getBooksAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(getBooksAsyncThunk.fulfilled, (state, action) => {
      state.loading = false;
      state.books = action.payload.books;
      state.totalPages = action.payload.totalPages;
      state.totalItemsCount = action.payload.totalItemsCount;
    });
    builder.addCase(getBooksAsyncThunk.rejected, (state, action) => {
      state.loading = false;
      state.isError = true;
      state.error = action.payload;
    });
  },
});

export default booksSlice.reducer;
export const booksActions = booksSlice.actions;
