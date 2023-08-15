import Swal from "sweetalert2";

export async function questionAlertIsSureAsync(
  title: string,
  text: string
): Promise<boolean> {
  const { isConfirmed } = await Swal.fire({
    title,
    text,
    showDenyButton: true,
    confirmButtonText: "Yes",
    denyButtonText: `No`,
    confirmButtonColor: "green",
  });
  return isConfirmed;
}
