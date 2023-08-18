import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../../models/error/AlertError";
import { StoreState } from "../../../config/store";
import { LendingIncludingBookCopy } from "../../../../models/lending/LendingIncludingBookCopy";
import { getUnreturnedLendingsByMemberAsync } from "../../../../request/lending/lendingRequests";
import ResponseError from "../../../../request/ResponseError";

export const getUnreturnedLendingsByMemberAsyncThunk = createAsyncThunk<
  { lendings: LendingIncludingBookCopy[]; totalPages: number },
  void,
  { rejectValue: AlertError }
>(
  "lendingsUnreturned/getUnreturnedLendingsByMemberAsync",
  async (_, { getState, rejectWithValue }) => {
    const state = getState() as StoreState;
    try {
      return await getUnreturnedLendingsByMemberAsync(
        state.lendingsUnreturned.lendingsReturn.member.id,
        state.lendingsUnreturned.requestQueryParams
      );
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);