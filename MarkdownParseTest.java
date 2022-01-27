import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.*;

public class MarkdownParseTest {
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
    
    @Test
    public void addition() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testGetLinkFileOne() throws IOException{
        Path fileName = Path.of("test-file1.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> linkOne = getLinks(contents);
        assertEquals("[https://something.com, some-page.html]", linkOne.toString());
    }

    @Test
    public void testGetLinkFileTwo() throws IOException{
        Path fileName = Path.of("test-file2.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> linkOne = getLinks(contents);
        assertEquals("[some-page.html]", linkOne.toString());
    }

    @Test
    public void testGetLinkFileThree() throws IOException{
        Path fileName = Path.of("test-file3.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> linkOne = getLinks(contents);
        assertEquals("[]", linkOne.toString());
    }

    @Test
    public void testGetLinkFileFour() throws IOException{
        Path fileName = Path.of("test-file4.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> linkOne = getLinks(contents);
        assertEquals("[]", linkOne.toString());
    }

    @Test
    public void testGetLinkFileFive() throws IOException{
        Path fileName = Path.of("test-file5.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> linkOne = getLinks(contents);
        assertEquals("[]", linkOne.toString());
    }

    @Test 
    public void testGetLinkFileSix() throws IOException{
        Path fileName = Path.of("test-file6.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> linkOne = getLinks(contents);
        assertEquals("[]", linkOne.toString());
    }

}

