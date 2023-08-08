import Alert from "../Alert";
import LoginForm from "./LoginForm";
import LoginBackgroundImage from "./LoginBackgroundImage";

export default function LoginPage() {
  return (
    <>
      {/* <Alert /> */}
      <div className="flex justify-evenly items-center h-full">
        <LoginForm />
        {/* <LoginBackgroundImage /> */}
      </div>
    </>
  );
}
