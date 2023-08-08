import "./App.css";
import { useAppDispatch } from "./store/config/hooks";
import { loginThunk } from "./store/authentication/authThunks";
import AppRouter from "./components/routes/AppRouter";
import { BrowserRouter } from "react-router-dom";
import Header from "./components/header/Header";
import Footer from "./components/footer/Footer";

function App() {
  const dispatch = useAppDispatch();

  dispatch(loginThunk());

  return (
    <BrowserRouter>
      {/* <Loader /> */}
      <div
        className="min-h-screen"
        style={{
          display: "grid",
          gridAutoFlow: "row",
          gridTemplateRows: "8% auto 8%",
        }}
      >
        <Header />
        <AppRouter />
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
