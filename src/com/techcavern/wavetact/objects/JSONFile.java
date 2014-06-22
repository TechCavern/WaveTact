package com.techcavern.wavetact.objects;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.techcavern.wavetact.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class JSONFile {
    private File file;

    public JSONFile(String a) {
        File b = new File(a);
        this.file = b;
    }

    public void write(Object obj, boolean pretty) throws IOException {
        if (!file.exists()) {
            Files.touch(file);
        }
        Gson gson = pretty ? Constants.GSON_PRETTY_PRINT : Constants.GSON;
        Files.write(gson.toJson(obj), file, Charset.forName("UTF-8"));
    }

    public void write(Object obj) throws IOException {
        write(obj, true);
    }

    public boolean exists() {
        return file.exists();
    }

    public <T> T read(Class<T> clazz) throws FileNotFoundException {
        return Constants.GSON.fromJson(Files.newReader(file, Charset.forName("UTF-8")), clazz);
    }
}
