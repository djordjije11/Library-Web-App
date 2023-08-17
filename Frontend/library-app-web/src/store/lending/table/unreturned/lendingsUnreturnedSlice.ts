import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { LendingIncludingBookCopy } from "../../../../models/lending/LendingIncludingBookCopy";
import { LendingsUnreturnedState } from "./LendingsUnreturnedState";
import { getUnreturnedLendingsByMemberAsyncThunk } from "./lendingsUnreturnedThunks";
import RequestQueryParams from "../../../../models/request/RequestQueryParams";
import MemberShort from "../../../../models/member/MemberShort";

const initialState = {
  lendingsReturn: {
    lendings: [] as LendingIncludingBookCopy[],
  },
} as LendingsUnreturnedState;

const lendingsUnreturnedSlice = createSlice({
  name: "lendingsUnreturned",
  initialState,
  reducers: {
    setRequestQueryParams: (
      state,
      action: PayloadAction<RequestQueryParams>
    ) => {
      state.requestQueryParams = action.payload;
    },
    setMember: (state, action: PayloadAction<MemberShort>) => {
      if (
        state.lendingsReturn.member !== undefined &&
        state.lendingsReturn.member.id !== action.payload.id
      ) {
        state.lendingsReturn.lendings = [] as LendingIncludingBookCopy[];
      }
      state.lendingsReturn.member = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(
      getUnreturnedLendingsByMemberAsyncThunk.pending,
      (state) => {
        state.loading = true;
      }
    );
    builder.addCase(
      getUnreturnedLendingsByMemberAsyncThunk.fulfilled,
      (state, action) => {
        state.lendingsReturn.lendings = action.payload.lendings;
        state.totalPages = action.payload.totalPages;
        state.loading = false;
      }
    );
    builder.addCase(
      getUnreturnedLendingsByMemberAsyncThunk.rejected,
      (state, action) => {
        state.isError = true;
        state.error = action.payload;
        state.loading = false;
      }
    );
  },
});

export default lendingsUnreturnedSlice.reducer;
export const lendingsUnreturnedActions = lendingsUnreturnedSlice.actions;
