package su.svn.href.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Locale;

public class MoneySerializer extends JsonSerializer<Double>
{
    @Override
    public void serialize(Double value, JsonGenerator generator, SerializerProvider provider)
    throws IOException, JsonProcessingException
    {
        generator.writeNumber(String.format(Locale.US, "%.2f", value));
    }
}
