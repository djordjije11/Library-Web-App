import { createAsyncThunk } from "@reduxjs/toolkit";
import MemberDetail from "../../../models/member/MemberDetail";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import {
  getMemberAsync,
  updateMemberAsync,
} from "../../../request/member/memberRequests";
import ResponseError from "../../../request/ResponseError";

export const getMemberAsyncThunk = createAsyncThunk<
  MemberDetail,
  number,
  { rejectValue: AlertError }
>("members/getMemberAsync", async (id: number, { rejectWithValue }) => {
  try {
    return await getMemberAsync(id);
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});

export const updateMemberAsyncThunk = createAsyncThunk<
  MemberDetail,
  MemberDetail,
  { rejectValue: AlertError }
>(
  "members/updateMemberAsync",
  async (member: MemberDetail, { rejectWithValue }) => {
    try {
      return await updateMemberAsync(member);
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
