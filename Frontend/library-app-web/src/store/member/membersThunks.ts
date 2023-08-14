import { createAsyncThunk } from "@reduxjs/toolkit";
import Member from "../../models/member/Member";
import { getMembersAsync } from "../../request/member/memberRequests";
import AlertError, { constructAlertError } from "../../models/error/AlertError";
import ResponseError from "../../request/ResponseError";

export const getMembersAsyncThunk = createAsyncThunk<
  { members: Member[]; totalPages: number },
  { pageNumber: number; pageSize: number },
  { rejectValue: AlertError }
>(
  "members/getMembersAsync",
  async ({ pageNumber, pageSize }, { rejectWithValue }) => {
    try {
      return getMembersAsync(pageNumber, pageSize);
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
