import { createSlice } from "@reduxjs/toolkit";
import MemberAddState from "./MemberAddState";
import { addMemberAsyncThunk } from "./memberAddThunks";

const initialState: MemberAddState = {} as MemberAddState;

const memberAddSlice = createSlice({
  name: "memberAdd",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(addMemberAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(addMemberAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
    builder.addCase(addMemberAsyncThunk.fulfilled, (state, action) => {
      state.member = action.payload;
      state.loading = false;
    });
  },
});

export default memberAddSlice.reducer;
export const memberAddActions = memberAddSlice.actions;
