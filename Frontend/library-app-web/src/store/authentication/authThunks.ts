import { createAsyncThunk } from "@reduxjs/toolkit";
import LoginInput from "../../models/authentication/LoginInput";
import { loginAsync } from "../../request/authentication/authRequests";
import { authActions } from "./authSlice";
import ResponseError from "../../request/ResponseError";
import AlertError, { constructAlertError } from "../../models/error/AlertError";
import {
  decodeAndSaveAuthToken,
  getDecodedAuthToken,
  removeAuthToken,
} from "../../services/authentication/authTokenService";
import { AppThunk } from "../config/store";
import { ErrorType } from "../../models/error/ErrorType";
import AuthClaims from "../../models/authentication/claims/AuthClaims";

export const loginAsyncThunk = createAsyncThunk<
  AuthClaims,
  LoginInput,
  { rejectValue: AlertError }
>("auth/loginAsync", async (loginInput: LoginInput, { rejectWithValue }) => {
  try {
    const authToken: string = await loginAsync(loginInput);
    const authClaims = decodeAndSaveAuthToken(authToken);
    if (authClaims === null) {
      return rejectWithValue({ error: ErrorType.SYSTEM_ERROR });
    }
    return authClaims;
  } catch (error) {
    return rejectWithValue(constructAlertError(error as ResponseError));
  }
});

export const loginThunk = (): AppThunk => (dispatch, getState) => {
  const authClaims = getDecodedAuthToken();
  if (authClaims === null) {
    return;
  }
  dispatch(authActions.loggedIn(authClaims));
};

export const logoutThunk = (): AppThunk => (dispatch, getState) => {
  removeAuthToken();
  dispatch(authActions.loggedOut());
};
