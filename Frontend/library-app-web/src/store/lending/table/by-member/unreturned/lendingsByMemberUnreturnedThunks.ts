import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../../../models/error/AlertError";
import { StoreState } from "../../../../config/store";
import { LendingIncludingBookCopy } from "../../../../../models/lending/LendingIncludingBookCopy";
import { getUnreturnedLendingsByMemberAsync } from "../../../../../request/lending/lendingRequests";
import ResponseError from "../../../../../request/ResponseError";

export const getUnreturnedLendingsByMemberAsyncThunk = createAsyncThunk<
  {
    lendings: LendingIncludingBookCopy[];
    totalPages: number;
    totalItemsCount: number;
  },
  void,
  { rejectValue: AlertError }
>(
  "lendingsByMemberUnreturned/getUnreturnedLendingsByMemberAsync",
  async (_, { getState, rejectWithValue }) => {
    const state = getState() as StoreState;
    try {
      return await getUnreturnedLendingsByMemberAsync(
        state.lendingsByMemberUnreturned.lendingsByMember.member.id,
        state.lendingsByMemberUnreturned.requestQueryParams
      );
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
