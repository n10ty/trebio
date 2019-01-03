package com.trebio.web.template.filter;

import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class NewLineCastFilter implements Filter {

    public static final String FILTER_NAME = "n_replace";

    @Override
    public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber)  {
        if (input instanceof String) {
            return ((String) input).replaceAll("\n", "<br/>");
        }


        return null;
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }
}
