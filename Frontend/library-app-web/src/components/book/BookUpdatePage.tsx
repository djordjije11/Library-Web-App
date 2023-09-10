import { useNavigate, useParams } from "react-router-dom";
import { Book } from "../../models/book/Book";
import { constructBookSave } from "../../models/book/BookSave";
import { getBookAsync, updateBookAsync } from "../../request/book/bookRequests";
import BackgroundImage from "../home/BackgroundImage";
import BookForm from "./BookForm";
import { useEffect, useMemo, useState } from "react";
import { HOME_PAGE } from "../routes/AppRouter";
import AuthorShort from "../../models/author/AuthorShort";
import { Card } from "@material-tailwind/react";
import GrayLoader from "../shared/loader/GrayLoader";

export default function BookUpdatePage() {
  const { id } = useParams();
  const bookId = Number(id);
  const navigate = useNavigate();
  const [bookUpdate, setBookUpdate] = useState<Book>({
    authors: [] as AuthorShort[],
  } as Book);
  const [loading, setLoading] = useState<boolean>(false);

  async function loadBook() {
    setLoading(true);
    try {
      const response = await getBookAsync(bookId);
      setBookUpdate(response.book);
    } catch (error) {
      navigate(HOME_PAGE);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadBook();
  }, []);

  async function onSubmitAsync(book: Book): Promise<Book> {
    const updatedBook = await updateBookAsync(constructBookSave(book));
    console.log(updatedBook);
    setBookUpdate(updatedBook);
    return updatedBook;
  }

  function BookUpdateFormHeader(): JSX.Element {
    return (
      <div className="flex justify-center font-bold">
        <h3>Edit a book</h3>
      </div>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        {loading ? (
          <div className="w-2/4 h-full">
            <Card
              style={{ height: "97%" }}
              className="w-full bg-blue-gray-50 my-3"
            >
              <div className="h-full flex justify-center items-center">
                <GrayLoader />
              </div>
            </Card>
          </div>
        ) : (
          <div className="w-2/4">
            <BookForm
              book={bookUpdate}
              onSubmitAsync={onSubmitAsync}
              clearOnSubmit={false}
              formHeader={<BookUpdateFormHeader />}
            />
          </div>
        )}
      </div>
    </BackgroundImage>
  );
}
