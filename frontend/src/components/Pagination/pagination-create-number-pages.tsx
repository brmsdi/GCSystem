export async function createNumberPages(
  currentPage: number,
  totalPages: number,
  max: number = 5
) {
  const totalNumberPages = Math.ceil(totalPages / max);
  for (var index = 1; index <= totalNumberPages; index++) {
    let maxNumberOfPage = index * 5;
    let minNumberOfPage = maxNumberOfPage - 4;
    let tempNumbers: number[] = [];
    if (
      currentPage >= minNumberOfPage &&
      currentPage <= maxNumberOfPage &&
      !(maxNumberOfPage > totalPages)
    ) {
      for (
        let pageMin = minNumberOfPage;
        pageMin <= maxNumberOfPage;
        pageMin++
      ) {
        tempNumbers.push(pageMin);
      }
      return {
        pagesNumbers: tempNumbers,
        currentPage: currentPage,
        next: maxNumberOfPage < totalPages ? true : false,
        first: minNumberOfPage === 1 ? true : false,
        nextPage: maxNumberOfPage + 1,
        previousPagination: minNumberOfPage,
      };
    } else if (maxNumberOfPage > totalPages) {
      for (let pageMin = minNumberOfPage; pageMin <= totalPages; pageMin++) {
        tempNumbers.push(pageMin);
      }
      return {
        pagesNumbers: tempNumbers,
        currentPage: currentPage,
        next: false,
        first: minNumberOfPage === 1 ? true : false,
        nextPage: totalPages,
        previousPagination: minNumberOfPage - 1,
      };
    }
  } // end for index
}
