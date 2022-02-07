package com.pis.translator;

import com.pis.translator.entity.School;
import com.pis.translator.overpass.OverpassClient;
import com.pis.translator.translator.ScsTranslator;
import nice.fontaine.overpass.models.response.geometries.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final String BELARUS = "\\u0411\\u0435\\u043b\\u0430\\u0440\\u0443\\u0441\\u044c";

    public static void main(String[] args) {

        List<Node> nodes = OverpassClient.getSchools(BELARUS, args[2]);
        List<School> schools = ScsTranslator.translateAll(nodes, args[1]);

        String rootPath = "\\";
        try {
            rootPath = args[1] + "_schools";
            Files.createDirectories(Path.of(rootPath));
        } catch (IOException e) {
            log.error("Cannot create directory: {}", e.getMessage());
        }

        for (School school : schools) {
            try {
                Path schoolFileName = Paths.get(rootPath + "\\" + school.getId().trim() + ".scs");
                Files.write(schoolFileName, school.toString().getBytes());
            } catch (IOException e) {
                log.error("Cannot save file: {} ", e.getMessage());
            }
        }
    }
}

