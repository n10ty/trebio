package com.trebio.web.template.test;

import com.github.badoualy.telegram.tl.api.TLAbsMessage;
import com.github.badoualy.telegram.tl.api.TLMessage;
import com.github.badoualy.telegram.tl.api.TLMessageMediaPhoto;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.Test;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class TelegramMessageHasPhotoTest implements Test {

    public static final String TEST_NAME = "hasPhoto";

    @Override
    public boolean apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
        if (!(input instanceof TLAbsMessage)) {
            throw new PebbleException(null, "Can not pass null value to \"media\" test.", lineNumber, self.getName());
        }

        if (!(input instanceof TLMessage)) {
            return false;
        }

        return ((TLMessage) input).getMedia() != null && ((TLMessage) input).getMedia() instanceof TLMessageMediaPhoto;
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }
}
