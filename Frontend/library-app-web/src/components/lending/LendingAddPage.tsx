import BackgroundImage from "../home/BackgroundImage";
import LendingAddForm from "./LendingAddForm";

export default function LendingAddPage() {
  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        <div className="w-2/4">
          <LendingAddForm />
        </div>
      </div>
    </BackgroundImage>
  );
}
