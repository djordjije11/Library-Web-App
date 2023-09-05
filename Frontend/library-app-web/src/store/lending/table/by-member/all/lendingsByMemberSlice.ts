import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { LendingIncludingBookCopy } from "../../../../../models/lending/LendingIncludingBookCopy";
import { LendingsByMemberState } from "./LendingsByMemberState";
import { getLendingsByMemberAsyncThunk } from "./lendingsByMemberThunks";
import RequestQueryParams from "../../../../../models/request/RequestQueryParams";
import MemberShort from "../../../../../models/member/MemberShort";

const initialState = {
  lendingsByMember: {
    lendings: [] as LendingIncludingBookCopy[],
  },
} as LendingsByMemberState;

const lendingsByMemberSlice = createSlice({
  name: "lendingsByMember",
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
        state.lendingsByMember.member !== undefined &&
        state.lendingsByMember.member.id !== action.payload.id
      ) {
        state.lendingsByMember.lendings = [] as LendingIncludingBookCopy[];
      }
      state.lendingsByMember.member = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getLendingsByMemberAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(
      getLendingsByMemberAsyncThunk.fulfilled,
      (state, action) => {
        state.lendingsByMember.lendings = action.payload.lendings;
        state.totalPages = action.payload.totalPages;
        state.loading = false;
      }
    );
    builder.addCase(getLendingsByMemberAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
  },
});

export default lendingsByMemberSlice.reducer;
export const lendingsByMemberActions = lendingsByMemberSlice.actions;
