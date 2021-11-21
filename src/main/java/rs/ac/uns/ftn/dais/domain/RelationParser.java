package rs.ac.uns.ftn.dais.domain;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RelationParser {

    private Gson gson;
    public RelationParser() {
        gson = new Gson();
    }

    public Relation parse(String path) throws IOException {
        try(JsonReader reader = gson.newJsonReader(new FileReader(new File(path).getAbsolutePath()))) {
            reader.beginObject();

            String relationName = "";
            LabelSet relationLabels = new LabelSet();
            FunctionalDependencySet functionalDependencies = new FunctionalDependencySet();

            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("name")) {
                    relationName = reader.nextString();
                } else if (name.equals("labels")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        relationLabels.add(reader.nextString());
                    }
                    reader.endArray();
                } else if (name.equals("functionalDependencies")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        reader.beginObject();
                        LabelSet leftSide = new LabelSet();
                        LabelSet rightSide = new LabelSet();
                        while (reader.hasNext()) {
                            switch (reader.nextName()) {
                                case "left": {
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        leftSide.add(reader.nextString());
                                    }
                                    reader.endArray();
                                    break;
                                }
                                case "right": {
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        rightSide.add(reader.nextString());
                                    }
                                    reader.endArray();
                                    break;
                                }
                            }
                        }
                        functionalDependencies.add(new FunctionalDependency(leftSide, rightSide));
                        reader.endObject();
                    }
                    reader.endArray();
                }
            }

            reader.endObject();

            return new Relation(relationName, relationLabels, functionalDependencies);
        }
    }

}
