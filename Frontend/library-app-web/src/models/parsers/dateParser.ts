export function convertJsonDateToDate(date?: string): Date | undefined {
  if (date === undefined || date === null) {
    return undefined;
  }
  const [year, month, day]: string[] = date.split("-");
  return new Date(Number(year), Number(month) - 1, Number(day));
}

export function convertDateToJsonDate(date: Date): string {
  const dateFix = new Date(
    date.getFullYear(),
    date.getMonth(),
    date.getDate() + 1
  );
  return dateFix.toISOString().split("T")[0];
}
