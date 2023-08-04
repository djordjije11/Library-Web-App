import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import AlertState from "./AlertState";
import AlertError from "../../models/error/AlertError";

const initialState : AlertState = {
    show: false
};

const alertSlice = createSlice({
    name: "alert",
    initialState,
    reducers: {
        error: (state, action : PayloadAction<AlertError>) => {
            state.error = action.payload;
            state.show = true;
        },
        dismiss: state => {
            state.show = false;
            state.error = undefined;
        }
    }
})

export default alertSlice.reducer;
export const alertActions = alertSlice.actions;