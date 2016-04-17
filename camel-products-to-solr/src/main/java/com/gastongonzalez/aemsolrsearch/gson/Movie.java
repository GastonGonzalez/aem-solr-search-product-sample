package com.gastongonzalez.aemsolrsearch.gson;


import java.util.List;

public class Movie
{
    private String name;
    private String sku;
    private List<Person> cast;
    private List<Person> crew;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public List<Person> getCast()
    {
        return cast;
    }

    public void setCast(List<Person> cast)
    {
        this.cast = cast;
    }

    public List<Person> getCrew()
    {
        return crew;
    }

    public void setCrew(List<Person> crew)
    {
        this.crew = crew;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("name='").append(name).append('\'');
        sb.append(", sku='").append(sku).append('\'');
        sb.append(", cast=").append(cast);
        sb.append(", crew=").append(crew);
        sb.append('}');
        return sb.toString();
    }
}
