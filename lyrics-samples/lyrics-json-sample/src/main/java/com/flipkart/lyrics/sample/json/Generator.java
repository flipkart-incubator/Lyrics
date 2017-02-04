package com.flipkart.lyrics.sample.json;

import com.flipkart.lyrics.config.DefaultTune;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.json.Lyrics;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by shrey.garg on 04/02/17.
 */
public class Generator {

    public static void main(String[] args) {
        Tune tune = new DefaultTune();

        File target = new File(Generator.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        File generatedSources = new File(target, "generated-sources");

        Lyrics lyrics = new Lyrics(tune, generatedSources);

        // The working directory should be lyrics-json-sample
        File source = new File("src/main/resources/com");
        lyrics.compose(true, source);
    }

}
