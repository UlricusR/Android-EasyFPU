package info.rueth.fpucalculator;

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

/**
 * Holds an absorption scheme, connecting FPUs to recommended absorption times.
 */
public class AbsorptionScheme {
    private List<AbsorptionBlock> absorptionBlocks;
    private Context context;
    private static final String MAX_FPU = "max_fpu";
    private static final String ABSORPTION_TIME = "absorption_time";
    private static final String FILENAME_USER = "absorptionscheme.json";

    private static volatile AbsorptionScheme INSTANCE;

    static AbsorptionScheme getInstance(Context context) throws IOException {
        if (INSTANCE == null) {
            synchronized (AbsorptionScheme.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AbsorptionScheme(context);
                }
            }
        }
        return INSTANCE;
    }

    private AbsorptionScheme(Context context) throws IOException {
        this.context = context;

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
        absorptionBlocks = new ArrayList<>();
        readJsonStream(inputStream);
        inputStream.close();

        // Save user file in case inputStream used the default file
        if (!userFileExists) save();

        // Sort absorption blocks
        Collections.sort(absorptionBlocks, new Comparator<AbsorptionBlock>() {
            @Override
            public int compare(AbsorptionBlock o1, AbsorptionBlock o2) {
                return o1.getMaxFPU() - o2.getMaxFPU();
            }
        });
    }

    /**
     * Picks the absorption time associated to the number of FPUs, e.g.:
     * <p>absorptionScheme: 0-1 FPU - 3 hours; 1-2 FPU - 4 hours; 2-3 FPUs - 5 hours; 3-4 FPUs - 6 hours; >4 FPUs - 8 hours</p>
     * <p>The fpu value is commercially rounded to 0 digits, i.e. 2.49 will be rounded to 2, 2.50 will be rounded to 3.</p>
     * <p>If the fpu value is beyond the last scheme block, the time of the last scheme block in the array is returned.</p>
     *
     * @param fpus The calculated FPUs.
     * @return The associated absorption time.
     */
    public int getAbsorptionTime(double fpus) {
        // Round up the fpus - it's more secure to get a longer insulin interval
        long roundedFPUs = Math.round(fpus);

        // Find associated absorption time
        for (AbsorptionBlock absorptionBlock : absorptionBlocks) {
            if (roundedFPUs <= absorptionBlock.getMaxFPU()) {
                return absorptionBlock.getAbsorptionTime();
            }
        }

        // Seems to be beyond the last block, so return time of the last block
        return absorptionBlocks.get(absorptionBlocks.size() - 1).getAbsorptionTime();
    }

    private void readJsonStream(InputStream in) throws IOException {
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

    public void save() throws IOException {
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(FILENAME_USER, Context.MODE_PRIVATE);
        writeJsonStream(outputStream);
        outputStream.close();
    }

    private void writeJsonStream(OutputStream out) throws IOException {
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

    @Override
    public String toString() {
        String returnString = "Absorption Scheme:";
        for (AbsorptionBlock absorptionBlock : absorptionBlocks) {
            returnString = returnString.concat(" (" + absorptionBlock.getMaxFPU() + "FPU -> " + absorptionBlock.getAbsorptionTime() + "h)");
        }
        return returnString;
    }

    @Override
    public boolean equals(Object absorptionScheme) {
        if (this == absorptionScheme) return true;
        if (!(absorptionScheme != null && this.getClass() == absorptionScheme.getClass()))
            return false;
        return this.toString().equals(absorptionScheme.toString());
    }
}
