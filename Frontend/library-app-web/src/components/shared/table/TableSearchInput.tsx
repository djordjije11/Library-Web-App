import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import { FormEvent } from "react";

export interface TableSearchInputProps {
  search: string;
  onSearchChange: React.ChangeEventHandler<HTMLInputElement>;
}

export default function TableSearchInput(props: TableSearchInputProps) {
  const { search, onSearchChange } = props;

  return (
    <form
      className="max-w-xs w-full px-2"
      onSubmit={(event: FormEvent) => event.preventDefault()}
    >
      <div className="relative">
        <MagnifyingGlassIcon
          className="absolute top-0 bottom-0 my-auto text-gray-400 left-3"
          width={"20px"}
        />
        <input
          type="text"
          placeholder="Search"
          className="w-full py-2 pl-12 pr-4 text-gray-500 border border-gray-400 rounded-md outline-none bg-gray-50 focus:bg-white focus:border-gray-600"
          value={search}
          onChange={onSearchChange}
        />
      </div>
    </form>
  );
}
