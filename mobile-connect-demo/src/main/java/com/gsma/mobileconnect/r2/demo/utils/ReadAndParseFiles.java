package com.gsma.mobileconnect.r2.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsma.mobileconnect.r2.demo.objects.OperatorParameters;
import com.gsma.mobileconnect.r2.json.AnnotatedDeserializer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadAndParseFiles {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadAndParseFiles.class);

    private ReadAndParseFiles() {}

    public static OperatorParameters readFile(String filePath)
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OperatorParameters.class, new AnnotatedDeserializer<OperatorParameters>())
                .create();
        OperatorParameters operatorParameters = null;
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            operatorParameters = gson.fromJson(json, OperatorParameters.class);
        } catch (Exception e) {
            LOGGER.warn(String.format("Failed to read file %s", filePath));
        }
        return operatorParameters;
    }

    public static JSONArray readJsonArray(String filePath) {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            Object object = jsonParser.parse(new FileReader(filePath));
            jsonArray = (JSONArray) object;
        } catch (IOException | ParseException e) {
            LOGGER.warn(String.format("Failed to read file %s", filePath));
        }
        return jsonArray;
    }
}
