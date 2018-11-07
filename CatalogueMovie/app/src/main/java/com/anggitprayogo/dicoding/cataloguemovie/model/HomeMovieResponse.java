package com.anggitprayogo.dicoding.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.anggitprayogo.dicoding.cataloguemovie.db.DatabaseContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    public static class Results implements Parcelable {
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

        public Results() {
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.releaseDate);
            dest.writeString(this.overview);
            dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
            dest.writeString(this.backdropPath);
            dest.writeList(this.genreIds);
            dest.writeString(this.originalTitle);
            dest.writeString(this.originalLanguage);
            dest.writeString(this.posterPath);
            dest.writeDouble(this.popularity);
            dest.writeString(this.title);
            dest.writeDouble(this.voteAverage);
            dest.writeByte(this.video ? (byte) 1 : (byte) 0);
            dest.writeInt(this.id);
            dest.writeInt(this.voteCount);
        }

        public Results(Cursor cursor){
            this.id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.ID)));
            this.voteCount = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.VOTE_COUNT)));
            this.voteAverage = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.VOTE_AVARAGE)));
            this.title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.TITLE));
            this.posterPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.POSTER_PATH));
            this.backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.BACKDROP_PATH));
            this.releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.RELEASE_DATE));
            this.overview = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.OVERVIEW));
            this.popularity = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.COLUMN_FAVOURITE.POPULARITY)));
        }

        protected Results(Parcel in) {
            this.releaseDate = in.readString();
            this.overview = in.readString();
            this.adult = in.readByte() != 0;
            this.backdropPath = in.readString();
            this.genreIds = new ArrayList<Integer>();
            in.readList(this.genreIds, Integer.class.getClassLoader());
            this.originalTitle = in.readString();
            this.originalLanguage = in.readString();
            this.posterPath = in.readString();
            this.popularity = in.readDouble();
            this.title = in.readString();
            this.voteAverage = in.readDouble();
            this.video = in.readByte() != 0;
            this.id = in.readInt();
            this.voteCount = in.readInt();
        }

        public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
            @Override
            public Results createFromParcel(Parcel source) {
                return new Results(source);
            }

            @Override
            public Results[] newArray(int size) {
                return new Results[size];
            }
        };
    }
}
