import bookWorld from "../../images/book-world.jpg";

export default function LoginBackgroundImage() {
  return (
    <img
      src={bookWorld}
      alt="Background Image"
      style={{
        borderRadius: "10px 10px",
        boxShadow: "10px 30px 40px 10px rgba(0,0,0,.8)",
      }}
    />
  );
}
