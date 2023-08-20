import AuthorShort from "../models/author/AuthorShort";
import { Book } from "../models/book/Book";
import { BookShort } from "../models/book/BookShort";
import { BookCopyDisplay } from "../models/bookcopy/BookCopyDisplay";
import AuthorCard from "./author/AuthorCard";
import BookForm from "./book/BookForm";
import BookCopyCard from "./bookcopy/BookCopyCard";

export default function TestPage() {
  return (
    <>
      <div>
        <AuthorCard
          author={{ firstname: "MACori", lastname: "SOKI" } as AuthorShort}
          onClose={() => {}}
        />
        <BookCopyCard
          bookCopy={
            {
              book: {
                id: 2,
                title: "Stepski vuk",
                authors: "Ovaj onaj omaj sozmaj",
                publisher: { name: "KLET" },
                pagesNumber: 200,
              } as BookShort,
              isbn: "2152501521",
            } as BookCopyDisplay
          }
          onClose={() => {}}
        />
      </div>

      <div className="w-full flex justify-center items-center">
        <div className="w-8/12 flex justify-center items-center">
          <BookForm
            onSubmitAsync={async (book) => {
              console.log(book);
              await Promise.resolve();
              return {} as Book;
            }}
            clearOnSubmit={false}
            formHeader={<></>}
          />
        </div>
      </div>
    </>
  );
}
