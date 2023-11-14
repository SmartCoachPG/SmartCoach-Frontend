package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateSerializer implements JsonSerializer<Date> {
    private final SimpleDateFormat dateFormat;

    public DateSerializer() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(src);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date updatedDate = calendar.getTime();

        return new JsonPrimitive(dateFormat.format(updatedDate));
    }
}

