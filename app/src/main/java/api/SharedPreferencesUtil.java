package api;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SharedPreferencesUtil {

    private static SharedPreferences sharedPreferences = null;

    public static SharedPreferences getEncryptedSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            try {
                MasterKey masterKey = new MasterKey.Builder(context)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();

                sharedPreferences = EncryptedSharedPreferences.create(
                        context,
                        "user_settings_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return sharedPreferences;
    }

    public SharedPreferencesUtil() {
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token_key", token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        return sharedPreferences.getString("token_key", null);
    }

    public static void saveUserId(Context context, Long userId) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("user_id_key", userId);
        editor.apply();
    }

    public static Long getUserId(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        return sharedPreferences.getLong("user_id_key", -1);
    }

    public static void deleteToken(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token_key");
        editor.apply();
    }

    public static void deleteUserId(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id_key");
        editor.apply();
    }


    public static void saveRutina(Context context, String rutina) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("rutina_key", rutina);
        editor.apply();
    }

    public static String getRutina(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        return sharedPreferences.getString("rutina_key", null);
    }

    public static void deleteRutina(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("rutina_key");
        editor.apply();
    }

    public static void saveGimnasio(Context context, int idGimnasio) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gimnasioid_key", idGimnasio);
        editor.apply();
    }

    public static int getGimnasio(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        return sharedPreferences.getInt("gimnasioid_key", 0);
    }

    public static void deleteGimnasio(Context context) {
        SharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("gimnasioid_key");
        editor.apply();
    }
}

