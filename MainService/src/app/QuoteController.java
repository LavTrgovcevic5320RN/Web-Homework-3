package app;

import com.google.gson.Gson;
import http.Request;
import http.ServerThread;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class QuoteController extends Controller {
    List<String> quotes = ServerThread.quotes;
    private BufferedReader in;
    private PrintWriter out;
    private String qod, requestLine;
    private String qod2 = "";
    Gson gson = new Gson();

    public QuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        String htmlBody = "" +
                "<form method=\"POST\" action=\"/apply\">" +
                "<label>Author: </label><input name=\"author\" type=\"text\"><br><br>" +
                "<label>Quote: </label><input name=\"quote\" type=\"text\"><br><br>" +
                "<button>Submit</button>" +
                "</form>" +
                "<h2>Quote of the day: </h2>";

        try {
            Socket socket = new Socket("localhost", 8081);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            qod = "GET /qod";
            out.println(gson.toJson(qod, String.class));

            requestLine = in.readLine();
            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
            } while (!requestLine.trim().equals(""));

            if(ServerThread.qod == "") {
                ServerThread.qod = gson.fromJson(in.readLine(), String.class);
                htmlBody += ServerThread.qod + "<br>";
            }else{
                htmlBody += ServerThread.qod + "<br>";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlBody += "<h2>Saved quotes:</h2>";

        for(String s : quotes){
            htmlBody += s + "<br>";
        }

        String content = "<html><head><title>Odgovor servera</title></head>\n";

        content += "<body>" + htmlBody + "</body></html>";

        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {
        return new RedirectResponse("/quotes");
    }
}
