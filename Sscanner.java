import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Sscanner {
    public static void main(String[] args) {

        int codeLine = 1;
        int countLexemes = 0;
        boolean isValue = false;
        String className = test.class.getSimpleName();

        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> identifiers = new ArrayList<>();

        try {
            // فتح ملف النص للقراءة
            File file = new File("test.java");
            Scanner scanner = new Scanner(file);

            // قراءة الملف وتحليله
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                if (line.trim().startsWith("//")) {
                    System.out.println("Comment has found in line " + codeLine);
                    codeLine++;
                    continue;
                }

                String[] words = line.split("\\s+");

                for (String word : words) {
                    ++countLexemes;

                    if (isValue == true) {
                        values.add(word);
                        if (isNumber(word)) {
                            System.out.println("<num," + word + ">");
                        } else {
                            System.out.println("<String," + word + ">");
                        }

                        isValue = false;

                    } else if (word.equals("start") || word.equals("finish") || word.equals("if")
                            || word.equals("repeat")
                            || word.equals("var") ||
                            word.equals("int") || word.equals("float") || word.equals("do") || word.equals("read")
                            || word.equals("print") ||
                            word.equals("void") || word.equals("return") || word.equals("then") || word.equals("String")
                            ||
                            word.equals("args") || word.equals("System") || word.equals("System.out.print") ||
                            word.equals("public") || word.equals("class") || word.equals("static")
                            || word.equals("double") || word.equals("main") ||word.equals("for")) {
                        System.out.println("<Keyword," + word + ">");

                        // identifiers
                    } else if (isIdentifier(word)) {
                        if (!word.equals(className)) {
                            if(! identifiers.contains(word)){
                                identifiers.add(word);
                            }

                            
                        }

                        System.out.println("<Id," + word + ">");

                        // relational operator
                    } else if (word.equals("=")) {
                        isValue = true;
                        System.out.println("<" + word + ">");

                    }

                    else if (word.equals("==") || word.equals("!=") || word.equals(">") || word.equals(">=")
                            || word.equals("<")
                            || word.equals("<=")
                            || word.equals("=") || word.equals("+") || word.equals("-") || word.equals("*")
                            || word.equals("/") || word.equals("%") || word.equals(" ") ||word.equals("++")) {
                        System.out.println("<" + word + ">");

                        // delimiters
                    } else if (word.equals(".") || word.equals("(") || word.equals(")") || word.equals(",")
                            || word.equals("{")
                            || word.equals("}")
                            || word.equals(";")
                            || word.equals("[")
                            || word.equals("]")) {
                        System.out.println("<Delimiters," + word + ">");

                        // numbers
                    } else if (isNumber(word)) {
                        System.out.println("<num," + word + ">");

                        // comment
                    } else if (word.length() > 0) {
                        System.out.println(word + " : This lexeme is not known in line :" + codeLine);
                    }

                }
                codeLine++;

            }

            // for closing the scanner
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // printing number of lexemes
        System.out.println("\nThe number of the lexemes in the program: " + countLexemes);
        

        // printing synpol tabel
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("\nSymbol Tabel:");

        System.out.printf("%-10s %-10s %-10s\n", "Name", "Value", "Type");

        for (int i = 0; i < identifiers.size(); i++) {
            System.out.printf("%-10s %-10s %-10s\n", identifiers.get(i), values.get(i), getType(values.get(i)));
           
        }



    }

    // methos for determine whether if it is identifier
    private static boolean isIdentifier(String word) {
        // تحديد ما إذا كانت الكلمة تبدأ بحرف
        if (word.length() == 0 || !Character.isLetter(word.charAt(0))) {
            return false;
        }

        // تحديد ما إذا كانت الكلمة تحتوي على أحرف وأرقام فقط
        for (int i = 1; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!Character.isLetterOrDigit(c)){
                if(c != '_'){
                    return false;
                }
            }

        }

        return true;
    }

    private static boolean isNumber(String word) {
        try {
            Double.parseDouble(word);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getType(String word) {
        if (word.matches("[+-]?\\d+")) {
            return "int";
        } else if (word.matches("[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?")) {
            return "double";
        } else if (word.matches("(?i)true|false")) {
            return "boolean";
        } else {
            return "string";
        }
    }

}
