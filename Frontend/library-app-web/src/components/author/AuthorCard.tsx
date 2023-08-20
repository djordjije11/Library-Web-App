import { MouseEventHandler } from "react";
import AuthorShort from "../../models/author/AuthorShort";
import { color } from "@material-tailwind/react/types/components/alert";
import ItemCard from "../shared/card/ItemCard";

export interface AuthorCardProps {
  author: AuthorShort;
  onClose: MouseEventHandler<HTMLButtonElement>;
  color?: color;
}

export default function AuthorCard(props: AuthorCardProps) {
  const { author, onClose, color } = props;

  return (
    <ItemCard
      titleRendered={
        <span>
          {author.firstname} {author.lastname}
        </span>
      }
      bodyRendered={<></>}
      onClose={onClose}
      color={color}
    />
  );
}
