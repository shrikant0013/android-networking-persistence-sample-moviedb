package com.shrikant.themoviedb.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by spandhare on 10/23/16.
 */

@Generated("org.jsonschema2pojo")
public class MovieList {

    @SerializedName("page")
    @Expose
    private Long page;
    @SerializedName("results")
    @Expose
    private List<Movie> results = new ArrayList<Movie>();
    @SerializedName("total_results")
    @Expose
    private Long totalResults;
    @SerializedName("total_pages")
    @Expose
    private Long totalPages;

    /**
     *
     * @return
     * The page
     */
    public Long getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Long page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Movie> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public Long getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Long getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

}