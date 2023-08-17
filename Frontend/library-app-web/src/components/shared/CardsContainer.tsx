export interface CardsContainer {
  children: JSX.Element;
}

export default function CardsContainer({ children }: CardsContainer) {
  return (
    <div className="w-full max-h-52 flex flex-wrap gap-2 overflow-y-auto p-3 border rounded-md border-slate-300 bg-white">
      {children}
    </div>
  );
}
