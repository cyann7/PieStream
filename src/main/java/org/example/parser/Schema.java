package org.example.parser;

import org.example.events.Attribute;
import org.example.utils.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Schema {
    private String rawdataType;
    private String timestampField;
    private Map<String, String> fields;
    private boolean hasNativeTimestamp;
    private List<Attribute> attributes;
    private Map<String, Integer> fieldIndexMap;

    public Schema(String schemaFilePath) {
        this.fields = new HashMap<>();
        this.attributes = new ArrayList<>();
        this.fieldIndexMap = new HashMap<>();
        loadSchema(schemaFilePath);
    }

    private void loadSchema(String schemaFilePath) {
        try {
            Config config = Config.loadConfig(schemaFilePath);
            this.rawdataType = config.getRawdataType(); // 获取 rawdataType
            this.timestampField = config.getTimestamp();
            this.hasNativeTimestamp = timestampField != null && !timestampField.isEmpty();
            this.fields.clear();
            this.attributes.clear();
            this.fieldIndexMap.clear();

            for (Config.Field field : config.getFields()) {
                fields.put(field.getName(), field.getType());
                attributes.add(new Attribute(field.getName(), field.getType()));
                fieldIndexMap.put(field.getName(), attributes.size() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for rawdataType
    public String getRawdataType() {
        return rawdataType;
    }

    public String getTimestampField() {
        return timestampField;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public boolean hasNativeTimestamp() {
        return hasNativeTimestamp;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Map<String, Integer> getFieldIndexMap() {
        return fieldIndexMap;
    }
}