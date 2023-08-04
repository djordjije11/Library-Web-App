import { createAsyncThunk } from "@reduxjs/toolkit";
import Member from "../../models/member/Member";
import { getMembersAsync } from "../../request/member/memberRequests";

export const getMembersAsyncThunk = createAsyncThunk("members/getMembersAsync", async (_, { rejectWithValue }) : Promise<Member[] | unknown> => {
    return getMembersAsync();
})