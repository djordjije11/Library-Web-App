import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import AuthorShort from "../../../models/author/AuthorShort";
import AuthorsState from "./AuthorsState";
import RequestQueryParams from "../../../models/request/RequestQueryParams";
import { getAuthorsAsyncThunk } from "./authorsThunks";

const initialState = { authors: [] as AuthorShort[] } as AuthorsState;

const authorsSlice = createSlice({
  name: "authors",
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
    builder.addCase(getAuthorsAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(getAuthorsAsyncThunk.fulfilled, (state, action) => {
      state.loading = false;
      state.authors = action.payload.authors;
      state.totalPages = action.payload.totalPages;
      state.totalItemsCount = action.payload.totalItemsCount;
    });
    builder.addCase(getAuthorsAsyncThunk.rejected, (state) => {
      state.loading = false;
      state.isError = true;
      state.error = state.error;
    });
  },
});

export default authorsSlice.reducer;
export const authorsActions = authorsSlice.actions;
