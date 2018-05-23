import datastructure.Meal;
import parser.MealParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SchoolAPI {
    private static String URL_FORMAT = "https://%s/sts_sci_md00_001.do?schulCode=%s&schulCrseScCode=%d&schulKndScScore=0%d&schYm=%d%02d";

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
        return String.format(URL_FORMAT, this.region.value, this.schoolCode, this.type.value, this.type.value, year, month);
    }

    private static String getResponseDataFromConnection(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder buffer = new StringBuilder();
        String inputLine;

        boolean reading = false;

        while ((inputLine = reader.readLine()) != null) {
            if (reading) {
                if (inputLine.contains("</tbody>"))
                    break;
                buffer.append(inputLine);
            } else {
                if (inputLine.contains("<tbody>"))
                    reading = true;
            }
        }

        reader.close();

        return buffer.toString();
    }

    public ArrayList<Meal> getMonthlyMenus(int year, int month) throws IOException {
        URL url = new URL(getFormattedURL(year, month));

        return MealParser.parse(getResponseDataFromConnection(url));
    }
}
