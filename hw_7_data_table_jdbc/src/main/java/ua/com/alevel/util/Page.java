package ua.com.alevel.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Page<T> {

    public Page(List<T> elements) {
        this.elements = elements;
    }

    private Integer pageNumber;
    private Integer numberOfRows;
    private Boolean hasNext;
    private Boolean hasPrevious;
    protected List<T> elements;

    public int getNumberOfRows() {
        if (numberOfRows == null) {
            numberOfRows = 5;
        }
        return numberOfRows;
    }

    public Integer getPageNumber() {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        return pageNumber;
    }

    public List<Integer> getPagesNumbers() {
        List<Integer> pagesNumbers = new ArrayList<>();
        int numberOfPages = getTotalRows() / numberOfRows + 1;
        for (int i = 0; i < numberOfPages; i++) {
            pagesNumbers.add(i + 1);
        }
        return pagesNumbers;
    }

    public List<T> getCurrentPageElements() {
        List<T> currentPageElements = new ArrayList<>();
        int start = (getPageNumber() - 1) * getNumberOfRows();
        int finish = start + getNumberOfRows();
        for (int i = start; i < finish && i < elements.size(); i++) {
            currentPageElements.add(elements.get(i));
        }
        return currentPageElements;
    }

    public Integer getTotalRows() {
        return elements.size();
    }

}
