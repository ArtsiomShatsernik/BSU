import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionalInterface
interface MyInterface {
    ArrayList<String> sort(ArrayList<String> ar);
}

public class Main {
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        System.out.println("Введите строку: ");
        String lex = s.nextLine();
        // String lex = "A 1 2 B TEST 11.UO,1A 1F011";
        System.out.println("Введите разделители: ");
        String sep = s.nextLine();
        System.out.println("Введите число для поиска в строке: ");
        String P = s.nextLine();
        // String sep = " .,\n";
        ArrayList <String> words;
        if (sep.length() <= 1) {  // Если разделитель один
            words = new ArrayList<>(Arrays.asList(lex.split(sep)));
            for (String i : words) {
                System.out.print(i + " ");
            }
        } else {                  // Если разделителей несколько
            words = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(lex, sep);
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                words.add(tokenizer.nextToken());
                System.out.print(words.get(i) + " ");
                i++;
            }
        }
        ArrayList<String> hex = new ArrayList<String>();
        boolean exFlag = false;
        for (int i = 0; i < words.size(); i++) {
            try {
                Integer.parseInt(words.get(i), 16);
            } catch (NumberFormatException e) {
                exFlag = true;
            } finally {
                if (exFlag == false) {
                    hex.add(words.get(i));
                }
                exFlag = false;
            }
        }
        System.out.println();
        System.out.println("16-ричные числа в строке: ");
        for (int i = 0; i < hex.size(); i++) {
            System.out.print(hex.get(i) + " ");
        }
        System.out.println();
        System.out.println("Числа в которых больше единиц, чем нулей: ");
        ArrayList <String> ZeroOne = new ArrayList<String>();
        for (String word: hex) {
            if (ZeroOne(word)) {
                System.out.println(word);
                ZeroOne.add(word);
            }
        }
        System.out.println("Добавим в первоначальную строку случайное число и удалим первую лексему с латинскими буквами: ");
        Random r = new Random();
        StringBuffer buffer = new StringBuffer(lex);
        buffer.append(" ");
        buffer.append(r.nextInt(10));
        String res = buffer.toString();
        System.out.println(res);
        ArrayList <String> noFirstLatin = new ArrayList<>(words);
        Pattern pattern = Pattern.compile("(.*)[a-zA-Z](.*)");
        for (int i = 0; i < noFirstLatin.size() - 1; i++) {
            Matcher matcher = pattern.matcher(noFirstLatin.get(i));
            if(matcher.matches()) {
                noFirstLatin.remove(i);
                break;
            }
        }
        for (String word: noFirstLatin ) {
            System.out.print(word + " ");
        }
        int position = -1;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(P)) {
                position = i;
                break;
            }
        }
        System.out.println();
        if (position == - 1) {
            System.out.println("Числа нет в строке ");
        } else {
            String text = String.format("Число %s есть в строке под индексом %d", P, position);
            System.out.println(text);
        }

        System.out.println("Удалим первое 16-ричное число: ");
        ArrayList <String> noFirstHex = new ArrayList<>(hex);
        noFirstHex.remove(0);
        for (String word: noFirstHex ) {
            System.out.print(word + " ");
        }
        System.out.println();
        System.out.println("Отсортируем лексемы в первоначальной строке по первому символу слов: ");
        MyInterface inter = (ar) -> {
            for (int i = 0; i < ar.size() - 1; i++) {
                for (int j = ar.size() - 1; j > i; j--) {
                    char[] symbols1 = ar.get(j).toCharArray();
                    char[] symbols2 = ar.get(j - 1).toCharArray();
                    if (symbols2[0] > symbols1[0]) {
                        String temp = ar.get(j);
                        Collections.swap(ar, j, j - 1);
                    }
                }
            }
            return ar;
        };
        ArrayList <String> sortedWords = inter.sort(words);
        for (String word: sortedWords ) {
            System.out.print(word + " ");
        }
    }

    public static boolean ZeroOne(String cur) {
        int oneCount = 0;
        int zeroCount = 0;
        for (int i = 0; i < cur.length(); i++) {
            char[] symbols = cur.toCharArray();
            if (symbols[i] == '1') {
                oneCount++;
            } else if (symbols[i] == '0') {
                zeroCount++;
            }
        }
        if (oneCount > zeroCount) {
            return true;
        } else {
            return false;
        }
    }
}