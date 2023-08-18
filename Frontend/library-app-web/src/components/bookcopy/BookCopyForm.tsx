import { FormEvent, useState } from "react";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import BookCopyInputResults from "../../models/bookcopy/BookCopyInputResults";
import { Button, Card, CardBody, CardFooter } from "@material-tailwind/react";
import FormField from "../shared/form/FormField";
import FormInput from "../shared/form/FormInput";
import { validateIsbn } from "../../validation/modelValidations";
import { allValid } from "../../models/validation/ValidationResult";
import { successAlert } from "../../services/alert/successHandler";
import { handleBookCopyFormError } from "../../services/alert/errorHandler";
import { constructAlertError } from "../../models/error/AlertError";
import ResponseError from "../../request/ResponseError";
import { Book } from "../../models/book/Book";

export interface BookCopyFormProps {
  book: Book;
  bookCopy?: BookCopyDisplay;
  onSubmitAsync: (bookCopy: BookCopyDisplay) => Promise<BookCopyDisplay>;
  clearOnSubmit: boolean;
  formHeader: JSX.Element;
}

export default function BookCopyForm(props: BookCopyFormProps) {
  const { book, onSubmitAsync, clearOnSubmit, formHeader } = props;
  const [bookCopy, setBookCopy] = useState<BookCopyDisplay>(
    props.bookCopy || ({} as BookCopyDisplay)
  );
  const [bookCopyInputResults, setBookCopyInputResults] =
    useState<BookCopyInputResults>({} as BookCopyInputResults);

  function validateForm(): boolean {
    const isbnResult = validateIsbn(bookCopy.isbn);
    setBookCopyInputResults((prev) => ({ ...prev, isbnResult }));
    return allValid(isbnResult);
  }

  function clearFormFields() {
    setBookCopy((prev) => ({ ...prev, isbn: "" }));
  }

  async function handleSubmitAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    try {
      await onSubmitAsync(bookCopy);
      if (clearOnSubmit) {
        clearFormFields();
      }
      successAlert();
    } catch (error) {
      handleBookCopyFormError(constructAlertError(error as ResponseError));
    }
  }

  return (
    <Card className="my-4 w-full bg-blue-gray-50">
      <form onSubmit={handleSubmitAsync} noValidate autoComplete="off">
        <CardBody>
          <div>{formHeader}</div>
          <div>Book: {book.title}</div>
          <FormField
            name="isbn"
            label="ISBN"
            result={bookCopyInputResults.isbnResult}
          >
            <FormInput
              name="isbn"
              type="text"
              value={bookCopy.isbn}
              onChange={(event) =>
                setBookCopy((prev) => ({ ...prev, isbn: event.target.value }))
              }
            />
          </FormField>
        </CardBody>
        <CardFooter className="pt-0 flex justify-start items-center gap-2">
          <Button type="submit">Submit</Button>
        </CardFooter>
      </form>
    </Card>
  );
}
