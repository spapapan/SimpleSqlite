package com.app.<YOUR_PACKAGE_NAME>;
 
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleSqlite {

    public static <T> ArrayList<T> get(SQLiteDatabase database, String query, Class<T> classOfT)
    {
        ArrayList<T> list = new ArrayList<>();
        Cursor contentInfoCursor = database.rawQuery(query, null);

        try {
            if (contentInfoCursor != null) {
                if (contentInfoCursor.getCount()>0)
                {
                    contentInfoCursor.moveToFirst();

                    String[] columnNames = contentInfoCursor.getColumnNames();

                    //Check for inconsistencies
                    checkSimpleSqliteInconsistencies(query,columnNames,classOfT);

                    do
                    {
                        Map<String, Object> itemsMapList = new HashMap<>();

                        for (int index=0; index<columnNames.length; index++)
                        {
                            int fieldType = contentInfoCursor.getType(contentInfoCursor.getColumnIndex(columnNames[index]));

                            if (fieldType == Cursor.FIELD_TYPE_STRING)
                                itemsMapList.put(columnNames[index],contentInfoCursor.getString(contentInfoCursor.getColumnIndex(columnNames[index])));
                            else if (fieldType == Cursor.FIELD_TYPE_INTEGER)
                                itemsMapList.put(columnNames[index],contentInfoCursor.getInt(contentInfoCursor.getColumnIndex(columnNames[index])));
                            else if (fieldType == Cursor.FIELD_TYPE_FLOAT)
                                itemsMapList.put(columnNames[index],contentInfoCursor.getFloat(contentInfoCursor.getColumnIndex(columnNames[index])));
                            else if (fieldType == Cursor.FIELD_TYPE_BLOB)
                                itemsMapList.put(columnNames[index],contentInfoCursor.getBlob(contentInfoCursor.getColumnIndex(columnNames[index])));
                        }

                        if (itemsMapList.size()>0)
                        {
                            Object gnrObj = jsonToGenericObject(new JSONObject(itemsMapList).toString(),classOfT);
                            list.add((T)gnrObj);
                        }
                    }
                    while (contentInfoCursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            contentInfoCursor.close();
        }

        return list;
    }

    public static <T> void checkSimpleSqliteInconsistencies(String query, String[] columnNames, Class<T> classOfT)
    {
        Field[] genericClassFields = classOfT.getFields();
        boolean isError = false;

        if (genericClassFields.length != columnNames.length){
            isError=true;
            Log.wtf("SimpleSqlite","Table doesn't have same items as " + classOfT.getName());
        }

        for (String columnName : columnNames)
        {
            boolean hasFieldSameAsColumnName = false;
            for (Field genericClassField : genericClassFields) {
                if (genericClassField.getName().equals(columnName)) {
                    hasFieldSameAsColumnName = true;
                    break;
                }
            }
            if (!hasFieldSameAsColumnName){
                isError=true;
                Log.wtf("SimpleSqlite","-" + columnName + "- doesn't exist in " + classOfT.getName());
            }
        }

        if (isError)
            Log.wtf("SimpleSqlite","Query : \n " + query);
    }

    public static <T> T jsonToGenericObject(String jsonText, Class<T> classOfT)
    {
        JsonParser parser = new JsonParser();
        JsonElement mJson =  parser.parse(jsonText);
        Gson gson = new Gson();

        return  gson.fromJson(mJson, classOfT);
    }
}
