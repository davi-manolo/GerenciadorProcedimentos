package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertiesManager {

    private Properties props;

    public PropertiesManager(String filePath) {
        props = new Properties();
        try (InputStream in = new FileInputStream(filePath)) {
            props.load(in);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro de entrada/saída de dados: " + e.getMessage());
        }
    }

    public List<String> getKeys() {
        Set<Object> keys = props.keySet();
        return keys.stream()
                .map(key -> String.valueOf(key.toString()))
                .collect(Collectors.toList());
    }

    public String getValue(String key) {
        Object obj = props.get(key.toString());
        return obj != null ? obj.toString() : "Indefinido.";
    }

    public List<String> getValues() {
        Collection<Object> values = props.values();
        return values.stream()
                .map(entry -> entry.toString())
                .collect(Collectors.toList());
    }

}
