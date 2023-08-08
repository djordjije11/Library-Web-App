import { useAppSelector } from "../../store/config/hooks";
import { Navigate, useLocation, Location } from "react-router-dom";
import { LOGIN_PAGE } from "./AppRouter";

export interface LocationState {
  from: Location;
}

export default function AuthenticatedRoute({
  children,
}: {
  children: JSX.Element;
}) {
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);
  const location = useLocation();

  if (userLoggedIn === false) {
    const locationState: LocationState = {
      from: location,
    };
    return <Navigate to={LOGIN_PAGE} state={locationState} />;
  }

  return children;
}
