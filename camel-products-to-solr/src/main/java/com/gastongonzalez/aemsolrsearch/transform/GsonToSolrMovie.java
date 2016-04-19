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
        MovieDocument doc = new MovieDocument();
        doc.withId(movie.getSku());
        doc.withName(movie.getName());
        doc.withSku(movie.getSku());
        doc.withCast(getNamesFromPerson(movie.getCast()));
        doc.withCrew(getNamesFromPerson(movie.getCrew()));
        doc.withImage(movie.getImage());
        doc.withThumbnailImage(movie.getThumbnailImage());
        doc.withPlot(movie.getPlot());

        return doc;
    }

    private Set<String> getNamesFromPerson(final List<Person> people)
    {
        Set<String> peopleNames = new HashSet<String>();

        if (CollectionUtils.isNotEmpty(people))
        {
            for (Person person: people)
            {
                peopleNames.add(person.getName());
            }
        }

        return peopleNames;
    }
}
