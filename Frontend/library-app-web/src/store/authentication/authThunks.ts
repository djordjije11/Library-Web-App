import { createAsyncThunk } from "@reduxjs/toolkit";
import LoginInput from "../../models/authentication/LoginInput";
import { loginAsync } from "../../request/authentication/authRequests";
import { authActions } from "./authSlice";
import ResponseError from "../../request/ResponseError";
import { alertActions } from "../alert/alertSlice";
import { constructAlertError } from "../../models/alert/AlertError";

export const loginAsyncThunk = createAsyncThunk("auth/loginAsync", async (loginInput : LoginInput, { dispatch, rejectWithValue }) => {
    try{
        const authToken : string = await loginAsync(loginInput);
        dispatch(authActions.loggedIn(authToken));
    } catch(error){
        const responseError = error as ResponseError;
        dispatch(alertActions.error(constructAlertError(responseError)));
    }
});