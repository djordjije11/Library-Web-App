import { createAsyncThunk } from "@reduxjs/toolkit";
import Member from "../../models/member/Member";
import AlertError, { constructAlertError } from "../../models/error/AlertError";
import ResponseError from "../../request/ResponseError";
import { addMemberAsync } from "../../request/member/memberRequests";

export const addMemberAsyncThunk = createAsyncThunk<
  Member,
  Member,
  { rejectValue: AlertError }
>("memberAdd/addMemberAsync", async (member: Member, { rejectWithValue }) => {
  try {
    const dbMember = await addMemberAsync(member);
    return dbMember;
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});
