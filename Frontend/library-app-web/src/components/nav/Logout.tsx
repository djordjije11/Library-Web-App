import { logoutThunk } from "../../store/authentication/authThunks";
import { useAppDispatch } from "../../store/config/hooks";

export default function Logout() {
  const dispatch = useAppDispatch();
  return <button onClick={() => dispatch(logoutThunk())}>Log out</button>;
}
