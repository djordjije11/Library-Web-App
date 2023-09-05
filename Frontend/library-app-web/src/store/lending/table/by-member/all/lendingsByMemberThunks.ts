import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../../../models/error/AlertError";
import { StoreState } from "../../../../config/store";
import { LendingIncludingBookCopy } from "../../../../../models/lending/LendingIncludingBookCopy";
import { getLendingsByMemberAsync } from "../../../../../request/lending/lendingRequests";
import ResponseError from "../../../../../request/ResponseError";

export const getLendingsByMemberAsyncThunk = createAsyncThunk<
  { lendings: LendingIncludingBookCopy[]; totalPages: number },
  void,
  { rejectValue: AlertError }
>(
  "lendingsByMember/getLendingsByMemberAsync",
  async (_, { getState, rejectWithValue }) => {
    const state = getState() as StoreState;
    try {
      return await getLendingsByMemberAsync(
        state.lendingsByMember.lendingsByMember.member.id,
        state.lendingsByMember.requestQueryParams
      );
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
