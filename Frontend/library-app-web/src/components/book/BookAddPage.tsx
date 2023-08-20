import { Book } from "../../models/book/Book";
import { constructBookSave } from "../../models/book/BookSave";
import { addBookAsync } from "../../request/book/bookRequests";
import BackgroundImage from "../home/BackgroundImage";
import BookForm from "./BookForm";

export default function BookAddPage() {
  async function onSubmitAsync(book: Book): Promise<Book> {
    return await addBookAsync(constructBookSave(book));
  }

  function BookAddFormHeader(): JSX.Element {
    return (
      <div className="flex justify-center font-bold">
        <h3>Add a new book</h3>
      </div>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        <div className="w-2/4">
          <BookForm
            onSubmitAsync={onSubmitAsync}
            clearOnSubmit={true}
            formHeader={<BookAddFormHeader />}
          />
        </div>
      </div>
    </BackgroundImage>
  );
}
