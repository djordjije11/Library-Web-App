import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import AuthState from "./AuthState";
import AuthClaims from "../../models/authentication/claims/AuthClaims";
import { loginAsyncThunk } from "./authThunks";

const initialState: AuthState = {
  loggedIn: false,
  isError: false,
  loading: false,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loggedIn: (state, action: PayloadAction<AuthClaims>) => {
      state.loggedIn = true;
      state.authClaims = action.payload;
      state.isError = false;
      state.error = undefined;
    },
    loggedOut: (state) => {
      state.loggedIn = false;
      state.authClaims = undefined;
      state.isError = false;
      state.error = undefined;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(loginAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(loginAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
    builder.addCase(loginAsyncThunk.fulfilled, (state, action) => {
      authSlice.caseReducers.loggedIn(state, action);
      state.loading = false;
    });
  },
});

export default authSlice.reducer;
export const authActions = authSlice.actions;
