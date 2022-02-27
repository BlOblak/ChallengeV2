package com.challenge.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "db_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbSequence {

    @ApiModelProperty(value = "Id")
    @Id
    private String  id;

    @ApiModelProperty(value = "Sequence")
    private int seq;
}
