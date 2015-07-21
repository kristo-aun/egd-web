package ee.esutoniagodesu;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

class TikRow {
    String jatikId;
    String yomi;
    String jap;
    String et;
    List<Map.Entry<String, String>> est;
}

public class JCTest {
    /**
     * Mitu tühikut ja koma
     * Rohkem kui 1
     * spaces=5220
     * commas=1501
     *
     * Rohkem kui 2
     * spaces=3644
     * commas=539
     *
     * Rohkem kui 3
     * spaces=2427
     * commas=212
     *
     *
     */
    @Test
    public void tiktable() throws FileNotFoundException {

        String inPath = "/home/scylla/esutoniagodesu/egd-web/tmp/tiktable.csv";

        Scanner sc = new Scanner(new File(inPath));

        TikRow a;
        int spaces = 0, commas = 0;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] arr = line.split("\t");
            if (arr.length != 4) throw new RuntimeException(line);
            a = new TikRow();

            a.jatikId = arr[0].trim();
            a.yomi = arr[1].trim();
            a.jap = arr[2].trim();
            a.et = arr[3].trim();
            a.est = extract(arr[3].trim());

            int spaceCount = StringUtils.countOccurrencesOf(a.et, " ");
            int commaCount = StringUtils.countOccurrencesOf(a.et, ",");

            if (spaceCount > 3) {
                spaces++;
            }

            if (commaCount > 3) {
                commas++;
            }
        }

        sc.close();
    }

    private static List<Map.Entry<String, String>> extract(String et) {
        List<Map.Entry<String, String>> result = new ArrayList<>();

        List<String> pairs = new ArrayList<>();

        String[] arr = et.split("//");
        for (String p : arr) {
            boolean commaBetween = commaBetweenBrackets(p);
            if (!commaBetween) {
                String[] arr2 = p.split(",");

                for (String q : arr2) {
                    if (q.contains("("))
                        result.add(bracketsSeparated(q));
                    else
                        result.add(toEntry(q, null));
                }
            } else {

                result.add(bracketsSeparated(p));
            }

        }
        return result;
    }

    private static Map.Entry<String, String> toEntry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static Map.Entry<String, String> bracketsSeparated(String s) {
        String betw = s.substring(s.indexOf("("), s.indexOf(")") + 1);
        String arr = s.replace(betw, "").trim();
        return toEntry(arr, betw.substring(betw.indexOf("(")+1, betw.indexOf(")")));
    }

    private static boolean commaBetweenBrackets(String s) {
        if (StringUtils.countOccurrencesOf(s, ",") > 1) throw new RuntimeException("commaBetweenBrackets: s=" + s);
        return s.contains(",") && s.contains("(") && s.contains(")") &&
            s.indexOf(",") > s.indexOf("(") && s.indexOf(",") < s.indexOf(")");
    }

    //@Test
    public void convert() throws IOException {

        String inPath = "/home/scylla/japest/andmetootlus/input/cont.csv";
        String outPath = "/home/scylla/japest/andmetootlus/output/constituents.csv";

        InputStreamReader isr = new InputStreamReader(new FileInputStream(inPath), "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outPath), "UTF-8");
        BufferedWriter writer = new BufferedWriter(osw);
        String line = reader.readLine();
        while(line != null) {

            String[] arr = line.split(";");

            List<String> constit = new ArrayList<>();
            if (arr.length > 1) {

                for (int i=1; i<arr.length;i++) {
                    if (!constit.contains(arr[i]) && !constit.contains(arr[i].toLowerCase())) {
                        constit.add(arr[i]);
                    }
                }

                if (constit.size() != arr.length-1) {
                    System.out.println("leitud topelt: kanji=" + arr[0]);
                }
            }

            StringBuilder oline = new StringBuilder(arr[0]);
            if (constit.size() > 0)
                oline.append(";").append(Joiner.on(";").skipNulls().join(constit));


            writer.write(oline.toString());
            writer.newLine();
            line = reader.readLine();
        }

        reader.close();

        writer.close();
    }

}

