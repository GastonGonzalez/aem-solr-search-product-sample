package com.gastongonzalez.aemsolrsearch.model;

import com.gastongonzalez.aemsolrsearch.gson.Movie;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Set;

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
    private Set<String> cast;
    @Field
    private Set<String> crew;
    @Field
    private String plot;
    @Field(value = "imageLarge_url")
    private String image;
    @Field(value = "imageSmall_url")
    private String thumbnailImage;
    @Field(value = "ratings_df")
    private String ratingsFacet;
    @Field
    private String source;

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

    public MovieDocument withRatingsFacet(String ratingsFacet)
    {
        this.ratingsFacet = ratingsFacet;
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

    public MovieDocument withSource(String source)
    {
        this.source = source;
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

    public Set<String> getCast()
    {
        return cast;
    }

    public Set<String> getCrew()
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

    public String getRatingsFacet()
    {
        return ratingsFacet;
    }

    public String getThumbnailImage()
    {
        return thumbnailImage;
    }

    public Float getPrice()
    {
        return price;
    }

    public String getSource() {
        return source;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setMpaaRating(String mpaaRating)
    {
        this.mpaaRating = mpaaRating;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public void setCast(Set<String> cast)
    {
        this.cast = cast;
    }

    public void setCrew(Set<String> crew)
    {
        this.crew = crew;
    }

    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setThumbnailImage(String thumbnailImage)
    {
        this.thumbnailImage = thumbnailImage;
    }

    public void setRatingsFacet(String ratingsFacet)
    {
        this.ratingsFacet = ratingsFacet;
    }

    public void setPrice(Float price)
    {
        this.price = price;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString()
    {
        final java.lang.StringBuilder sb = new java.lang.StringBuilder("MovieDocument{");
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
        sb.append(", source='").append(source).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
