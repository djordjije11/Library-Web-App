import { PayloadAction, createSlice } from "@reduxjs/toolkit"
import { decodeAuthToken, removeAuthToken, saveAuthToken } from "../../services/authentication/authTokenService"
import AuthState from "./AuthState"

const initialState: AuthState = {
    loggedIn: false
}

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        loggedIn: (state, action: PayloadAction<string>) => {
            const authClaims = decodeAuthToken(action.payload);
            if(authClaims === null) {
                return;
            }
            saveAuthToken(action.payload);
            state.loggedIn = true;
            state.authClaims = authClaims;
            // state = {
            //     loggedIn: true,
            //     employee: action.payload.employeeClaim,
            //     building: action.payload.buildingClaim
            // } as AuthUser;
        },
        loggedOut: (state) =>{
            removeAuthToken();
            state.loggedIn = false;
            state.authClaims = undefined;
            // state = {
            //     loggedIn: false,
            // } as AuthUser;
        }
    }
});


export default authSlice.reducer;
export const authActions = authSlice.actions;