import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import MembersState from "./MembersState";
import { getMembersAsyncThunk } from "./membersThunks";
import MemberShort from "../../../models/member/MemberShort";
import RequestQueryParams from "../../../models/request/RequestQueryParams";

const initialState: MembersState = {
  members: [] as MemberShort[],
} as MembersState;

const membersSlice = createSlice({
  name: "members",
  initialState,
  reducers: {
    setRequestQueryParams: (
      state,
      action: PayloadAction<RequestQueryParams>
    ) => {
      state.requestQueryParams = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getMembersAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(getMembersAsyncThunk.fulfilled, (state, action) => {
      state.members = action.payload.members;
      state.totalPages = action.payload.totalPages;
      state.loading = false;
    });
    builder.addCase(getMembersAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
  },
});

export default membersSlice.reducer;
export const membersActions = membersSlice.actions;
