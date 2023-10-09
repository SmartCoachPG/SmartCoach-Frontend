package api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.time.LocalTime;

public class TimeSerializer implements JsonSerializer<Time> {

    @Override
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
        LocalTime localTime = LocalTime.parse(src.toString());
        String formattedTime = String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond());

        return new JsonPrimitive(formattedTime);
    }

}
