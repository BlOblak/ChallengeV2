package com.challenge.resource.api.utils;

import com.challenge.repository.db.utils.DbPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebApiResponse<T> {

    // HttpStatus code
    @ApiModelProperty(value = "Http status code")
    int status;

    // Response
    @ApiModelProperty(value = "Content")
    T data;

    // Exception
    @ApiModelProperty(value = "Error message")
    String message;

    // Display message for client
    @ApiModelProperty(value = "Display message for client")
    String displayMessage;

    // Custom error code
    @ApiModelProperty(value = "Error code")
    String errorCode;

    // pagination
    @ApiModelProperty(value = "Page number")
    Integer pageNumber;
    @ApiModelProperty(value = "Page size")
    Integer pageSize;
    @ApiModelProperty(value = "Total number of elements")
    Long totalElements;
    @ApiModelProperty(value = "Total number of pages")
    Integer totalPages;

    public WebApiResponse() {
        super();
    }

    public WebApiResponse(T data) {
        this.status = HttpStatus.OK.value();
        this.data = data;
    }

    public WebApiResponse(T data, Page dbPage) {
        this(data);

        if (dbPage == null)
            return;

        this.pageNumber = dbPage.getPageable().getPageNumber();
        this.pageSize = dbPage.getPageable().getPageSize();
        this.totalElements = dbPage.getTotalElements();
        this.totalPages = dbPage.getTotalPages();
    }

    public WebApiResponse(T data, Integer pageNumber, Integer pageSize) {
        this(data);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * @param data   Data
     * @param status Http status
     */
    public WebApiResponse(T data, HttpStatus status) {
        this(data);
        this.status = status.value();
    }

    /**
     * @param msg   Message
     * @param status Http status
     */
    public WebApiResponse(HttpStatus status, String msg) {
        this.message = msg;
        this.status = status.value();
    }

    /**
     * @param data   Data
     * @param status Http status
     */
    public WebApiResponse(T data, HttpStatus status, Page dbPage) {
        this(data, dbPage);
        this.status = status.value();
    }
}
