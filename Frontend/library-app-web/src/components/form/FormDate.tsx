import {
  ArrowUturnLeftIcon,
  ArrowUturnRightIcon,
} from "@heroicons/react/24/solid";
import { useState } from "react";
import DatePicker from "tailwind-datepicker-react";

export interface FormDateProps {
  title: string;
  minDate?: Date;
  maxDate?: Date;
  defaultDate?: Date;
  onChange: (selectedDate: Date) => void;
}

export default function FormDate(props: FormDateProps) {
  const { title, minDate, maxDate, defaultDate, onChange } = props;
  const [show, setShow] = useState<boolean>(false);

  function getDefaultMinDate(): Date {
    var defaultMinDate = new Date();
    defaultMinDate.setDate(defaultMinDate.getDate() - 365 * 150);
    return defaultMinDate;
  }

  const options = {
    title,
    autoHide: true,
    todayBtn: false,
    clearBtn: true,
    maxDate: maxDate || new Date(),
    minDate: minDate || getDefaultMinDate(),
    theme: {
      background: "",
      todayBtn: "Today",
      clearBtn: "Clear",
      icons: "",
      text: "",
      disabledText: "",
      input: "",
      inputIcon: "",
      selected: "",
    },
    icons: {
      prev: () => <ArrowUturnLeftIcon height="20px" color="black" />,
      next: () => <ArrowUturnRightIcon height="20px" color="black" />,
    },
    datepickerClassNames: "top-12",
    defaultDate: defaultDate || maxDate || new Date(),
    language: "en",
  };

  const handleChange = (selectedDate: Date) => {
    onChange(selectedDate);
  };
  const handleClose = (state: boolean) => {
    setShow(state);
  };

  return (
    <div>
      <DatePicker
        options={options}
        onChange={handleChange}
        show={show}
        setShow={handleClose}
      />
    </div>
  );
}
