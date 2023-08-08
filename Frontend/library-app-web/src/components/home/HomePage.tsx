import DailyQuoteParagraph from "./DailyQuoteParagraph";
import homePageBackground from "../../images/home-page-background-3.jpg";

export default function HomePage() {
  return (
    <div
      style={{
        backgroundImage: `url(${homePageBackground})`,
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        opacity: 0.92,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <DailyQuoteParagraph />
    </div>
  );
}
