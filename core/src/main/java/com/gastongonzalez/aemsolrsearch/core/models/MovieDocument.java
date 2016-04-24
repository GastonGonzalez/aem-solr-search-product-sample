package com.gastongonzalez.aemsolrsearch.core.models;

import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

public class MovieDocument
{
    @Field
    private String format;
    @Field
    private String id;
    @Field
    private String mpaaRating;
    @Field
    private String name;
    @Field
    private String sku;
    @Field
    private List<String> cast;
    @Field
    private List<String> crew;
    @Field
    private String plot;
    @Field(value = "imageLarge_url")
    private String image;
    @Field(value = "imageSmall_url")
    private String thumbnailImage;
    @Field(value = "ratings_df")
    private String ratingsFacet;
    @Field
    private Float price;

    public MovieDocument withFormat(String format)
    {
        this.format = format;
        return this;
    }

    public MovieDocument withId(String id)
    {
        this.id = id;
        return this;
    }

    public MovieDocument withMpaaRating(String mpaaRating)
    {
        this.mpaaRating = mpaaRating;
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

    public MovieDocument withCast(List<String> cast)
    {
        this.cast = cast;
        return this;
    }

    public MovieDocument withCrew(List<String> crew)
    {
        this.crew = crew;
        return this;
    }

    public MovieDocument withPlot(String plot)
    {
        this.plot = plot;
        return this;
    }

    public MovieDocument withImage(String image)
    {
        this.image = image;
        return this;
    }

    public MovieDocument withThumbnailImage(String thumbnailImage)
    {
        this.thumbnailImage = thumbnailImage;
        return this;
    }

    public MovieDocument withPrice(Float price)
    {
        this.price = price;
        return this;
    }

    public String getFormat()
    {
        return format;
    }

    public String getId()
    {
        return id;
    }

    public String getMpaaRating()
    {
        return mpaaRating;
    }

    public String getName()
    {
        return name;
    }

    public String getSku()
    {
        return sku;
    }

    public List<String> getCast()
    {
        return cast;
    }

    public List<String> getCrew()
    {
        return crew;
    }

    public String getPlot()
    {
        return plot;
    }

    public String getImage()
    {
        return image;
    }

    public String getThumbnailImage()
    {
        return thumbnailImage;
    }

    public Float getPrice()
    {
        return price;
    }

    public String getRatingsFacet()
    {
        return ratingsFacet;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("MovieDocument{");
        sb.append("format='").append(format).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", mpaaRating='").append(mpaaRating).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sku='").append(sku).append('\'');
        sb.append(", cast=").append(cast);
        sb.append(", crew=").append(crew);
        sb.append(", plot='").append(plot).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", thumbnailImage='").append(thumbnailImage).append('\'');
        sb.append(", ratingsFacet='").append(ratingsFacet).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
