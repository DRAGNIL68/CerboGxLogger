package net.outmoded.cerbogxlogger;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LangManager {
    private HashMap<String, String> langHash = new HashMap<>();

    private static LangManager instance;

    private LangManager() {}

    public static synchronized LangManager getInstance() {
        if (instance == null) {
            instance = new LangManager();
        }
        return instance;
    }

    public void load(){
        Path path;
        Path path1;
        try {
            path = Paths.get(Launcher.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());

            path = path.getParent();

            path1 = Paths.get(path.toString(), "lang.yml");

            if (!Files.exists(path1)){
                Launcher.getLogger().info("creating lang.yml");
                Path copied = Paths.get("src/main/resources/lang.yml");
                Files.copy(copied, path1, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        Yaml yaml = new Yaml();

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path1.toFile());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        langHash = yaml.load(inputStream);
        System.out.println(langHash);


        langHash.clear();

        Launcher.getLogger().info("loaded lang.yml successfully. {} string(s) loaded", langHash.size()+1);
    }

    public String getLangString(String key) {
        return langHash.get(key);
    }

    public Set<String> getAllKeys(){
        return langHash.keySet();
    }

}
