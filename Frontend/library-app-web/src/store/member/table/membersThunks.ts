import { createAsyncThunk } from "@reduxjs/toolkit";
import { getMembersAsync } from "../../../request/member/memberRequests";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import ResponseError from "../../../request/ResponseError";
import MemberShort from "../../../models/member/MemberShort";
import MembersState from "./MembersState";
import { StoreState } from "../../config/store";

export const getMembersAsyncThunk = createAsyncThunk<
  { members: MemberShort[]; totalPages: number },
  void,
  { rejectValue: AlertError }
>("members/getMembersAsync", async (_, { getState, rejectWithValue }) => {
  const state: StoreState = getState() as StoreState;
  try {
    return await getMembersAsync(state.members.requestQueryParams);
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});
