import AuthClaims from "../../models/authentication/claims/AuthClaims";

export default interface AuthState {
    loggedIn: boolean,
    authClaims?: AuthClaims
}