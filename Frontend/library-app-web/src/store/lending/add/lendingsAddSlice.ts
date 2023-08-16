import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import LendingsAddState from "./LendingsAddState";
import { addLendingsAsyncThunk } from "./lendingsAddThunks";
import { LendingsAdd } from "../../../models/lending/LendingsAdd";
import { BookCopyDisplay } from "../../../models/bookcopy/BookCopyDisplay";
import MemberShort from "../../../models/member/MemberShort";

const initialState: LendingsAddState = {
  lendingsAdd: {
    bookCopies: [] as BookCopyDisplay[],
  } as LendingsAdd,
} as LendingsAddState;

const lendingsAddSlice = createSlice({
  name: "lendingsAdd",
  initialState,
  reducers: {
    setLendingsAdd: (state, action: PayloadAction<LendingsAdd>) => {
      state.lendingsAdd = action.payload;
    },
    setMember: (state, action: PayloadAction<MemberShort>) => {
      state.lendingsAdd.member = action.payload;
    },
    addBookCopy: (state, action: PayloadAction<BookCopyDisplay>) => {
      let shouldAdd: boolean = true;
      state.lendingsAdd.bookCopies.map((bookCopy) => {
        if (bookCopy.id === action.payload.id) {
          shouldAdd = false;
        }
      });
      if (shouldAdd) {
        state.lendingsAdd.bookCopies.push(action.payload);
      }
    },
    removeBookCopy: (state, action: PayloadAction<number>) => {
      state.lendingsAdd.bookCopies = state.lendingsAdd.bookCopies.filter(
        (bookCopy) => bookCopy.id !== action.payload
      );
    },
  },
  extraReducers: (builder) => {
    builder.addCase(addLendingsAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(addLendingsAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
    builder.addCase(addLendingsAsyncThunk.fulfilled, (state, action) => {
      state.lendingsAdd = initialState.lendingsAdd;
      state.loading = false;
    });
  },
});

export default lendingsAddSlice.reducer;
export const lendingsAddActions = lendingsAddSlice.actions;
