import BackgroundImage from "../home/BackgroundImage";
import MemberAddForm from "./MemberAddForm";

export default function MemberAddPage() {
  return (
    <BackgroundImage>
      <div className="flex items-center justify-center">
        <div className="w-2/4">
          <MemberAddForm />
        </div>
      </div>
    </BackgroundImage>
  );
}
