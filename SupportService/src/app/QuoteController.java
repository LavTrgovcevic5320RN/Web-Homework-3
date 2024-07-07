package app;

import http.Request;
import http.response.HtmlResponse;
import http.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteController extends Controller {

    public static List<String> quotes = new ArrayList<>(){{
        add("Allan Bloom: " + '"' + "Education is the movement from darkness to light." + '"');
        add("Abraham Lincoln: " + '"' + "The Best way to predict your Future is to create it." + '"');
        add("Albert Einstein: " + '"' + "Anyone who has never made a mistake has never tried anything new." + '"');
        add("Leonardo Da Vinci: " + '"' + "Learning never exhausts the mind" + '"');
    }};

    public QuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Random rand = new Random();
        String s = quotes.get(rand.nextInt(quotes.size()));
        return new HtmlResponse(s);
    }

    @Override
    public Response doPost() {
        return null;
    }
}
