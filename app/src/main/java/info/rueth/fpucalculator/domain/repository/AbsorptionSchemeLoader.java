package info.rueth.fpucalculator.domain.repository;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.model.AbsorptionBlock;

class AbsorptionSchemeLoader {
    private Context context;

    private static final String MAX_FPU = "max_fpu";
    private static final String ABSORPTION_TIME = "absorption_time";
    private static final String FILENAME_USER = "absorptionscheme.json";

    AbsorptionSchemeLoader(Context context) {
        this.context = context;
    }

    List<AbsorptionBlock> load() throws IOException {
        // Load the absorption scheme
        InputStream inputStream;
        boolean userFileExists;
        try {
            // Load the user file
            inputStream = context.openFileInput(FILENAME_USER);
            userFileExists = true;
        } catch (FileNotFoundException e) {
            // Load the default file instead
            inputStream = context.getResources().openRawResource(R.raw.absorptionscheme_default);
            userFileExists = false;
        }

        // Read absorption blocks
        List<AbsorptionBlock> absorptionBlocks = new ArrayList<>();
        readJsonStream(inputStream, absorptionBlocks);
        inputStream.close();

        // Save user file in case inputStream used the default file
        if (!userFileExists) save(absorptionBlocks);

        // Sort absorption blocks
        Collections.sort(absorptionBlocks, (o1, o2) -> o1.getMaxFPU() - o2.getMaxFPU());

        return absorptionBlocks;
    }

    private void readJsonStream(InputStream in, List<AbsorptionBlock> absorptionBlocks) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                absorptionBlocks.add(readAbsorptionBlock(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    private AbsorptionBlock readAbsorptionBlock(JsonReader reader) throws IOException {
        int maxFPU = -1;
        int absorptionTime = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(MAX_FPU)) {
                maxFPU = reader.nextInt();
            } else if (name.equals(ABSORPTION_TIME)) {
                absorptionTime = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new AbsorptionBlock(maxFPU, absorptionTime);
    }

    void save(List<AbsorptionBlock> absorptionBlocks) throws IOException {
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(FILENAME_USER, Context.MODE_PRIVATE);
        writeJsonStream(outputStream, absorptionBlocks);
        outputStream.close();
    }

    private void writeJsonStream(OutputStream out, List<AbsorptionBlock> absorptionBlocks) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.setIndent("  ");
        writer.beginArray();
        for (AbsorptionBlock absorptionBlock : absorptionBlocks) {
            writeAbsorptionBlock(writer, absorptionBlock);
        }
        writer.endArray();
        writer.close();
    }

    private void writeAbsorptionBlock(JsonWriter writer, AbsorptionBlock absorptionBlock) throws IOException {
        writer.beginObject();
        writer.name(MAX_FPU).value(absorptionBlock.getMaxFPU());
        writer.name(ABSORPTION_TIME).value(absorptionBlock.getAbsorptionTime());
        writer.endObject();
    }

    public List<AbsorptionBlock> reset() throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.absorptionscheme_default);

        // Read absorption blocks
        List<AbsorptionBlock> absorptionBlocks = new ArrayList<>();
        readJsonStream(inputStream, absorptionBlocks);
        inputStream.close();

        // Save user file
        save(absorptionBlocks);

        // Sort absorption blocks
        Collections.sort(absorptionBlocks, (o1, o2) -> o1.getMaxFPU() - o2.getMaxFPU());

        return absorptionBlocks;
    }
}
