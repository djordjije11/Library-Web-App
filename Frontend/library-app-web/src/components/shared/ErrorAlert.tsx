import {
  ExclamationTriangleIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";
import { Alert, Button } from "@material-tailwind/react";
import { useEffect } from "react";

export interface ErrorAlertProps {
  isOpen: boolean;
  setIsOpen: (value: React.SetStateAction<boolean>) => void;
  message: string;
}

export default function ErrorAlert(props: ErrorAlertProps) {
  const { isOpen, setIsOpen, message } = props;

  useEffect(() => {
    if (isOpen) {
      //setTimeout(() => setIsOpen(false), 3000);
    }
  }, [isOpen]);

  return (
    <Alert
      color="red"
      variant="gradient"
      open={isOpen}
      icon={<ExclamationTriangleIcon />}
      action={
        <Button
          variant="text"
          color="white"
          size="sm"
          className="!absolute top-3 right-3"
          onClick={() => setIsOpen(false)}
        >
          <XMarkIcon width="18px" />
        </Button>
      }
    >
      {message || "Error!"}
    </Alert>
  );
}
