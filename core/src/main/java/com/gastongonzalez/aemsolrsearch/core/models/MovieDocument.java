package com.gastongonzalez.aemsolrsearch.core.models;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Set;

public class MovieDocument
{
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String sku;
    @Field
    private Set<String> cast;
    @Field
    private Set<String> crew;

    public MovieDocument withId(String id)
    {
        this.id = id;
        return this;
    }

    public MovieDocument withName(String name)
    {
        this.name = name;
        return this;
    }

    public MovieDocument withSku(String sku)
    {
        this.sku = sku;
        return this;
    }

    public MovieDocument withCast(Set<String> cast)
    {
        this.cast = cast;
        return this;
    }

    public MovieDocument withCrew(Set<String> crew)
    {
        this.crew = crew;
        return this;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getSku()
    {
        return sku;
    }

    public Set<String> getCast()
    {
        return cast;
    }

    public Set<String> getCrew()
    {
        return crew;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("MovieDocument{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sku='").append(sku).append('\'');
        sb.append(", cast=").append(cast);
        sb.append(", crew=").append(crew);
        sb.append('}');
        return sb.toString();
    }
}
