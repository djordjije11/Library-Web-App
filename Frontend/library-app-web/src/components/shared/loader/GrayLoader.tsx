import { ProgressBar } from "react-loader-spinner";

export default function GrayLoader() {
  return (
    <ProgressBar
      width="100"
      ariaLabel="progress-bar-loading"
      wrapperStyle={{}}
      wrapperClass="progress-bar-wrapper"
      borderColor="white"
      barColor="gray"
    />
  );
}
