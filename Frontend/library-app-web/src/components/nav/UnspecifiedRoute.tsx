import { useAppSelector } from "../../store/config/hooks";
import { Navigate } from "react-router-dom";
import { HOME_PAGE, LOGIN_PAGE } from "./routesUrls";

export default function UnspecifiedRoute() {
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);

  return userLoggedIn ? (
    <Navigate to={HOME_PAGE} />
  ) : (
    <Navigate to={LOGIN_PAGE} />
  );
}
