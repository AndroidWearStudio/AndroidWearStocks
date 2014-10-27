package com.example.isatimur.androidwearstocks;

import android.os.AsyncTask;

import com.backend.module.quotesapi.quotesApi.QuotesApi;
import com.backend.module.quotesapi.quotesApi.model.QuoteBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Тимакс on 24.10.2014.
 */
public class GetQuoteTask extends AsyncTask<String, Boolean, QuoteBean> {

    @Override
    protected QuoteBean doInBackground(String... params) {
        String ticker = params[0].toString();
        QuoteBean quoteBean = new QuoteBean();
        try {
            QuotesApi api = new QuotesApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null).setRootUrl("https://mbox-cloud-test.appspot.com/_ah/api").setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            }).build();

            quoteBean = api.getQuote(ticker).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quoteBean;
    }
}
