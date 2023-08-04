import AlertError from "../../models/error/AlertError";
import AuthClaims from "../../models/authentication/claims/AuthClaims";

export default interface AuthState {
  loggedIn: boolean;
  authClaims?: AuthClaims;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}
