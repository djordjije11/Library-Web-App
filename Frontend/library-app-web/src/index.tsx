import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { Provider } from "react-redux";
import store from "./store/config/store";
import { ThemeProvider } from "@material-tailwind/react";

const strictMode: boolean = true;

function renderAppWithStrictModeConfig(): JSX.Element {
  if (strictMode) {
    return <React.StrictMode>{renderApp()}</React.StrictMode>;
  }
  return renderApp();
}

function renderApp(): JSX.Element {
  return (
    <ThemeProvider>
      <Provider store={store}>
        <App />
      </Provider>
    </ThemeProvider>
  );
}

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(renderAppWithStrictModeConfig());
