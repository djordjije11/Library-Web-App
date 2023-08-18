import BackgroundImage from "../home/BackgroundImage";
import LendingReturnForm from "./LendingReturnForm";

export default function LendingReturnPage() {
  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        <div className="w-2/4">
          <LendingReturnForm />
        </div>
      </div>
    </BackgroundImage>
  );
}
