package com.gastongonzalez.aemsolrsearch.gson;


import java.util.List;

public class Movie
{
    private String customerReviewAverage;
    private String format;
    private String image;
    private String mpaaRating;
    private String name;
    private String plot;
    private String sku;
    private String thumbnailImage;
    private List<Person> cast;
    private List<Person> crew;
    private Float regularPrice;

    public String getCustomerReviewAverage()
    {
        return customerReviewAverage;
    }

    public void setCustomerReviewAverage(String customerReviewAverage)
    {
        this.customerReviewAverage = customerReviewAverage;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getMpaaRating()
    {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating)
    {
        this.mpaaRating = mpaaRating;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPlot()
    {
        return plot;
    }

    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getThumbnailImage()
    {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage)
    {
        this.thumbnailImage = thumbnailImage;
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

    public Float getRegularPrice()
    {
        return regularPrice;
    }

    public void setRegularPrice(Float regularPrice)
    {
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("customerReviewAverage='").append(customerReviewAverage).append('\'');
        sb.append(", format='").append(format).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", mpaaRating='").append(mpaaRating).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", plot='").append(plot).append('\'');
        sb.append(", sku='").append(sku).append('\'');
        sb.append(", thumbnailImage='").append(thumbnailImage).append('\'');
        sb.append(", cast=").append(cast);
        sb.append(", crew=").append(crew);
        sb.append(", regularPrice=").append(regularPrice);
        sb.append('}');
        return sb.toString();
    }
}
