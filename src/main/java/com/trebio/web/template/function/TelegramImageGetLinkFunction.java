package com.trebio.web.template.function;

import com.github.badoualy.telegram.tl.api.TLAbsPhoto;
import com.github.badoualy.telegram.tl.api.TLMessageMediaPhoto;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.trebio.context.bot.model.KotloBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelegramImageGetLinkFunction implements Function {

    public static final String FUNCTION_NAME = "get_image_link";

    private KotloBot bot;

    public TelegramImageGetLinkFunction(KotloBot bot) {
        this.bot = bot;
    }

    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        Object media = args.get("media");
        TLAbsPhoto photo = ((TLMessageMediaPhoto) media).getPhoto();
        
        if (!(media instanceof TLMessageMediaPhoto)) {
            return null;
        }

        bot.parsePhoto((TLMessageMediaPhoto) media);

        return String.format(
                "/image/%s_%s.jpg",
                photo.getId(),
                "m"
        );
    }

    @Override
    public List<String> getArgumentNames() {
        List<String> names = new ArrayList<>();
        names.add("media");

        return names;
    }
}
