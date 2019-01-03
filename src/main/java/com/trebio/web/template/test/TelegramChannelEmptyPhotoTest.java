package com.trebio.web.template.test;

import com.github.badoualy.telegram.tl.api.TLAbsMessage;
import com.github.badoualy.telegram.tl.api.TLChannel;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.Test;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class TelegramChannelEmptyPhotoTest implements Test {

    public static final String TEST_NAME = "hasEmptyAvatar";

    @Override
    public boolean apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
        if (!(input instanceof TLAbsMessage)) {
            throw new PebbleException(null, "Can not pass null value to \"media\" test.", lineNumber, self.getName());
        }

        if (!(input instanceof TLChannel)) {
            return false;
        }

        return ((TLChannel) input).getPhoto().isEmpty();
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }
}
