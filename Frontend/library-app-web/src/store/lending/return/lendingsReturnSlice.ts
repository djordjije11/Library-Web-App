import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { LendingIncludingBookCopy } from "../../../models/lending/LendingIncludingBookCopy";
import { LendingsReturnState } from "./LendingsReturnState";
import { returnLendingsAsyncThunk } from "./lendingsReturnThunks";
import MemberShort from "../../../models/member/MemberShort";

const initialState: LendingsReturnState = {
  lendingsByMember: { lendings: [] as LendingIncludingBookCopy[] },
} as LendingsReturnState;

const lendingsReturnSlice = createSlice({
  name: "lendingsReturn",
  initialState,
  reducers: {
    setMember: (state, action: PayloadAction<MemberShort>) => {
      if (
        state.lendingsByMember.member !== undefined &&
        state.lendingsByMember.member.id !== action.payload.id
      ) {
        state.lendingsByMember.lendings = [] as LendingIncludingBookCopy[];
      }
      state.lendingsByMember.member = action.payload;
    },
    addLending: (state, action: PayloadAction<LendingIncludingBookCopy>) => {
      if (
        state.lendingsByMember.lendings.some(
          (lending) => lending.id === action.payload.id
        ) === false
      ) {
        state.lendingsByMember.lendings.push(action.payload);
      }
    },
    removeLendingByBookCopyId: (state, action: PayloadAction<number>) => {
      state.lendingsByMember.lendings = state.lendingsByMember.lendings.filter(
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
      state.lendingsByMember = initialState.lendingsByMember;
    });
  },
});

export default lendingsReturnSlice.reducer;
export const lendingsReturnActions = lendingsReturnSlice.actions;
