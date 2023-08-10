import homePageBackground from "../../images/home-page-background-3.jpg";

export default function BackgroundImage({
  children,
}: {
  children: JSX.Element;
}) {
  return (
    <div
      style={{
        backgroundImage: `url(${homePageBackground})`,
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        opacity: 0.92,
      }}
    >
      {children}
    </div>
  );
}
