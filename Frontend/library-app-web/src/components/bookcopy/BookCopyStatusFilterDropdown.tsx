import Select from "react-select";
import { BookCopyStatus } from "../../models/bookcopy/BookCopyStatus";
import { useMemo } from "react";
import classNames from "classnames";

export interface BookCopyStatusFilterDropdownProps {
  status?: BookCopyStatus;
  onChange: (status?: BookCopyStatus) => void;
}

interface SelectOption {
  value: BookCopyStatus;
  label: string;
}

export default function BookCopyStatusFilterDropdown(
  props: BookCopyStatusFilterDropdownProps
) {
  const { status, onChange } = props;
  const options: SelectOption[] = [
    { value: BookCopyStatus.AVAILABLE, label: BookCopyStatus.AVAILABLE },
    { value: BookCopyStatus.LENT, label: BookCopyStatus.LENT },
    { value: BookCopyStatus.LOST, label: BookCopyStatus.LOST },
  ];

  function getOption(status?: BookCopyStatus): SelectOption | undefined {
    if (status === undefined) {
      return undefined;
    }
    return options.find((option) => option.value === status);
  }

  const selectedOption = useMemo(() => getOption(status), [status]);

  return (
    <div className="hover:cursor-pointer">
      <Select
        className="basic-single"
        classNamePrefix={"select"}
        isClearable={true}
        name="book-copy-status"
        options={options}
        isDisabled={false}
        isLoading={false}
        isRtl={false}
        isSearchable={false}
        value={selectedOption}
        placeholder="Filter by status"
        onChange={(option) => onChange(option?.value)}
        classNames={{
          control: () =>
            classNames("hover:cursor-pointer hover:bg-blue-gray-50"),
          option: () =>
            classNames("hover:cursor-pointer hover:bg-blue-gray-200"),
        }}
        theme={(theme) => ({
          ...theme,
          borderRadius: 0,
          colors: {
            ...theme.colors,
            primary25: "white",
            primary: "#708090",
          },
        })}
      />
    </div>
  );
}
