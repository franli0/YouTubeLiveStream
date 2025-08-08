package com.fengsui.youtubesearch;

import java.util.ArrayList;
import java.util.List;

public final class MovieList {
    public static final String MOVIE_CATEGORY[] = {
            "Category Zero",
            "Category One",
            "Category Two",
            "Category Three",
            "Category Four",
            "Category Five",
    };

    private static List<Movie> list;
    private static long count = 0;

    public static List<Movie> getList() {
        if (list == null) {
            list = setupMovies();
        }
        return list;
    }

    public static List<Movie> setupMovies() {
        list = new ArrayList<>();
        String title[] = {
                "TWICE &quot;The Feels&quot; M/V",
                "Jisoo Drama Controversy Explodes",
                "ITZY &quot;WANNABE&quot; M/V",
                "aespa 에스파 &#39;Dreams Come True&#39; MV",
                "▽◦◦▽\uD83E\uDD0D KPOP PLAYLIST 2020 \uD83E\uDD0D▽◦◦▽"
        };

        String description = "TWICE \\\"The Feels\\\" M/V TWICE's 1ST Full English Single \\\"The Feels\\\" Released on 10.01_FRI , 0AM (EST) 1PM (KST) Listen ...";
        String studio[] = {
                "Studio Zero", "Studio One", "Studio Two", "Studio Three", "Studio Four"
        };
        String videoUrl[] = {
                "f5_wn8mexmM",
                "w3V5PJGAi9g",
                "fE2h3lGlOsk",
                "H69tJmsgd9I",
                "WZPLefjiAZw"
        };
        String bgImageUrl[] = {
                /*"https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/bg.jpg",
                "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/bg.jpg",
                "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/bg.jpg",
                "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/bg.jpg",*/
                "",
                "",
                "",
                "",
                ""
        };
        String cardImageUrl[] = {
                "https://i.ytimg.com/vi/f5_wn8mexmM/hqdefault.jpg",
                "https://i.ytimg.com/vi/w3V5PJGAi9g/hqdefault.jpg",
                "https://i.ytimg.com/vi/fE2h3lGlOsk/hqdefault.jpg",
                "https://i.ytimg.com/vi/H69tJmsgd9I/hqdefault.jpg",
                "https://i.ytimg.com/vi/WZPLefjiAZw/hqdefault.jpg"
        };

        for (int index = 0; index < title.length; ++index) {
            list.add(
                    buildMovieInfo(
                            title[index],
                            description,
                            studio[index],
                            videoUrl[index],
                            cardImageUrl[index],
                            bgImageUrl[index]));
        }

        return list;
    }

    private static Movie buildMovieInfo(
            String title,
            String description,
            String studio,
            String videoUrl,
            String cardImageUrl,
            String backgroundImageUrl) {
        Movie movie = new Movie();
        movie.setId(count++);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setStudio(studio);
        movie.setCardImageUrl(cardImageUrl);
        movie.setBackgroundImageUrl(backgroundImageUrl);
        movie.setVideoUrl(videoUrl);
        return movie;
    }
}