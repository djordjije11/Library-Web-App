import { createAsyncThunk } from "@reduxjs/toolkit";
import Member from "../../models/member/Member";
import { getMembersAsync } from "../../request/member/memberRequests";
import AlertError, { constructAlertError } from "../../models/error/AlertError";
import ResponseError from "../../request/ResponseError";
import RequestQueryParams from "../../models/request/RequestQueryParams";

export const getMembersAsyncThunk = createAsyncThunk<
  { members: Member[]; totalPages: number },
  RequestQueryParams,
  { rejectValue: AlertError }
>(
  "members/getMembersAsync",
  async (requestQueryParams, { rejectWithValue }) => {
    try {
      return getMembersAsync(requestQueryParams);
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
