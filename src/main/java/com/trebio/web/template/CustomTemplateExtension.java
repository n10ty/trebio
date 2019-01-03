package com.trebio.web.template;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.extension.Test;
import com.trebio.context.bot.model.KotloBot;
import com.trebio.web.template.filter.NewLineCastFilter;
import com.trebio.web.template.function.TelegramImageGetLinkFunction;
import com.trebio.web.template.function.TelegramImageGetSizeFunction;
import com.trebio.web.template.function.TimestampToDateConverter;
import com.trebio.web.template.test.TelegramChannelEmptyPhotoTest;
import com.trebio.web.template.test.TelegramMessageHasPhotoTest;
import com.trebio.web.template.test.TelegramMessageIsPostTest;

import java.util.HashMap;
import java.util.Map;

public class CustomTemplateExtension extends AbstractExtension {
    private KotloBot bot;

    public CustomTemplateExtension(KotloBot bot) {
        this.bot = bot;
    }

    @Override
    public Map<String, Filter> getFilters() {
        Map<String, Filter> filters = new HashMap<>();

        filters.put(NewLineCastFilter.FILTER_NAME, new NewLineCastFilter());

        return filters;
    }

    @Override
    public Map<String, Function> getFunctions() {
        Map<String, Function> functions = new HashMap<>();

        functions.put(TelegramImageGetLinkFunction.FUNCTION_NAME, new TelegramImageGetLinkFunction(bot));
        functions.put(TelegramImageGetSizeFunction.FUNCTION_NAME, new TelegramImageGetSizeFunction());
        functions.put(TimestampToDateConverter.FUNCTION_NAME, new TimestampToDateConverter());

        return functions;
    }

    @Override
    public Map<String, Test> getTests() {
        Map<String, Test> tests = new HashMap<>();

        tests.put(TelegramMessageHasPhotoTest.TEST_NAME, new TelegramMessageHasPhotoTest());
        tests.put(TelegramMessageIsPostTest.TEST_NAME, new TelegramMessageIsPostTest());
        tests.put(TelegramChannelEmptyPhotoTest.TEST_NAME, new TelegramChannelEmptyPhotoTest());

        return tests;
    }
}
