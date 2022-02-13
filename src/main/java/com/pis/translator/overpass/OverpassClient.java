package com.pis.translator.overpass;

import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.models.query.statements.NodeQuery;
import nice.fontaine.overpass.models.response.OverpassResponse;
import nice.fontaine.overpass.models.response.geometries.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OverpassClient {

    private static final Logger log = LoggerFactory.getLogger(OverpassClient.class);

    private OverpassClient() {
    }

    public static List<Node> getSchools(String area, String limit) {
        int limitInt;
        try {
            limitInt = Integer.parseInt(limit);
        } catch (NumberFormatException e) {
            log.warn("Cannot parse limit argument. The default limit(100) will apply");
            limitInt = 100;
        }

        NodeQuery node = new NodeQuery.Builder()
                .tag("amenity", "school")
                .area(area)
                .limit(limitInt)
                .build();

        Call<OverpassResponse> call = Overpass.ask(node);
        OverpassResponse message = null;
        try {
            message = call.execute().body();
        } catch (IOException e) {
            log.error("Cannot to get result from overpass api: {}", e.getMessage());
        }

        if (message != null) {
            return Arrays.stream(message.elements).map(Node.class::cast).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
