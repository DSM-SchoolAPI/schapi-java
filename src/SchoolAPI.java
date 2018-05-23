import datastructure.Meal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SchoolAPI {
    private static final String URL_FORMAT = "http://%s/sts_sci_md00_001.do?schulCode=%s&schulCrseScCode=%d&schulKndScScore=0%d&schYm=%d%02d";

    enum Region {
        SEOUL("stu.sen.go.kr"),
        BUSAN("stu.pen.go.kr"),
        DAEGU("stu.dge.go.kr"),
        INCHEON("stu.ice.go.kr"),
        GWANGJU("stu.gen.go.kr"),
        DAEJEON("stu.dje.go.kr"),
        ULSAN("stu.use.go.kr"),
        SEJONG("stu.sje.go.kr"),
        GYEONGGI("stu.cbe.go.kr"),
        KANGWON("stu.kwe.go.kr"),
        CHUNGBUK("stu.cbe.go.kr"),
        CHUNGNAM("stu.cne.go.kr"),
        JEONBUK("stu.jbe.go.kr"),
        JEONNAM("stu.jne.go.kr"),
        GYEONGBUK("stu.gbe.go.kr"),
        GYEONGNAM("stu.gne.go.kr"),
        JEJU("stu.jje.go.kr");

        private String value;

        Region(String value) {
            this.value = value;
        }
    }

    enum Type {
        KINDERGARTEN(1),
        ELEMENTARY(2),
        MIDDLE(3),
        HIGH(4);

        private int value;

        Type(int value) {
            this.value = value;
        }
    }

    private Region region;
    private String schoolCode;
    private Type type;

    public SchoolAPI(Region region, String schoolCode, Type type) {
        this.region = region;
        this.schoolCode = schoolCode;
        this.type = type;
    }

    private String getFormattedURL(int year, int month) {
        return URL_FORMAT.format(this.region.value, this.schoolCode, this.type.value, this.type.value, year, month);
    }

    private static String getResponseDataFromConnection(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

        rd.close();

        return response.toString();
    }

    public ArrayList<Meal> getMonthlyMenus(int year, int month) throws MalformedURLException, IOException {
        ArrayList<Meal> meals = new ArrayList<>();

        URL url = new URL(getFormattedURL(year, month));

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String responseData = getResponseDataFromConnection(conn);


    }
}
