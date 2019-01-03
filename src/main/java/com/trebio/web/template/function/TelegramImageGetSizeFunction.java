package com.trebio.web.template.function;

import com.github.badoualy.telegram.tl.api.TLAbsPhoto;
import com.github.badoualy.telegram.tl.api.TLMessageMediaPhoto;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TelegramImageGetSizeFunction implements Function {

    private final static int WIDTH = 200;
    private final static Logger LOGGER = Logger.getLogger("trebio");

    public static final String FUNCTION_NAME = "getImageSize";

    @Override
    public ImageSize execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        Object media = args.get("media");
        TLAbsPhoto photo = ((TLMessageMediaPhoto) media).getPhoto();

        if (!(media instanceof TLMessageMediaPhoto)) {
            return null;
        }

        File file = new File(String.format("%s/src/main/webapp/resources/image/photo/%s_%s.jpg",
                System.getProperty("user.dir"),
                photo.getId(),
                "m"
        ));

        try {
            BufferedImage image = ImageIO.read(file);
            int widthRatio = image.getWidth() / WIDTH;
            int resizedHeight = image.getHeight() / widthRatio;

            return new ImageSize(WIDTH, resizedHeight);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        throw new PebbleException(null, "Something went wrong", lineNumber, self.getName());
    }

    @Override
    public List<String> getArgumentNames() {
        List<String> names = new ArrayList<>();
        names.add("media");

        return names;
    }
}