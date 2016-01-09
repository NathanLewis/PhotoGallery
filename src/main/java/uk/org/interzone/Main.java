package uk.org.interzone;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        File dir = new File("../photos");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".JPG");
            }
        });

        for (File picfile : files) {
            System.out.println(picfile);
        }

    }

}
