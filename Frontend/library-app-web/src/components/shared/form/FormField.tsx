import { AnimatePresence, motion } from "framer-motion";
import { ExclamationCircleIcon } from "@heroicons/react/24/outline";
import ValidationResult from "../../../models/validation/ValidationResult";

export interface FormFieldProps {
  name: string;
  label?: string;
  result?: ValidationResult;
  children: JSX.Element;
}

export default function FormField(props: FormFieldProps) {
  const { name, label, result } = props;

  function renderLabel(): JSX.Element {
    return (
      <label
        htmlFor={name}
        className="font-semibold capitalize"
        style={{ visibility: label === undefined ? "hidden" : "visible" }}
      >
        {label || "Input field"}
      </label>
    );
  }

  function renderError(): JSX.Element {
    if (result === undefined || result.isValid) {
      return <></>;
    }
    return <InputError message={result.error} key={`error-${name}`} />;
  }

  return (
    <div className="flex flex-col w-full gap-2">
      <div className="flex justify-between">
        {renderLabel()}
        <AnimatePresence mode="wait" initial={false}>
          {renderError()}
        </AnimatePresence>
      </div>
      {props.children}
    </div>
  );
}

const InputError = ({ message }: { message?: string }) => {
  return (
    <motion.p
      className="flex items-center gap-1 px-2 font-semibold text-red-500 bg-red-100 rounded-md"
      {...framer_error}
    >
      <ExclamationCircleIcon height={"16px"} color="red" />
      {message}
    </motion.p>
  );
};

const framer_error = {
  initial: { opacity: 0, y: 10 },
  animate: { opacity: 1, y: 0 },
  exit: { opacity: 0, y: 10 },
  transition: { duration: 0.2 },
};
