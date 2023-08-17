import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { LendingIncludingBookCopy } from "../../../models/lending/LendingIncludingBookCopy";
import { LendingsReturnState } from "./LendingsReturnState";
import { returnLendingsAsyncThunk } from "./lendingsReturnThunks";
import MemberShort from "../../../models/member/MemberShort";

const initialState: LendingsReturnState = {
  lendingsReturn: { lendings: [] as LendingIncludingBookCopy[] },
} as LendingsReturnState;

const lendingsReturnSlice = createSlice({
  name: "lendingsReturn",
  initialState,
  reducers: {
    setMember: (state, action: PayloadAction<MemberShort>) => {
      if (
        state.lendingsReturn.member !== undefined &&
        state.lendingsReturn.member.id !== action.payload.id
      ) {
        state.lendingsReturn.lendings = [] as LendingIncludingBookCopy[];
      }
      state.lendingsReturn.member = action.payload;
    },
    addLending: (state, action: PayloadAction<LendingIncludingBookCopy>) => {
      if (
        state.lendingsReturn.lendings.some(
          (lending) => lending.id === action.payload.id
        ) === false
      ) {
        state.lendingsReturn.lendings.push(action.payload);
      }
    },
    removeLendingByBookCopyId: (state, action: PayloadAction<number>) => {
      state.lendingsReturn.lendings = state.lendingsReturn.lendings.filter(
        (lending) => lending.bookCopy.id !== action.payload
      );
    },
  },
  extraReducers: (builder) => {
    builder.addCase(returnLendingsAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(returnLendingsAsyncThunk.rejected, (state, action) => {
      state.loading = false;
      state.isError = true;
      state.error = action.error;
    });
    builder.addCase(returnLendingsAsyncThunk.fulfilled, (state) => {
      state.loading = false;
      state.lendingsReturn = initialState.lendingsReturn;
    });
  },
});

export default lendingsReturnSlice.reducer;
export const lendingsReturnActions = lendingsReturnSlice.actions;
