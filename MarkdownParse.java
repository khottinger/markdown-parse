// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

// Imported
import java.util.regex.Pattern;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )

        int currentIndex = 0;
        while(currentIndex < markdown.length()) {

            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            if(nextOpenBracket < 0){
                currentIndex++;
                continue;
            }
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            if(nextCloseBracket < 0){
                currentIndex = nextOpenBracket + 1;
                continue;
            }
            int openParen = markdown.indexOf("(", nextCloseBracket);
            if(openParen < 0){
                currentIndex = nextCloseBracket + 1;
                continue;
            }
            int closeParen = markdown.indexOf(")", openParen);
            if(closeParen < 0){
                currentIndex = openParen + 1;
                continue;
            }
            if(containsNewLine(markdown.substring(openParen + 1, closeParen))){
                // New variable for finding the index of new lines
                StringBuffer stringBuffer = new StringBuffer(markdown.substring(openParen + 1, closeParen));
                currentIndex = stringBuffer.indexOf("\n") + 1;
                // System.out.println("New line at " + stringBuffer.indexOf("\n"));
            } else {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
                currentIndex = closeParen + 1;
            }
        }
        return toReturn;
    }

    static boolean containsNewLine(String str) {
        Pattern regex = Pattern.compile("^(.*)$", Pattern.MULTILINE);
            return regex.split(str).length > 0;
    }

    /*
    *   link: https://stackoverflow.com/questions/28989930/java-newline-detection-in-strings#:~:text=You%20can%20use%20the%20regex%20pattern%20%5E%20%28.%2A%29%24,%28.%2A%29%24%22%2C%20Pattern.MULTILINE%29%3B%20return%20regex.split%20%28str%29.length%20%3E%200%3B%20%7D
    *   
    *    static boolean containsNewLine(String str) {
    *       Pattern regex = Pattern.compile("^(.*)$", Pattern.MULTILINE);
    *       return regex.split(str).length > 0;
    *    }
    */

    // Link: https://stackoverflow.com/questions/7649412/get-the-index-of-the-start-of-a-newline-in-a-stringbuffer
    // System.out.println("New line at " + stringBuffer.indexOf("\n"));
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}