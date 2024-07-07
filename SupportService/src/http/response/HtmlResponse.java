package http.response;

import com.google.gson.Gson;

public class HtmlResponse extends Response {

    private final String html;

    Gson gson = new Gson();

    public HtmlResponse(String html) {
        this.html = html;
    }

    @Override
    public String getResponseString() {
//        System.out.println(html);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + gson.toJson(html, String.class);
        return response;
    }
}
