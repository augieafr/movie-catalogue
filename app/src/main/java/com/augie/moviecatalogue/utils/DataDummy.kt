package com.augie.moviecatalogue.utils

import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.data.source.remote.*

object DataDummy {
    // all the dummy is only for testing. The information in dummy data may not represent
    // the actual data
    fun generateDetailTvShow(): DetailTvShowResponse {
        return DetailTvShowResponse(
            "en",
            1,
            listOf(NetworksItem(" ", "", 1, "")),
            "",
            "",
            listOf(GenresItem("", 1)),
            1.0,
            listOf(ProductionCountriesItem("", "")),
            0,
            1,
            1,
            "",
            "",
            listOf(
                SeasonsItem(
                    "", "",
                    1, "", 1, 1, ""
                )
            ),
            listOf("", ""),
            listOf(CreatedByItem(1, "", "", "", 1)),
            LastEpisodeToAir(
                "", "", "",
                1, 1.0, "", 1,
                1, "", 1
            ),
            "",
            listOf("", ""),
            listOf(SpokenLanguagesItem("", "", "")),
            listOf(ProductionCompaniesItem("", "", 1, "")),
            "",
            1.0,
            "",
            "",
            listOf(1, 2, 3),
            "",
            true,
            "",
            "",
            ""
        )
    }

    fun generateDetailMovie(): DetailMovieResponse {
        return DetailMovieResponse(
            "en",
            "1",
            false,
            "Detail Movie",
            "backdrop.jpg",
            1,
            listOf(GenresItem("action", 1)),
            1.0,
            listOf(ProductionCountriesItem("iso", "dunno")),
            0,
            1,
            1,
            "this is overview",
            "Detail Movie",
            1,
            "poster.jpg",
            listOf(SpokenLanguagesItem("en", "isoEn?", "english")),
            listOf(ProductionCompaniesItem("logo.jpg", "dummy corp", 1, "usa")),
            "2021-01-01",
            1.0,
            "",
            "this is dummy",
            false,
            "dummy.com",
            "airing"
        )
    }

    fun generateDummyTvShowResponse(): TvResponse {
        return TvResponse(
            1,
            1,
            listOf(
                TvItem(
                    "2021-01-01",
                    "this is overview",
                    "en",
                    listOf(1, 2, 3),
                    "dummytvshowposter1.jpg",
                    listOf("USA"),
                    "dummytvshowbackdrop1.jpg",
                    "Dummy TvShow 1",
                    8.0,
                    8.0,
                    "Dummy TvShow 1",
                    0,
                    100
                ),

                TvItem(
                    "2021-02-02",
                    "this is overview",
                    "en",
                    listOf(1, 2, 3),
                    "dummytvshowposter2.jpg",
                    listOf("USA"),
                    "dummytvshowbackdrop2.jpg",
                    "Dummy TvShow 2",
                    8.0,
                    8.0,
                    "Dummy TvShow 2",
                    1,
                    200
                )
            ),
            1
        )

    }

    fun generateDummyMovieResponse(): MovieResponse {
        return MovieResponse(
            1,
            1,
            listOf(
                MovieItem(
                    "This is overview",
                    "en",
                    "Dummy Movie 1",
                    false,
                    "Dummy Movie 1",
                    listOf(1, 2, 3),
                    "dummymovieposter1.jpg",
                    "dummymoviebackdrop1.jpg",
                    "2021-01-01",
                    8.0,
                    8.0,
                    0,
                    false,
                    100
                ),
                MovieItem(
                    "This is overview",
                    "en",
                    "Dummy Movie 2",
                    false,
                    "Dummy Movie 2",
                    listOf(1, 2, 3),
                    "dummymovieposter2.jpg",
                    "dummymoviebackdrop2.jpg",
                    "2021-02-02",
                    8.0,
                    8.0,
                    1,
                    false,
                    200
                )
            ),
            1
        )
    }

