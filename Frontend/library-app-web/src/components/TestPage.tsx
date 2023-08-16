import { Card } from "@material-tailwind/react";
import { BookCopyStatus } from "../models/bookcopy/BookCopyStatus";
import BookCopyCard from "./bookcopy/BookCopyCard";
import BookCopyTable from "./bookcopy/BookCopyTable";

export default function TestPage() {
  return (
    <div style={{ width: "100%" }}>
      <div className="flex max-w-sm">
        {([1, 2, 3, 4, 5, 6, 7, 8, 9] as number[]).map((i) => (
          <BookCopyCard
            key={i}
            bookCopy={{
              rowVersion: 1,
              id: 1,
              isbn: "1252362733",
              book: {
                id: 1,
                title: "Stepski vuk",
                authors: "Herman Hese, Morgan Freemen, Stamina",
                publisher: { name: "KLET" },
              },
              status: BookCopyStatus.AVAILABLE,
            }}
            color="cyan"
          />
        ))}
      </div>
      <Card>
        <BookCopyTable />
      </Card>
    </div>
  );
}
