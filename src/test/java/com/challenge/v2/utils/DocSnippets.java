package com.challenge.v2.utils;

import com.challenge.resource.api.utils.WebApiResponse;
import org.springframework.restdocs.request.RequestParametersSnippet;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public class DocSnippets {
    public static RequestParametersSnippet getPageAndSizeRequestParameters() throws NoSuchFieldException {
        return requestParameters(
                parameterWithName("page").description(ApiDocs.desc(WebApiResponse.class, "pageNumber")),
                parameterWithName("size").description(ApiDocs.desc(WebApiResponse.class, "pageSize"))
        );
    }
}
