import { ChangeEventHandler } from "react";

export interface FormInputProps {
  name: string;
  type: string;
  placeholder?: string;
  value: string;
  onChange: ChangeEventHandler<HTMLInputElement>;
}

export default function FormInput(props: FormInputProps) {
  const { type, name, placeholder, value, onChange } = props;

  return (
    <input
      id={name}
      name={name}
      type={type}
      value={value || ""}
      onChange={onChange}
      className="w-full p-3 font-medium border rounded-md border-slate-300 placeholder:opacity-60"
      placeholder={placeholder}
    />
  );
}
