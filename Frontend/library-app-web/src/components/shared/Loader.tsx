import { ProgressBar } from "react-loader-spinner";
import { useAppSelector } from "../../store/config/hooks";
import { Modal } from "@mui/material";

export default function Loader() {
  const loading: boolean = useAppSelector((state) => state.loader.loading);

  return (
    <Modal
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
      open={loading}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <ProgressBar
        height="100"
        width="100"
        ariaLabel="progress-bar-loading"
        wrapperStyle={{}}
        wrapperClass="progress-bar-wrapper"
        borderColor="#F4442E"
        barColor="#51E5FF"
      />
    </Modal>
  );
}
