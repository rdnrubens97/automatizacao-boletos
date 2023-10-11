package com.alra.service.model.cxempresa.retornoconsultaboleto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RetornoConsultaBoleto {
    @JsonProperty("totalPages")
    private Integer totalPages;
    @JsonProperty("totalElements")
    private Integer totalElements;
    @JsonProperty("last")
    private boolean last;
    @JsonProperty("first")
    private boolean first;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("numberOfElements")
    private Integer numberOfElements;
    @JsonProperty("content")
    private List<Content> content;
}
