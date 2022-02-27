package com.challenge.repository.db.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Getter
@Setter
public class DbPage<T> {

    Pageable pageable;
    Integer totalPages;
    Integer totalElements;
    List<T> content;

    public DbPage(List<T> content) {
        this.content = content;
    }

    public DbPage(List<T> content, Pageable pageable, Integer totalPages, Integer totalElements) {
        this.pageable = pageable;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }

    public DbPage(List<T> content, Pageable pageable, Integer totalElements) {
        this.pageable = pageable;
        this.totalElements = totalElements;
        this.content = content;
        this.totalPages = 0;

        if (totalElements != null && totalElements != 0) {
            this.totalPages = ((totalElements - 1) / pageable.getPageSize()) + 1;
        }
    }
}
