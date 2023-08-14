import { useEffect, useState } from "react";
import DailyQuote from "../../models/quotes/DailyQuote";
import { getDailyQuoteAsync } from "../../request/quotes/quoteNinjasRequests";
import Loader from "../shared/Loader";

export default function DailyQuoteParagraph() {
  const [dailyQuote, setDailyQuote] = useState<DailyQuote>();
  const [loading, setLoading] = useState<boolean>();

  useEffect(() => {
    (async () => {
      setLoading(true);
      try {
        const quoteFromApi = await getDailyQuoteAsync();
        setDailyQuote(quoteFromApi);
      } catch (error) {
        return;
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (loading) {
    return <Loader />;
  }

  if (dailyQuote === undefined) {
    return <></>;
  }

  return (
    <div className="flex flex-col items-center bg-white border border-gray-200 rounded-lg shadow md:flex-row md:max-w-xl hover:bg-gray-100 dark:border-gray-700 dark:bg-gray-800 dark:hover:bg-gray-700">
      <div className="flex flex-col justify-between p-4 leading-normal">
        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          Quote of the day
        </h5>
        <p className="mb-3 text-center text-gray-500 dark:text-gray-400 italic">
          <span>"{dailyQuote?.quote}"</span>
        </p>
        <p className="mb-3 text-center text-gray-700 dark:text-gray-400 italic">
          <span>- {dailyQuote?.author}</span>
        </p>
      </div>
    </div>
  );
}
