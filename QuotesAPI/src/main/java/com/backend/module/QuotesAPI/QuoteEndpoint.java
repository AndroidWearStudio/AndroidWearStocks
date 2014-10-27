package com.backend.module.QuotesAPI;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "quotesApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "QuotesAPI.module.backend.com", ownerName = "QuotesAPI.module.backend.com", packagePath = ""))
public class QuoteEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getQuote")
    public QuoteBean getQuote(@Named("ticker") String ticker) {
        QuoteBean response = new QuoteBean();
        try {
            URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s="
                    + ticker + "&f=sl1p2");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                response.setTicker(rowData[0]);
                response.setPrice(rowData[1].replace("\"", ""));
                response.setChange(rowData[2].replace("\"", ""));
            }
        } catch (Exception e) {
            //handle error
            System.out.println("My ERROR! " + e.getMessage());
        }
        return response;
    }

}