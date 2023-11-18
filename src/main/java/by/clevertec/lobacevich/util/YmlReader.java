package by.clevertec.lobacevich.util;

import by.clevertec.lobacevich.exception.YmlReaderException;
import lombok.Cleanup;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YmlReader {

    private static final String PATH = "src/main/resources/application.yml";

    private YmlReader() {
    }

    public static Map<String, String> getData() {
        try {
            @Cleanup
            InputStream in = new FileInputStream(PATH);
            Yaml yaml = new Yaml();
            return yaml.load(in);
        } catch (IOException e) {
            throw new YmlReaderException("Can't read yml");
        }
    }
}
