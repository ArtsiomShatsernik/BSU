import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("inputTags.txt");
        FileWriter writer = new FileWriter("correctTags.txt");
        Scanner scanner = new Scanner(reader);
        ArrayList <String> rString =  new ArrayList<String>();
        while (scanner.hasNextLine()) {
            rString.add(scanner.nextLine());
        }
        System.out.println(rString);
        final String commentTagPattern = "^<!--(.)*-->$";
        final String attributePattern = "( (cell|multiple)| ((\\w)+=((\"([\\w\\s?!#])*\")|(([\\w\\s?!#])+))))*";
        final String emptyTagPattern = "^<(p|hr|br|img)" + attributePattern + ">[\\s\\w]*$";
        final String doubleTagPattern = "^<([a-zA-Z]+\\w*)" + attributePattern + ">[\\s\\w]*</[a-zA-Z]+\\w*>$";
        Pattern htmlTagPattern = Pattern.compile("(" + commentTagPattern + ")|(" + emptyTagPattern + ")|(" + doubleTagPattern + ")");
        for (String t: rString) {
            Matcher matcher = htmlTagPattern.matcher(t);
            if (matcher.matches()) {
                writer.write(t);
                writer.append('\n');
                System.out.println(t + " is a correct tag");
            } else {
                System.out.println(t + " is an incorrect tag");
            }
        }
        reader.close();
        writer.close();
    }
}