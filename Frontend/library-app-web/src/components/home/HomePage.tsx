import DailyQuoteParagraph from "./DailyQuoteParagraph";
import BackgroundImage from "./BackgroundImage";

export default function HomePage() {
  return (
    <BackgroundImage>
      <div className="h-full flex justify-center items-center">
        <DailyQuoteParagraph />
      </div>
    </BackgroundImage>
  );
}
