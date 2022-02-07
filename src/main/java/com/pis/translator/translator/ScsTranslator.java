package com.pis.translator.translator;

import com.pis.translator.entity.School;
import com.pis.translator.utils.SchoolUtils;
import nice.fontaine.overpass.models.response.geometries.Node;

import java.util.ArrayList;
import java.util.List;

public class ScsTranslator {

    private ScsTranslator() {
    }

    public static List<School> translateAll(List<Node> nodes, String searchArea) {
        List<School> schools = new ArrayList<>();
        for (Node node : nodes) {
            String name = node.tags.get("name");
            if (name == null)
                continue;
            School translated = translate(node);
            translated.setSearchArea(searchArea);
            SchoolUtils.generateId(translated);
            schools.add(translated);
        }
        return schools;
    }

    private static School translate(Node node) {
        School school = new School();

        school.setName(node.tags.get("name"));

        String enName = node.tags.get("name:en");
        if (enName != null)
            school.setNameEng(enName);

        StringBuilder address = new StringBuilder();
        if (node.tags.get("addr:city") != null) {
            address.append(node.tags.get("addr:city")).append(" ");
        }
        if (node.tags.get("addr:street") != null) {
            address.append(node.tags.get("addr:street")).append(" ");
        }
        if (node.tags.get("addr:housenumber") != null) {
            address.append(node.tags.get("addr:housenumber")).append(" ");
        }
        school.setAddress(address.toString().trim());
        return school;
    }
}
