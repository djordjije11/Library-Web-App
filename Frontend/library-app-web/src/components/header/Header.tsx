import { useAppSelector } from "../../store/config/hooks";
import AuthenticatedNavBar from "./authenticated-nav/AuthenticatedNavBar";
import UnauthenticatedNavBar from "./unauthenticated-nav/UnauthenticatedNavBar";

export default function Header() {
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);

  if (userLoggedIn == false) {
    return <UnauthenticatedNavBar />;
  }

  return <AuthenticatedNavBar />;
}
