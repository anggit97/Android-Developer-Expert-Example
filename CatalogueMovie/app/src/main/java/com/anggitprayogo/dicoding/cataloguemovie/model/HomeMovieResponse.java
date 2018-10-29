package com.anggitprayogo.dicoding.cataloguemovie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeMovieResponse {

    @Expose
    @SerializedName("total_pages")
    private int totalPages;
    @Expose
    @SerializedName("dates")
    private Dates dates;
    @Expose
    @SerializedName("total_results")
    private int totalResults;
    @Expose
    @SerializedName("page")
    private int page;
    @Expose
    @SerializedName("results")
    private List<Results> results;

    public int getTotalPages() {
        return totalPages;
    }

    public Dates getDates() {
        return dates;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getPage() {
        return page;
    }

    public List<Results> getResults() {
        return results;
    }

    public static class Dates {
        @Expose
        @SerializedName("minimum")
        private String minimum;
        @Expose
        @SerializedName("maximum")
        private String maximum;

        public String getMinimum() {
            return minimum;
        }

        public String getMaximum() {
            return maximum;
        }
    }

    public static class Results {
        @Expose
        @SerializedName("release_date")
        private String releaseDate;
        @Expose
        @SerializedName("overview")
        private String overview;
        @Expose
        @SerializedName("adult")
        private boolean adult;
        @Expose
        @SerializedName("backdrop_path")
        private String backdropPath;
        @Expose
        @SerializedName("genre_ids")
        private List<Integer> genreIds;
        @Expose
        @SerializedName("original_title")
        private String originalTitle;
        @Expose
        @SerializedName("original_language")
        private String originalLanguage;
        @Expose
        @SerializedName("poster_path")
        private String posterPath;
        @Expose
        @SerializedName("popularity")
        private double popularity;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("vote_average")
        private double voteAverage;
        @Expose
        @SerializedName("video")
        private boolean video;
        @Expose
        @SerializedName("id")
        private int id;
        @Expose
        @SerializedName("vote_count")
        private int voteCount;

        public String getReleaseDate() {
            return releaseDate;
        }

        public String getOverview() {
            return overview;
        }

        public boolean getAdult() {
            return adult;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getTitle() {
            return title;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public boolean getVideo() {
            return video;
        }

        public int getId() {
            return id;
        }

        public int getVoteCount() {
            return voteCount;
        }
    }
}
