import { Button } from "@material-tailwind/react";
import ItemCardsContainer from "../card/ItemCardsContainer";

export interface FormTableSelectMultiple {
  isAnySelected: boolean;
  onSelectClick: () => void;
  selectButtonText: string;
  selectedItemsRendered: JSX.Element;
  containerMaxHeight?: string;
}

export default function FormTableSelectMultiple(
  props: FormTableSelectMultiple
) {
  const {
    isAnySelected,
    onSelectClick,
    selectButtonText,
    selectedItemsRendered,
    containerMaxHeight,
  } = props;

  function SelectButton(): JSX.Element {
    return (
      <Button onClick={onSelectClick} color="blue-gray">
        {selectButtonText}
      </Button>
    );
  }

  if (isAnySelected === false) {
    return <SelectButton />;
  }

  return (
    <>
      <SelectButton />
      <ItemCardsContainer maxHeight={containerMaxHeight}>
        {selectedItemsRendered}
      </ItemCardsContainer>
    </>
  );
}
