import { Button } from "@material-tailwind/react";
import Members from "../components/Members";
import Logout from "../components/nav/Logout";
import { NavBarComponent } from "../components/nav/NavBarComponent";
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
      <NavBarComponent />
      <Button>Button</Button>
      <div>
        {`${authClaims.employeeClaim.firstname} ${authClaims.employeeClaim.lastname}`}
      </div>
      <div>{`${authClaims.buildingClaim.street}, ${authClaims.buildingClaim.city}`}</div>
      <br />
      <Members />
      <br />
      <Logout />
    </div>
  );
}
