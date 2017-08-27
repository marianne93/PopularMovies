package com.example.popularmovies;

import java.io.Serializable;

/**
 * Created by Marianne on 08-Jul-16.
 */
public class Movie {
        public String display_name;
        public float rating;
        public Double popularity;
        public String released_date;
        public String overview;
        public String poster_url;
        public int id;

        public String getDisplay_name() {
                return display_name;
        }

        public void setDisplay_name(String display_name) {
                this.display_name = display_name;
        }

        public float getRating() {
                return rating;
        }

        public void setRating(float rating) {
                this.rating = rating;
        }

        public Double getPopularity() {
                return popularity;
        }

        public void setPopularity(Double popularity) {
                this.popularity = popularity;
        }

        public String getReleased_date() {
                return released_date;
        }

        public void setReleased_date(String released_date) {
                this.released_date = released_date;
        }

        public String getOverview() {
                return overview;
        }

        public void setOverview(String overview) {
                this.overview = overview;
        }

        public String getPoster_url() {
                return poster_url;
        }

        public void setPoster_url(String poster_url) {
                this.poster_url = poster_url;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }
        // poster url format - http://image.tmdb.org/t/p/w185//k1QUCjNAkfRpWfm1dVJGUmVHzGv.jpg

        public Movie(){
        }
}
