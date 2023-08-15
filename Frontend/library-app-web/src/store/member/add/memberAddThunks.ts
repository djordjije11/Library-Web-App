import { createAsyncThunk } from "@reduxjs/toolkit";
import AlertError, {
  constructAlertError,
} from "../../../models/error/AlertError";
import ResponseError from "../../../request/ResponseError";
import { addMemberAsync } from "../../../request/member/memberRequests";
import MemberAdd from "../../../models/member/MemberAdd";
import MemberDetail from "../../../models/member/MemberDetail";

export const addMemberAsyncThunk = createAsyncThunk<
  MemberDetail,
  MemberAdd,
  { rejectValue: AlertError }
>(
  "memberAdd/addMemberAsync",
  async (member: MemberAdd, { rejectWithValue }) => {
    try {
      const dbMember = await addMemberAsync(member);
      return dbMember;
    } catch (error) {
      return rejectWithValue(constructAlertError(error as ResponseError));
    }
  }
);
