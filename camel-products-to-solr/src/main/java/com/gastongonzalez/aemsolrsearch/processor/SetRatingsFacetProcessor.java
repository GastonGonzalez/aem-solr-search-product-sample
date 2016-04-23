package com.gastongonzalez.aemsolrsearch.processor;

import com.gastongonzalez.aemsolrsearch.model.MovieDocument;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;

public class SetRatingsFacetProcessor implements Processor
{
    public void process(Exchange exchange) throws Exception
    {
        MovieDocument doc = exchange.getIn().getBody(MovieDocument.class);

        if (StringUtils.isNoneBlank(doc.getRatingsFacet()))
        {
            try
            {
                final Float numericRating = Float.valueOf(doc.getRatingsFacet());
                String ratingsLabel = null;
                switch (Math.round(numericRating))
                {
                    case 1:
                        ratingsLabel = "1 Star";
                        break;
                    case 2:
                        ratingsLabel = "2 Stars";
                        break;
                    case 3:
                        ratingsLabel = "3 Stars";
                        break;
                    case 4:
                        ratingsLabel = "4 Stars";
                        break;
                    case 5:
                        ratingsLabel = "5 Stars";
                        break;
                }

                doc.setRatingsFacet(ratingsLabel);
                exchange.getIn().setBody(doc);

            } catch (NumberFormatException e)
            {
                // skip handling
            }
        }
    }
}
