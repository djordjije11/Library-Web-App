import {
  Button,
  Card,
  CardBody,
  CardFooter,
  Textarea,
} from "@material-tailwind/react";
import { Book } from "../../models/book/Book";
import { FormEvent, useEffect, useState } from "react";
import { successAlert } from "../../services/alert/successHandler";
import FormField from "../shared/form/FormField";
import BookSaveInputResults from "../../models/book/BookSaveInputResults";
import { validateRequired } from "../../validation/modelValidations";
import { allValid } from "../../models/validation/ValidationResult";
import FormInput from "../shared/form/FormInput";
import ModalSelectPublisher from "../publisher/ModalSelectPublisher";
import PublisherShort from "../../models/publisher/PublisherShort";
import FormTableSelect from "../shared/form/FormTableSelect";
import AuthorShort from "../../models/author/AuthorShort";
import SelectAuthorsField from "../author/SelectAuthorsField";
import ModalSelectAuthor from "../author/ModalSelectAuthor";
import { constructAlertError } from "../../models/error/AlertError";
import ResponseError from "../../request/ResponseError";
import { handleBookFormError } from "../../services/alert/errorHandler";

export interface BookFormProps {
  book?: Book;
  onSubmitAsync: (book: Book) => Promise<Book>;
  clearOnSubmit: boolean;
  formHeader: JSX.Element;
}

export default function BookForm(props: BookFormProps) {
  const { clearOnSubmit, formHeader, onSubmitAsync } = props;
  const initialBook = { description: "", authors: [] as AuthorShort[] } as Book;
  const [bookInput, setBookInput] = useState<Book>(props.book || initialBook);
  const [bookInputResults, setBookInputResults] =
    useState<BookSaveInputResults>({} as BookSaveInputResults);
  const [showPublisherModal, setShowPublisherModal] = useState<boolean>(false);
  const [showAuthorsModal, setShowAuthorsModal] = useState<boolean>(false);

  function handlePropertyChanged(
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) {
    setBookInput((prev) => {
      return {
        ...prev,
        [event.target.name]: event.target.value,
      } as Book;
    });
  }

  useEffect(() => {
    setBookInput((prev) => ({ ...prev, ...props.book }));
  }, [props.book]);

  function validateForm(): boolean {
    const titleResult = validateRequired("Title", bookInput.title);
    setBookInputResults((prev) => ({ ...prev, titleResult }));
    return allValid(titleResult);
  }

  function clearFormFields() {
    setBookInput(initialBook);
  }

  async function handleSubmitAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    try {
      await onSubmitAsync(bookInput);
      if (clearOnSubmit) {
        clearFormFields();
      }
      successAlert();
    } catch (error) {
      handleBookFormError(constructAlertError(error as ResponseError));
    }
  }

  function handleSelectedPublisher(publisher: PublisherShort) {
    setBookInput((prev) => ({ ...prev, publisher: publisher }));
    setShowPublisherModal(false);
  }

  function handleSelectedAuthor(author: AuthorShort) {
    setShowAuthorsModal(false);
    if (bookInput.authors.some((a) => a.id === author.id)) {
      return;
    }
    setBookInput((prev) => ({
      ...prev,
      authors: [...prev.authors, author],
    }));
  }

  function handleRemoveAuthor(author: AuthorShort) {
    setBookInput((prev) => ({
      ...prev,
      authors: prev.authors.filter((a) => a.id !== author.id),
    }));
  }

  return (
    <>
      <ModalSelectPublisher
        show={showPublisherModal}
        close={() => setShowPublisherModal(false)}
        handleSelectedPublisher={handleSelectedPublisher}
      />
      <ModalSelectAuthor
        show={showAuthorsModal}
        close={() => setShowAuthorsModal(false)}
        handleSelectedAuthor={handleSelectedAuthor}
      />
      <Card className="w-full bg-blue-gray-50">
        <form onSubmit={handleSubmitAsync} noValidate autoComplete="off">
          <CardBody>
            <div>{formHeader}</div>
            <FormField
              name="title"
              label="Title"
              result={bookInputResults.titleResult}
            >
              <FormInput
                name="title"
                type="text"
                value={bookInput.title}
                onChange={handlePropertyChanged}
              />
            </FormField>
            <FormField name="description" label="Description">
              <Textarea
                name="description"
                size="lg"
                label="About"
                color="blue-gray"
                className="bg-white max-w-full"
                variant="outlined"
                value={bookInput.description}
                onChange={handlePropertyChanged}
                containerProps={{ className: "min-w-0" }}
              />
            </FormField>
            <FormField name="pagesNumber" label="Number of pages">
              <FormInput
                name="pagesNumber"
                type="number"
                value={bookInput.pagesNumber}
                onChange={handlePropertyChanged}
              />
            </FormField>
            <FormField name="authors" label="Author">
              <SelectAuthorsField
                authors={bookInput.authors}
                handleRemoveAuthor={handleRemoveAuthor}
                onSelectAuthorClick={() => setShowAuthorsModal(true)}
              />
            </FormField>
            <FormField name="publisher" label="Publisher">
              <FormTableSelect
                isSelected={bookInput.publisher !== undefined}
                onSelectClick={() => setShowPublisherModal(true)}
                selectedItemText={
                  bookInput.publisher !== undefined
                    ? bookInput.publisher.name
                    : ""
                }
                selectButtonText="Select a publisher"
              />
            </FormField>
          </CardBody>
          <CardFooter className="pt-0 flex justify-start items-center gap-2">
            <Button type="submit" color="blue-gray">
              Submit
            </Button>
          </CardFooter>
        </form>
      </Card>
    </>
  );
}
