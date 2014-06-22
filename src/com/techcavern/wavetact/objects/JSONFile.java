package com.techcavern.wavetact.objects;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techcavern.wavetact.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class JSONFile {
    private final Charset charset = Charset.forName("UTF-8");
    private final File file;

    public JSONFile(String a) {
        file = new File(a);
    }

    public void write(Object obj, boolean pretty) throws IOException {
        Files.touch(file);
        Gson gson = pretty ? Constants.GSON_PRETTY_PRINT : Constants.GSON;
        Files.write(gson.toJson(obj), file, charset);
    }

    public void write(Object obj) throws IOException {
        write(obj, true);
    }

    public boolean exists() {
        return file.exists();
    }

    public <T> T read(Class<T> clazz) throws FileNotFoundException {
        return Constants.GSON.fromJson(Files.newReader(file, charset), new TypeToken<T>(){}.getType());
    }

    public <T> T read() throws FileNotFoundException {
        return Constants.GSON.fromJson(Files.newReader(file, charset), new TypeToken<T>(){}.getType());
    }
}
