import "./App.css";
import { useAppDispatch } from "./store/config/hooks";
import { loginThunk } from "./store/authentication/authThunks";
import AppRouter from "./components/nav/AppRouter";
import { BrowserRouter } from "react-router-dom";

function App() {
  const dispatch = useAppDispatch();

  dispatch(loginThunk());

  return (
    <BrowserRouter>
      <AppRouter />;
    </BrowserRouter>
  );
}

export default App;
