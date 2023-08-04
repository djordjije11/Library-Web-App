import AlertState from "../store/alert/AlertState";
import { useAppSelector } from "../store/config/hooks";

export default function Alert() {
  const alertState: AlertState = useAppSelector((state) => state.alert);

  if (alertState.show === false) {
    return <></>;
  }

  console.log(alertState.error?.statusCode);
  console.log(alertState.error?.error);
  return (
    <div>
      <br />
      <br />
      ERORRRRRRR
      <br />
      <br />
    </div>
  );
}
