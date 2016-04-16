package com.gastongonzalez.aemsolrsearch;

import org.apache.solr.client.solrj.beans.Field;

public class Movie {

    @Field(value = "id")
    private String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("sku='").append(sku).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
