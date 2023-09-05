import { Chip } from "@material-tailwind/react";

export interface TablePageSizeSelectProps {
  pageSize: number;
  setPageSize: React.Dispatch<React.SetStateAction<number>>;
}

export function TablePageSizeSelect(props: TablePageSizeSelectProps) {
  const { pageSize, setPageSize } = props;

  function PageSizeOption({
    pageSizeOption,
  }: {
    pageSizeOption: number;
  }): JSX.Element {
    const checked = pageSize === pageSizeOption;
    return (
      <button onClick={() => setPageSize(pageSizeOption)}>
        <Chip
          variant={checked ? "filled" : "outlined"}
          value={pageSizeOption}
          className={`rounded-3xl border-gray-800 hover:bg-gray-400 hover:text-white hover:cursor-pointer p-0 w-10 h-4 ${
            checked ? "border" : ""
          }`}
          color="gray"
          size="sm"
        />
      </button>
    );
  }

  return (
    <div className="flex gap-2">
      <PageSizeOption pageSizeOption={5} />
      <PageSizeOption pageSizeOption={15} />
      <PageSizeOption pageSizeOption={30} />
    </div>
  );
}
