package com.flipkart.json;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by shrey.garg on 25/12/16.
 */
public final class Lyrics {

    private final Song song;
    private final File target;

    public Lyrics(Tune tune, File target) {
        if (tune == null) {
            throw new IllegalArgumentException("Tune cannot be null");
        }

        if (target == null || !target.isDirectory()) {
            throw new IllegalArgumentException("The target file should be a directory");
        }

        this.song = new Song(tune);
        this.target = target;
    }

    public void compose(boolean cleanTarget, File... sources) {
        if (sources == null || sources.length == 0) {
            throw new IllegalArgumentException("List of source files cannot be empty");
        }

        if (cleanTarget) {
            Arrays.stream(target.listFiles()).forEach(this::deleteFiles);
        }

        for (File source : sources) {
            generateClasses("", source);
        }
    }

    private void generateClasses(String pkg, File source) {
        pkg = pkg + source.getName() + ".";
        if (source.isDirectory()) {
            String finalPkg = pkg;
            Arrays.stream(source.listFiles()).forEach(file -> generateClasses(finalPkg, file));
        } else {
            generate(source, pkg);
        }
    }

    private void generate(File source, String pkg) {
        String fullName = source.getName();
        if (!fullName.endsWith(".json")) {
            System.out.println(String.format("Skipping %s, not a JSON file", source.getAbsolutePath()));
            return;
        }

        try {
            TypeModel typeModel = JsonMapper.get().readValue(source, TypeModel.class);
            String name = fullName.replace(".json", "");
            pkg = pkg.substring(0, pkg.length() - 1);
            song.createType(name, pkg, typeModel, target);
        } catch (IOException e) {
            System.out.println("Error while processing " + source.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private void deleteFiles(File file) {
        if (file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(this::deleteFiles);
        }
        file.delete();
    }

}
