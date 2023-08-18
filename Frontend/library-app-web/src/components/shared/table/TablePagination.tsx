import {
  ArrowLeftIcon,
  ArrowRightIcon,
  EllipsisHorizontalIcon,
} from "@heroicons/react/24/outline";
import { Button, IconButton } from "@material-tailwind/react";
import { Dispatch, SetStateAction, useCallback, useMemo } from "react";

export interface TablePaginationProps {
  currentPage: number;
  setCurrentPage: Dispatch<SetStateAction<number>>;
  pagesCount: number;
  totalItemsCount?: number;
  pageItemsAroundCurrentCount?: number;
  pageItemsOnEndsCount?: number;
}

export default function TablePagination(props: TablePaginationProps) {
  const { currentPage, setCurrentPage, pagesCount, totalItemsCount } = props;
  const pageItemsAroundCurrentCount = props.pageItemsAroundCurrentCount || 4;
  const pageItemsOnEndsCount = props.pageItemsOnEndsCount || 3;

  const disablePrevious = useMemo(() => currentPage === 1, [currentPage]);
  const disableNext = useMemo(
    () => currentPage === pagesCount,
    [currentPage, pagesCount]
  );

  function spaceExistsBetweenStartAndCurrentPageRange(): boolean {
    return currentPage - pageItemsAroundCurrentCount - pageItemsOnEndsCount > 1;
  }

  function spaceExistsBetweenEndAndCurrentPageRange(): boolean {
    return (
      currentPage + pageItemsAroundCurrentCount + pageItemsOnEndsCount <
      pagesCount
    );
  }

  const spaceExistsBetweenStartAndCurrentPageRangeMemo = useMemo(
    spaceExistsBetweenStartAndCurrentPageRange,
    [currentPage]
  );
  const spaceExistsBetweenEndAndCurrentPageRangeMemo = useMemo(
    spaceExistsBetweenEndAndCurrentPageRange,
    [currentPage, pagesCount]
  );

  function renderCurrentPageItem() {
    return (
      <IconButton
        className="rounded-full"
        color="gray"
        variant="filled"
        key={currentPage}
      >
        {currentPage}
      </IconButton>
    );
  }

  function renderPageItem(page: number): JSX.Element {
    return (
      <IconButton
        className="rounded-full"
        color="gray"
        variant="text"
        onClick={() => setCurrentPage(page)}
        key={page}
      >
        {page}
      </IconButton>
    );
  }

  function renderPageSpaceItem(): JSX.Element {
    return <EllipsisHorizontalIcon height={"18px"} />;
  }

  function renderPageItemsOnStart(): JSX.Element {
    const renders: JSX.Element[] = [];
    for (
      let pageIndex = 1,
        pageIndexMax = Math.min(
          pageItemsOnEndsCount + 1,
          currentPage - pageItemsAroundCurrentCount
        );
      pageIndex < pageIndexMax;
      pageIndex++
    ) {
      renders.push(renderPageItem(pageIndex));
    }
    return <>{renders}</>;
  }

  function renderPageItemsBeforeCurrentPage(): JSX.Element {
    const renders: JSX.Element[] = [];
    for (
      let pageIndex = currentPage - pageItemsAroundCurrentCount;
      pageIndex < currentPage;
      pageIndex++
    ) {
      if (pageIndex < 1) {
        continue;
      }
      renders.push(renderPageItem(pageIndex));
    }
    return <>{renders}</>;
  }

  function renderPageItemsAfterCurrentPage(): JSX.Element {
    const renders: JSX.Element[] = [];
    for (
      let pageIndex = currentPage + 1,
        pageIndexMax = Math.min(
          currentPage + pageItemsAroundCurrentCount,
          pagesCount
        );
      pageIndex <= pageIndexMax;
      pageIndex++
    ) {
      renders.push(renderPageItem(pageIndex));
    }
    return <>{renders}</>;
  }

  function renderPageItemsOnEnd(): JSX.Element {
    const renders: JSX.Element[] = [];
    for (
      let pageIndex = Math.max(
        currentPage + pageItemsAroundCurrentCount + 1,
        pagesCount - pageItemsOnEndsCount + 1
      );
      pageIndex <= pagesCount;
      pageIndex++
    ) {
      renders.push(renderPageItem(pageIndex));
    }
    return <>{renders}</>;
  }

  function renderPageSpaceItemBeforeCurrentPage(): JSX.Element {
    if (spaceExistsBetweenStartAndCurrentPageRangeMemo) {
      return renderPageSpaceItem();
    }
    return <></>;
  }

  function renderPageSpaceItemAfterCurrentPage(): JSX.Element {
    if (spaceExistsBetweenEndAndCurrentPageRangeMemo) {
      return renderPageSpaceItem();
    }
    return <></>;
  }

  const renderPageItemsOnStartMemo = useCallback(renderPageItemsOnStart, [
    currentPage,
  ]);
  const renderPageItemsBeforeCurrentPageMemo = useCallback(
    renderPageItemsBeforeCurrentPage,
    [currentPage]
  );
  const renderPageItemsOnEndMemo = useCallback(renderPageItemsOnEnd, [
    currentPage,
    pagesCount,
  ]);
  const renderPageItemsAfterCurrentPageMemo = useCallback(
    renderPageItemsAfterCurrentPage,
    [currentPage, pagesCount]
  );

  const renderPageSpaceItemBeforeCurrentPageMemo = useCallback(
    renderPageSpaceItemBeforeCurrentPage,
    [currentPage]
  );

  const renderPageSpaceItemAfterCurrentPageMemo = useCallback(
    renderPageSpaceItemAfterCurrentPage,
    [currentPage, pagesCount]
  );

  if (pagesCount === undefined || pagesCount === 0) {
    return <div className="w-full"></div>;
  }

  return (
    <div className="grid grid-flow-row grid-cols-7 gap-2 w-full">
      <div className="flex items-center justify-start">
        <Button
          variant="text"
          className="flex items-center gap-2 rounded-full"
          onClick={() => setCurrentPage((prev) => prev - 1)}
          disabled={disablePrevious}
        >
          <ArrowLeftIcon color="gray" strokeWidth={2} className="h-4 w-4" />
        </Button>
      </div>

      <div className="flex items-center justify-center">
        {renderPageItemsOnStartMemo()}
      </div>
      <div className="flex items-center justify-center">
        {renderPageSpaceItemBeforeCurrentPageMemo()}
      </div>
      <div className="flex items-center justify-center">
        {renderPageItemsBeforeCurrentPageMemo()}
        {renderCurrentPageItem()}
        {renderPageItemsAfterCurrentPageMemo()}
      </div>
      <div className="flex items-center justify-center">
        {renderPageSpaceItemAfterCurrentPageMemo()}
      </div>
      <div className="flex items-center justify-center">
        {renderPageItemsOnEndMemo()}
      </div>
      <div className="flex items-center justify-end">
        <Button
          variant="text"
          className="flex items-center gap-2 rounded-full"
          onClick={() => setCurrentPage((prev) => prev + 1)}
          disabled={disableNext}
        >
          <ArrowRightIcon color="gray" strokeWidth={2} className="h-4 w-4" />
        </Button>
      </div>
    </div>
  );
}
