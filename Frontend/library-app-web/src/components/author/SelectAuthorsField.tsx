import AuthorShort from "../../models/author/AuthorShort";
import FormTableSelectMultiple from "../shared/form/FormTableSelectMultiple";
import AuthorCard from "./AuthorCard";

export interface SelectAuthorsFieldProps {
  authors: AuthorShort[];
  onSelectAuthorClick: () => void;
  handleRemoveAuthor: (author: AuthorShort) => void;
}

export default function SelectAuthorsField(props: SelectAuthorsFieldProps) {
  const { authors, handleRemoveAuthor, onSelectAuthorClick } = props;

  return (
    <FormTableSelectMultiple
      isAnySelected={authors.length !== 0}
      onSelectClick={onSelectAuthorClick}
      selectButtonText="Select an author"
      selectedItemsRendered={
        <>
          {authors.map((author) => (
            <AuthorCard
              key={author.id}
              author={author}
              onClose={() => handleRemoveAuthor(author)}
            />
          ))}
        </>
      }
      containerMaxHeight="4.2rem"
    />
  );
}