    fun generateDummyTvShow(): ArrayList<TvShowEntity> {
        val tvShows = ArrayList<TvShowEntity>()

        tvShows.add(
            TvShowEntity(
                0,
                "Arrow",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                "2012",
                "Crime, Drama, Mystery, Action & Adventure",
                "42m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/fFT7T9y9NVRdcdVh81roVrJBcDh.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                1,
                "Doom Patrol",
                "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
                "2019",
                "Sci-Fi & Fantasy, Comedy, Drama",
                "49m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/nclcURTdlqVbDr6HPmrHC5X4qUu.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/b7unxzWSFfj7i6UVmuPixxxXFOF.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                2,
                "Dragon Ball",
                "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the mystical Dragon Balls brought her to Goku's home. Together, they set off to find all seven and to grant her wish.",
                "1986",
                "Animation, Action & Adventure, Sci-Fi & Fantasy",
                "25m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/tZ0jXOeYBWPZ0OWzUhTlYvMF7YR.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jipCnOL1aH81wL6CdlrbvHpkueg.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                3,
                "Fairy Tail",
                "Lucy is a 17-year-old girl, who wants to be a full-fledged mage. One day when visiting Harujion Town, she meets Natsu, a young man who gets sick easily by any type of transportation. But Natsu isn't just any ordinary kid, he's a member of one of the world's most infamous mage guilds: Fairy Tail.",
                "2009",
                "Action & Adventure, Animation, Comedy, Sci-Fi & Fantasy, Mystery",
                "25m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/jsYTctFnK8ewomnUgcwhmsTkOum.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/fANxNeH9JCXPrzNEfriGu1Y95dF.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                4,
                "Family Guy",
                "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
                "1999",
                "Animation, Comedy",
                "22m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/eWWCRjBfLyePh2tfZdvNcIvKSJe.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jbTqU6BJMufoMnPSlO4ThrcXs3Y.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                5,
                "Flash",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "2014",
                "Drama, Sci-Fi & Fantasy",
                "44m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jeruqNWhqRqOR1QyqdQdHunrvU5.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                6,
                "Game of Thrones",
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                "2011",
                "Sci-Fi & Fantasy, Drama, Action & Adventure",
                "1h",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/suopoADq0k8YZr4dQXcU6pToj6s.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                7,
                "Gotham",
                "Everyone knows the name Commissioner Gordon. He is one of the crime world's greatest foes, a man whose reputation is synonymous with law and order. But what is known of Gordon's story and his rise from rookie detective to Police Commissioner? What did it take to navigate the multiple layers of corruption that secretly ruled Gotham City, the spawning ground of the world's most iconic villains? And what circumstances created them – the larger-than-life personas who would become Catwoman, The Penguin, The Riddler, Two-Face and The Joker?",
                "2014",
                "Drama, Crime, Sci-Fi & Fantasy",
                "43m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/5tSHzkJ1HBnyGdcpr6wSyw7jYnJ.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/97GsCX3k6BbprnBIIMlVKirmhLz.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                8,
                "Grey's Anatomy",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                "2005",
                "Drama",
                "43m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg"
            )
        )

        tvShows.add(
            TvShowEntity(
                9,
                "Hanna",
                "This thriller and coming-of-age drama follows the journey of an extraordinary young girl as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is. Based on the 2011 Joe Wright film.",
                "2019",
                "Action & Adventure, Drama",
                "50m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/5nSSkcM3TgpllZ7yTyBOQEgAX36.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/6Fzyuwn8KeMCSqRILfdrDmCvmod.jpg"
            )
        )

        return tvShows
    }

    fun generateDummyMovie(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity(
                0,
                "A Star Is Born",
                "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
                "2018",
                "Drama, Romance, Music",
                "2h 16m",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg"
            )
        )

        movies.add(
            MovieEntity(
                1,
                "Alita: Battle Angel",
                "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                "2019",
                "Action, Science Fiction, Adventure",
                "2h 2m",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xRWht48C2V8XNfzvPehyClOvDni.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg"
            )
        )

        movies.add(
            MovieEntity(
                2,
                "Aquaman",
                "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
                "2018",
                "Action, Adventure, Fantasy",
                "2h 23m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/xLPffWMhMj1l50ND3KchMjYoKmE.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/9QusGjxcYvfPD1THg6oW3RLeNn7.jpg"
            )
        )

        movies.add(
            MovieEntity(
                3,
                "Bohemian Rhapsody",
                "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
                "2018",
                "Music, Drama, History",
                "2h 15m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/93xA62uLd5CwMOAs37eQ7vPc1iV.jpg"
            )
        )

        movies.add(
            MovieEntity(
                4,
                "Cold Pursuit",
                "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.",
                "2019",
                "Action, Crime, Thriller",
                "1h 59m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/aiM3XxYE2JvW1vJ4AC6cI1RjAoT.jpg"
            )
        )

        movies.add(
            MovieEntity(
                5,
                "Creed II",
                "Between personal obligations and training for his next big fight against an opponent with ties to his family's past, Adonis Creed is up against the challenge of his life.",
                "2018",
                "Drama",
                "2h 10m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/qPQFWrLoQYyGxmeBgmpX901Q0mF.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/9il7qNbeYnPMYlutsVYDsj4hRyb.jpg"
            )
        )

        movies.add(
            MovieEntity(
                6,
                "Fantastic Beasts: The Crimes of Grindelwald",
                "Gellert Grindelwald has escaped imprisonment and has begun gathering followers to his cause—elevating wizards above all non-magical beings. The only one capable of putting a stop to him is the wizard he once called his closest friend, Albus Dumbledore. However, Dumbledore will need to seek help from the wizard who had thwarted Grindelwald once before, his former student Newt Scamander, who agrees to help, unaware of the dangers that lie ahead. Lines are drawn as love and loyalty are tested, even among the truest friends and family, in an increasingly divided wizarding world.",
                "2018",
                "Adventure, Fantasy, Drama",
                "2h 14m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/fMMrl8fD9gRCFJvsx0SuFwkEOop.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/qrtRKRzoNkf5vemO9tJ2Y4DrHxQ.jpg"
            )
        )

        movies.add(
            MovieEntity(
                7,
                "Glass",
                "In a series of escalating encounters, former security guard David Dunn uses his supernatural abilities to track Kevin Wendell Crumb, a disturbed man who has twenty-four personalities. Meanwhile, the shadowy presence of Elijah Price emerges as an orchestrator who holds secrets critical to both men.",
                "2019",
                "Thriller, Drama, Science Fiction",
                "2h 9m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/svIDTNUoajS8dLEo7EosxvyAsgJ.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/ngBFDOsx13sFXiMweDoL54XYknR.jpg"
            )
        )

        movies.add(
            MovieEntity(
                8,
                "How to Train Your Dragon: The Hidden World",
                "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
                "2019",
                "Animation, Family, Adventure",
                "1h 44m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/bCYRgsT0Kndh23a6kHazBdXWCn1.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/lFwykSz3Ykj1Q3JXJURnGUTNf1o.jpg"
            )
        )

        movies.add(
            MovieEntity(
                9,
                "Avengers: Infinity War",
                "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
                "2018",
                "Adventure, Action, Science Fiction",
                "2h 29m",
                "https://www.themoviedb.org/t/p/w220_and_h330_face/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg"
            )
        )

        return movies
    }
}