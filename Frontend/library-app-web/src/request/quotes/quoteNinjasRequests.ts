import axios from "axios";
import DailyQuote from "../../models/quotes/DailyQuote";

const API_NINJAS_KEY = "/hrjksxFJXjVbomgxHTyZg==0sWag6FkWqVjTp4J";

const API_NINJAS_QUOTES_ART_URL =
  "https://api.api-ninjas.com/v1/quotes?category=art";

export async function getDailyQuoteAsync(): Promise<DailyQuote> {
  const response = await axios.get(API_NINJAS_QUOTES_ART_URL, {
    headers: { "X-Api-Key": API_NINJAS_KEY },
  });
  const { quote, author } = response.data[0];
  return { quote, author };
}

// PRIVREMENO DOK SE NE PODIGNE OPET NINJAS API
// export async function getDailyQuoteAsync(): Promise<DailyQuote> {
//   return {
//     quote:
//       "Whoever wants music instead of noise, joy instead of pleasure, soul instead of gold, creative work instead of business, passion instead of foolery, finds no home in this trivial world of ours.",
//     author: "Hermann Hesse",
//   };
// }
