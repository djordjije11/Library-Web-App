import axios from "axios";
import DailyQuote from "../../models/quotes/DailyQuote";

const API_NINJAS_KEY = "TLdzL+QgPRIeckCu8M0MAg==2qejAWFEc100apFN";

const API_NINJAS_QUOTES_ART_URL =
  "https://api.api-ninjas.com/v1/quotes?category=art";

export async function getDailyQuoteAsync(): Promise<DailyQuote> {
  const response = await axios.get(API_NINJAS_QUOTES_ART_URL, {
    headers: { "X-Api-Key": API_NINJAS_KEY },
  });
  const { quote, author } = response.data[0];
  return { quote, author };
}
