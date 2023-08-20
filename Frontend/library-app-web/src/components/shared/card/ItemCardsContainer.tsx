export interface CardsContainer {
  maxHeight?: string;
  children: JSX.Element;
}

export default function ItemCardsContainer(props: CardsContainer) {
  const maxHeight = props.maxHeight || "13rem";
  return (
    <div
      style={{ width: "100%", maxHeight }}
      className="flex flex-wrap gap-2 overflow-y-auto p-3 border rounded-md border-slate-300 bg-white"
    >
      {props.children}
    </div>
  );
}
