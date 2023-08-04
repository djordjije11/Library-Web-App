import { createSlice } from "@reduxjs/toolkit";
import MembersState from "./MembersState";
import { getMembersAsyncThunk } from "./membersThunks";
import Member from "../../models/member/Member";

const initialState : MembersState = {
    loading: false,
    members: [],
    error: ""
};

const membersSlice = createSlice({
    name: "members",
    initialState,
    reducers: {

    },
    extraReducers: builder => {
        builder.addCase(getMembersAsyncThunk.pending, state => {
            state.loading = true;
        });
        builder.addCase(getMembersAsyncThunk.fulfilled, (state, action) => {
            state.members = action.payload as Member[];
            state.loading = false;
        })
        builder.addCase(getMembersAsyncThunk.rejected, (state, action) => {
            state.error = "ERROR";
            state.loading = false;
        })
    }
})

export default membersSlice.reducer;