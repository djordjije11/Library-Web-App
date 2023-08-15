import { Gender } from "../../models/enums/Gender";
import { ChangeEvent } from "react";

export interface GenderRadioGroupProps {
  value?: Gender;
  onChange: (gender: Gender) => void;
}

export default function GenderRadioGroup(props: GenderRadioGroupProps) {
  const { value, onChange } = props;

  function handleChange(event: ChangeEvent<HTMLInputElement>) {
    var gender: Gender;
    switch (event.currentTarget.value) {
      case String(Gender.MALE):
        gender = event.currentTarget.checked ? Gender.MALE : Gender.FEMALE;
        break;
      case String(Gender.FEMALE):
        gender = event.currentTarget.checked ? Gender.FEMALE : Gender.MALE;
        break;
      default:
        return;
    }
    onChange(gender);
  }

  return (
    <fieldset>
      <legend className="sr-only">Gender</legend>
      <div className="flex m-2 items-center mb-4 gap-4">
        <div className="flex items-center">
          <input
            id="gender-option-1"
            type="radio"
            name="gender"
            value={String(Gender.MALE)}
            className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600"
            checked={value === Gender.MALE}
            onChange={handleChange}
          />
          <label
            htmlFor="gender-option-1"
            className="block ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >
            Male
          </label>
        </div>
        <div className="flex items-center">
          <input
            id="gender-option-2"
            type="radio"
            name="gender"
            value={String(Gender.FEMALE)}
            className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600"
            checked={value === Gender.FEMALE}
            onChange={handleChange}
          />
          <label
            htmlFor="gender-option-2"
            className="block ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >
            Female
          </label>
        </div>
      </div>
    </fieldset>
  );
}
