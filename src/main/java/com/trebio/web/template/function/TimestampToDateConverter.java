package com.trebio.web.template.function;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TimestampToDateConverter  implements Function {

    private final static int WIDTH = 200;
    private final static Logger LOGGER = Logger.getLogger("trebio");

    public static final String FUNCTION_NAME = "convertToDate";

    @Override
    public String execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        Integer timestamp = (Integer) args.get("timestamp");

        Date date = new Date(timestamp * 1000L);

        DateFormat dateFormat = new SimpleDateFormat("kk:mm dd-MM-yyyy");

        return dateFormat.format(date);
    }

    @Override
    public List<String> getArgumentNames() {
        List<String> names = new ArrayList<>();
        names.add("timestamp");
        names.add("format");

        return names;
    }
}