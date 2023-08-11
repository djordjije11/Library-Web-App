import Alert from "../Alert";
import LoginForm from "./LoginForm";
import LoginBackgroundImage from "./LoginBackgroundImage";
import useMediaQuery from "../shared/useMediaQuery";

export default function LoginPage() {
  const laptopScreenDisplay: boolean = useMediaQuery("(min-width: 1160px)");

  function renderLoginForm(): JSX.Element {
    if (laptopScreenDisplay) {
      return (
        <div
          style={{
            borderRadius: "10px 10px",
            boxShadow: "10px 30px 40px 10px rgba(0,0,0,.5)",
            padding: "42px",
          }}
        >
          <LoginForm />
        </div>
      );
    }

    return <LoginForm />;
  }

  function renderBackgroundImage(): JSX.Element {
    if (laptopScreenDisplay) {
      return <LoginBackgroundImage />;
    }
    return <></>;
  }

  return (
    <>
      {/* <Alert /> */}
      <div className="flex justify-center items-center h-full">
        <div
          className="flex justify-evenly items-stretch w-full"
          style={{ height: "364px" }}
        >
          {renderLoginForm()}
          {renderBackgroundImage()}
        </div>
      </div>
    </>
  );
}
