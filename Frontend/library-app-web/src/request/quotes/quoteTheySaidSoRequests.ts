import axios from "axios";
import DailyQuote from "../../models/quotes/DailyQuote";

const API_THEY_SAID_SO_KEY = "MVxGoURUWERdglrQVimisnuARQ5tb5hv31TfwAZy";

const API_THEY_SAID_SO_QUOTES_ART_URL =
  "https://quotes.rest/qod?category=art&language=en";

export async function getDailyQuoteAsync(): Promise<DailyQuote> {
  const response = await axios.get(API_THEY_SAID_SO_QUOTES_ART_URL, {
    headers: {
      "Content-Type": "application/json",
      "X-Theysaidso-Api-Secret": API_THEY_SAID_SO_KEY,
    },
  });
  const { quote, author } = response.data.contents.quotes[0];
  return { quote, author };
}
