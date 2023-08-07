import { Button } from "@material-tailwind/react";
import { logoutThunk } from "../../store/authentication/authThunks";
import { useAppDispatch } from "../../store/config/hooks";

export default function Logout() {
  const dispatch = useAppDispatch();

  return (
    <Button
      variant="gradient"
      size="sm"
      className="hidden lg:inline-block"
      onClick={() => dispatch(logoutThunk())}
    >
      <span>Log out</span>
    </Button>
  );
}
