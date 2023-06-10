package com.kms.booklet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("start")
    @Expose
    private Integer start;

    @SerializedName("docs")
    @Expose
    private List<SearchResultItem> docs = null;

    @SerializedName("numFound")
    @Expose
    private Integer numFound;

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStart() {
        return start;
    }

    public void setDocs(List<SearchResultItem> docs) {
        this.docs = docs;
    }

    public List<SearchResultItem> getDocs() {
        return docs;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    public Integer getNumFound() {
        return numFound;
    }

    @Override
    public String toString() {
        return
                "SearchResponse{" +
                        "start = '" + start + '\'' +
                        ",docs = '" + docs + '\'' +
                        ",numFound = '" + numFound + '\'' +
                        "}";
    }
}
