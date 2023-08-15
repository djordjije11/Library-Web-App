import "./App.css";
import { useAppDispatch } from "./store/config/hooks";
import { loginFromLocalThunk } from "./store/authentication/authThunks";
import AppRouter from "./components/routes/AppRouter";
import { BrowserRouter } from "react-router-dom";
import Header from "./components/header/Header";
import Footer from "./components/footer/Footer";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  const dispatch = useAppDispatch();

  dispatch(loginFromLocalThunk());

  return (
    <BrowserRouter>
      <ToastContainer
        position="top-right"
        autoClose={1000}
        hideProgressBar={true}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        theme="colored"
      />
      <div
        className="min-h-screen"
        style={{
          display: "grid",
          gridAutoFlow: "row",
          gridTemplateRows: "9% auto 8%",
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
