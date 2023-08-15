import { createSlice } from "@reduxjs/toolkit";
import MemberUpdateState from "./MemberUpdateState";
import {
  getMemberAsyncThunk,
  updateMemberAsyncThunk,
} from "./memberUpdateThunks";

const initialState: MemberUpdateState = {} as MemberUpdateState;

const memberUpdateSlice = createSlice({
  name: "memberUpdate",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getMemberAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(getMemberAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
    builder.addCase(getMemberAsyncThunk.fulfilled, (state, action) => {
      state.member = action.payload;
      state.loading = false;
    });
    builder.addCase(updateMemberAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(updateMemberAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
    builder.addCase(updateMemberAsyncThunk.fulfilled, (state, action) => {
      state.member = action.payload;
      state.loading = false;
    });
  },
});

export default memberUpdateSlice.reducer;
export const memberUpdateActions = memberUpdateSlice.actions;
