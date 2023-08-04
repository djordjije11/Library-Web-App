import Members from "../components/Members";
import AuthClaims from "../models/authentication/claims/AuthClaims";
import { authActions } from "../store/authentication/authSlice";
import { useAppDispatch, useAppSelector } from "../store/config/hooks";

export default function HomePage() {
  const authClaims: AuthClaims = useAppSelector(
    (state) => state.auth.authClaims
  );
  const dispatch = useAppDispatch();

  return (
    <div>
      <div>
        {`${authClaims.employeeClaim.firstname} ${authClaims.employeeClaim.lastname}`}
      </div>
      <div>{`${authClaims.buildingClaim.street}, ${authClaims.buildingClaim.city}`}</div>
      <br />
      <Members />
      <br />
      <button onClick={() => dispatch(authActions.loggedOut())}>LOGOUT</button>
    </div>
  );
}
