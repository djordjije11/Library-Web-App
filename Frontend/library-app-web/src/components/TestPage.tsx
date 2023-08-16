import { Card } from "@material-tailwind/react";
import { BookCopyStatus } from "../models/bookcopy/BookCopyStatus";
import BookCopyCard from "./bookcopy/BookCopyCard";
import BookCopyTable from "./bookcopy/BookCopyTable";

export default function TestPage() {
  return (
    <div style={{ width: "100%" }}>
      <div className="flex justify-start flex-wrap">
        {Array.from(Array(10).keys()).map((i) => (
          <div className="flex">
            <BookCopyCard
              onClose={() => {}}
              key={i}
              bookCopy={{
                rowVersion: 1,
                id: 1,
                isbn: "1252362733",
                book: {
                  id: 1,
                  title: "Step",
                  authors: "Herman Stamina",
                  publisher: { name: "KLET" },
                },
                status: BookCopyStatus.AVAILABLE,
              }}
              color="cyan"
            />
            <BookCopyCard
              onClose={() => {}}
              key={i}
              bookCopy={{
                rowVersion: 1,
                id: 1,
                isbn: "1252362733",
                book: {
                  id: 1,
                  title: "sdagdsa",
                  authors: " Freemen, Stamina",
                  publisher: { name: "KLET" },
                },
                status: BookCopyStatus.AVAILABLE,
              }}
              color="cyan"
            />
          </div>
        ))}
      </div>
      <Card>
        <BookCopyTable />
      </Card>
    </div>
  );
}
