package com.gastongonzalez.aemsolrsearch.transform;

import com.gastongonzalez.aemsolrsearch.gson.Movie;
import com.gastongonzalez.aemsolrsearch.gson.Person;
import com.gastongonzalez.aemsolrsearch.model.MovieDocument;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GsonToSolrMovie
{
    public MovieDocument adapt(Movie movie)
    {
        MovieDocument doc = new MovieDocument()
                .withFormat(movie.getFormat())
                .withId(movie.getSku())
                .withMpaaRating(movie.getMpaaRating())
                .withName(movie.getName())
                .withPrice(movie.getRegularPrice())
                .withSku(movie.getSku())
                .withCast(getNamesFromPerson(movie.getCast()))
                .withCrew(getNamesFromPerson(movie.getCrew()))
                .withImage(movie.getImage())
                .withRatingsFacet(movie.getCustomerReviewAverage())
                .withThumbnailImage(movie.getThumbnailImage())
                .withPlot(movie.getPlot())
                .withSource("Best Buy");

        return doc;
    }

    private Set<String> getNamesFromPerson(final List<Person> people)
    {
        Set<String> peopleNames = new HashSet<String>();

        if (CollectionUtils.isNotEmpty(people))
        {
            for (Person person : people)
            {
                peopleNames.add(person.getName());
            }
        }

        return peopleNames;
    }
}
