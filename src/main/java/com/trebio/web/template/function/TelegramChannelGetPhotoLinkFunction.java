package com.trebio.web.template.function;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class TelegramChannelGetPhotoLinkFunction implements Function {
    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        return null;
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }
}
