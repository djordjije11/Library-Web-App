import { useAppSelector } from "../../store/config/hooks";
import { HOME_PAGE } from "./AppRouter";
import { Navigate } from "react-router-dom";

export default function UnauthenticatedRoute({
  children,
}: {
  children: JSX.Element;
}) {
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);

  if (userLoggedIn) {
    return <Navigate to={HOME_PAGE} />;
  }

  return children;
}
