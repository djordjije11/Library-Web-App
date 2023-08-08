import { Typography } from "@material-tailwind/react";
import { useAppSelector } from "../../store/config/hooks";
import AuthClaims from "../../models/authentication/claims/AuthClaims";

export default function Footer() {
  const currentYear: number = new Date().getFullYear();
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);
  const authClaims: AuthClaims = useAppSelector(
    (state) => state.auth.authClaims
  );

  function renderBuildingIfAuthenticated(): JSX.Element {
    if (userLoggedIn === false) {
      return <></>;
    }

    return (
      <Typography className="font-normal">
        {`${authClaims.buildingClaim.street} , ${authClaims.buildingClaim.city}`}
      </Typography>
    );
  }

  return (
    <div className="bg-gray-100 h-full flex items-center">
      <footer className="p-6 bg-gray-100 h-fit flex w-full flex-row flex-wrap items-center justify-center border-t border-blue-gray-50 py-4 text-center md:justify-between">
        <Typography className="font-normal">
          &copy; {currentYear} Library
        </Typography>
        {renderBuildingIfAuthenticated()}
      </footer>
    </div>
  );
}
