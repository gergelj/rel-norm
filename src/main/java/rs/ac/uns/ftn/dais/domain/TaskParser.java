package rs.ac.uns.ftn.dais.domain;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import rs.ac.uns.ftn.dais.input.NormalFormInput;
import rs.ac.uns.ftn.dais.input.RelationInput;
import rs.ac.uns.ftn.dais.mapper.RelationMapper;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TaskParser {

    private Gson gson;
    private String path;
    public TaskParser(String path) {
        this.path = path;
        gson = new Gson();
    }

    public Relation parseRelation() throws IOException {
        try(JsonReader reader = gson.newJsonReader(new FileReader(new File(path).getAbsolutePath()))){
            RelationInput input = gson.fromJson(reader, RelationInput.class);
            return RelationMapper.map(input);
        }
    }

    public NormalFormInput parseNormalFormInput() throws IOException {
        try(JsonReader reader = gson.newJsonReader(new FileReader(new File(path).getAbsolutePath()))){
            return gson.fromJson(reader, NormalFormInput.class);
        }
    }
}
