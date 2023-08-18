import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import BackgroundImage from "../home/BackgroundImage";
import { Card } from "@material-tailwind/react";
import BookCopiesTable from "./BookCopiesTable";
import { getBookAsyncThunk } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesThunks";
import BookCopiesState from "../../store/bookcopy/table/by-book-all-buildings/BookCopiesState";
import Loader from "../shared/Loader";
import { HOME_PAGE } from "../routes/AppRouter";
import BookCopyStatusFilterDropdown from "./BookCopyStatusFilterDropdown";
import { bookCopiesActions } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesSlice";

export function BookCopiesListPage() {
  const { id } = useParams();
  const bookCopiesState: BookCopiesState = useAppSelector(
    (state) => state.bookCopies
  );
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        await dispatch(getBookAsyncThunk(Number(id))).unwrap();
      } catch (error) {
        navigate(HOME_PAGE);
      }
    })();
  }, []);

  function renderHeaderChildren(searchInputField: JSX.Element): JSX.Element {
    return (
      <div className="mt-2 flex justify-start items-center gap-2">
        {searchInputField}
        <BookCopyStatusFilterDropdown
          status={bookCopiesState.status}
          onChange={(bookCopyStatus) =>
            dispatch(bookCopiesActions.setBookCopyStatus(bookCopyStatus))
          }
        />
      </div>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        {bookCopiesState.book === undefined ? (
          <Loader />
        ) : (
          <div className="w-7/12 my-4 min-w-min h-3/4">
            <Card className="h-full w-full">
              <div className="flex justify-center items-center font-bold text-lg m-4">
                {bookCopiesState.book.title}
              </div>
              {/* <BookInfo
                book={bookCopiesState.book}
                availableCopiesInBuildingCount={
                  bookCopiesState.availableCopiesInBuildingCount
                }
              /> */}
              <BookCopiesTable renderHeaderChildren={renderHeaderChildren} />
            </Card>
          </div>
        )}
      </div>
    </BackgroundImage>
  );
}
