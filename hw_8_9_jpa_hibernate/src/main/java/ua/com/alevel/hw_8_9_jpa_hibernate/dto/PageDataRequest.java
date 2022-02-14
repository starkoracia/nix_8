package ua.com.alevel.hw_8_9_jpa_hibernate.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDataRequest {

    public PageDataRequest() {
        this.numberOfElementsOnPage = 10;
        this.pageNumber = 1;
        this.sortBy = "id";
        this.isSortAsc = true;
        this.searchString = "";
    }

    private Integer numberOfElementsOnPage;
    private Integer pageNumber;
    private String sortBy;
    private Boolean isSortAsc;
    private String searchString;

}
