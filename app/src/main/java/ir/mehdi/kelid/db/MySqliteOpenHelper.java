package ir.mehdi.kelid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.HashMap;


import ir.mehdi.kelid.KelidApplication;
import ir.mehdi.kelid.model.Property;
import ir.mehdi.kelid.utils.FileUtils;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Iman on 7/28/2016.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    final public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final String table_name = "userjob";
    public static final String search_history_table_name = "search_history";
    public static final String table_image_name = "userjobimage";
    public static final String table_payment_name = "userjobpayment";
    private static MySqliteOpenHelper instance;
    public HashMap<Long, Property> historyJobs = new HashMap<>();
    public HashMap<Long, Property> loadedPropertys = new HashMap<>();
    public HashMap<Long, Property> myJobs = new HashMap<>();
    public HashMap<Long, Property> myJobsremote = new HashMap<>();
    public HashMap<Long, Property> allJobs = new HashMap<>();
    public HashMap<Long, Property> bookmarkJobs = new HashMap<>();


    public static MySqliteOpenHelper getInstance() {
        if (instance == null) {
            instance = new MySqliteOpenHelper(KelidApplication.applicationContext);
        }
        return instance;

    }


    public static final String JOB_TABLE = "CREATE TABLE userjob (\n" +
            "  local_id integer primary key AUTOINCREMENT,\n" +
            "  remote_id integer,\n" +
            "created DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "  bookmark integer,\n" +
            "  myjob integer,\n" +
            //field for compare
            "  name text,\n" +
            "  title text,\n" +
            "  worktime text,\n" +
            "  adversText text,\n" +
            "  web text,\n" +
            "  email text,\n" +
            "  telegram text,\n" +
            "  address text,\n" +
            "  descritpion text,\n" +
            "  tel text,\n" +
            "  mobile text,\n" +
            "  province integer,\n" +
            "  city integer,\n" +
            "  region_id integer,\n" +
            "  bike integer,\n" +
            "  noon integer,\n" +
            "  namevisible integer,\n" +
            "  moorning integer,\n" +
            "  evening integer,\n" +
            "  boarding integer,\n" +
            "  cardReader integer,\n" +
            "  node_id integer,\n" +


            //field for compare
            "  send_name text,\n" +
            "  send_title text,\n" +
            "  send_worktime text,\n" +
            "  send_adversText text,\n" +
            "  send_web text,\n" +
            "  send_email text,\n" +
            "  send_telegram text,\n" +
            "  send_address text,\n" +
            "  send_descritpion text,\n" +
            "  send_tel text,\n" +
            "  send_mobile text,\n" +
            "  send_province integer,\n" +
            "  send_city integer,\n" +
            "  send_region_id integer,\n" +
            "  send_bike integer,\n" +
            "  send_noon integer,\n" +
            "  send_namevisible integer,\n" +
            "  send_moorning integer,\n" +
            "  send_evening integer,\n" +
            "  send_boarding integer,\n" +
            "  send_cardReader integer,\n" +
            "  send_node_id integer,\n" +
            "  qr_code text," +
            "  status integer,\n" +
            "  totalvisited integer,\n" +
            "  day1cnt integer,\n" +
            "  day2cnt integer,\n" +
            "  day3cnt integer,\n" +
            "  day4cnt integer\n" +

            "  )";
    public static final String search_history_table_create = "CREATE TABLE search_history (\n" +
            "  id integer primary key AUTOINCREMENT,\n" +
            "  name text\n" +
            "  )";
    public static final String JOB_Image_TABLE = "CREATE TABLE "+table_image_name+
            " ( local_id integer,\n" +
            "id integer primary key AUTOINCREMENT," +
            "local_name text," +
            "remote_name text," +
            "main number DEFAULT 0," +
            "deleted number DEFAULT 0" +
            "  )";
    public static final String JOB_Payment_TABLE = "CREATE TABLE "+table_payment_name+
            " ( job_id integer,\n" +
            "payDate text," +
            "festivalDate text," +
            "type number DEFAULT 0 )";

    private MySqliteOpenHelper(Context context) {
        super(context, "localdb", null, 1);
        loadProperty();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JOB_TABLE);
        db.execSQL(JOB_Image_TABLE);
        db.execSQL(JOB_Payment_TABLE);
        db.execSQL(search_history_table_create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String[] getHistorySearch() {
        String query = "select * from " + search_history_table_name;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String[] text = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            text[i] = cursor.getString(cursor.getColumnIndex("name"));
            cursor.moveToNext();
        }
        return text;

    }

//    public void insertHistory(String text) {
//        ContentValues cv = new ContentValues();
//        cv.put("name", text); //These Fields should be your String values of actual column names
//        int num = getWritableDatabase().update(search_history_table_name, cv, "name='" + text + "'", null);
//        if (num == 0)
//            getWritableDatabase().insert(search_history_table_name, null, cv);
//    }

    public void loadProperty() {
        historyJobs = new HashMap<>();
        myJobs = new HashMap<>();
        myJobsremote = new HashMap<>();
        allJobs = new HashMap<>();
        bookmarkJobs = new HashMap<>();
        String query = "select * from " + table_name;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Property[] userJobs = new Property[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            userJobs[i] = new Property();


            userJobs[i].desc = cursor.getString(cursor.getColumnIndex("descritpion"));

            try {
                userJobs[i].date = dateFormat.parse(cursor.getString(cursor.getColumnIndex("created")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            userJobs[i].local_id = cursor.getLong(cursor.getColumnIndex("local_id"));
            userJobs[i].remote_id = cursor.getLong(cursor.getColumnIndex("remote_id"));
            userJobs[i].advers = cursor.getString(cursor.getColumnIndex("adversText"));
            userJobs[i].title = cursor.getString(cursor.getColumnIndex("title"));
            userJobs[i].qr_code = cursor.getString(cursor.getColumnIndex("qr_code"));
            userJobs[i].desc = cursor.getString(cursor.getColumnIndex("descritpion"));
            userJobs[i].address = cursor.getString(cursor.getColumnIndex("address"));
            userJobs[i].city = cursor.getInt(cursor.getColumnIndex("city"));
            userJobs[i].region = cursor.getInt(cursor.getColumnIndex("region_id"));
            userJobs[i].email = cursor.getString(cursor.getColumnIndex("email"));
            userJobs[i].mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            userJobs[i].tel = cursor.getString(cursor.getColumnIndex("tel"));
            userJobs[i].telegram = cursor.getString(cursor.getColumnIndex("telegram"));
            userJobs[i].name = cursor.getString(cursor.getColumnIndex("name"));
            userJobs[i].dateString = cursor.getString(cursor.getColumnIndex("created"));
            userJobs[i].bookmark = cursor.getInt(cursor.getColumnIndex("bookmark"));
            userJobs[i].nodeid = cursor.getInt(cursor.getColumnIndex("node_id"));

            userJobs[i].status = cursor.getInt(cursor.getColumnIndex("status"));
            userJobs[i].moorning = cursor.getInt(cursor.getColumnIndex("moorning")) == 1;
            userJobs[i].bike = cursor.getInt(cursor.getColumnIndex("bike")) == 1;
            userJobs[i].noon = cursor.getInt(cursor.getColumnIndex("noon")) == 1;
            userJobs[i].namevisible = cursor.getInt(cursor.getColumnIndex("namevisible")) == 1;
            userJobs[i].cardReader = cursor.getInt(cursor.getColumnIndex("cardReader")) == 1;
            userJobs[i].boarding = cursor.getInt(cursor.getColumnIndex("boarding")) == 1;
            userJobs[i].evening = cursor.getInt(cursor.getColumnIndex("evening")) == 1;
            userJobs[i].address = cursor.getString(cursor.getColumnIndex("address"));


            userJobs[i].send_desc = cursor.getString(cursor.getColumnIndex("send_descritpion"));
            if (userJobs[i].send_desc == null)
                userJobs[i].send_desc = "";
            userJobs[i].send_advers = cursor.getString(cursor.getColumnIndex("send_adversText"));
            if (userJobs[i].send_advers == null)
                userJobs[i].send_advers = "";
            userJobs[i].send_title = cursor.getString(cursor.getColumnIndex("send_title"));
            if (userJobs[i].send_title == null)
                userJobs[i].send_title = "";
            userJobs[i].send_desc = cursor.getString(cursor.getColumnIndex("send_descritpion"));
            if (userJobs[i].send_desc == null)
                userJobs[i].send_desc = "";
            userJobs[i].send_address = cursor.getString(cursor.getColumnIndex("send_address"));
            if (userJobs[i].send_address == null)
                userJobs[i].send_address = "";
            userJobs[i].send_city = cursor.getInt(cursor.getColumnIndex("send_city"));

            userJobs[i].send_region = cursor.getInt(cursor.getColumnIndex("send_region_id"));

            userJobs[i].send_email = cursor.getString(cursor.getColumnIndex("send_email"));
            if (userJobs[i].send_email == null)
                userJobs[i].send_email = "";
            userJobs[i].send_mobile = cursor.getString(cursor.getColumnIndex("send_mobile"));
            if (userJobs[i].send_mobile == null)
                userJobs[i].send_mobile = "";
            userJobs[i].send_tel = cursor.getString(cursor.getColumnIndex("send_tel"));
            if (userJobs[i].send_tel == null)
                userJobs[i].send_tel = "";
            userJobs[i].send_telegram = cursor.getString(cursor.getColumnIndex("send_telegram"));
            if (userJobs[i].send_telegram == null)
                userJobs[i].send_telegram = "";
            userJobs[i].send_name = cursor.getString(cursor.getColumnIndex("send_name"));
            if (userJobs[i].send_name == null)
                userJobs[i].send_name = "";
            userJobs[i].send_nodeid = cursor.getInt(cursor.getColumnIndex("send_node_id"));

            userJobs[i].send_moorning = cursor.getInt(cursor.getColumnIndex("send_moorning")) == 1;

            userJobs[i].send_bike = cursor.getInt(cursor.getColumnIndex("send_bike")) == 1;

            userJobs[i].send_noon = cursor.getInt(cursor.getColumnIndex("send_noon")) == 1;
            userJobs[i].send_namevisible = cursor.getInt(cursor.getColumnIndex("send_namevisible")) == 1;

            userJobs[i].send_cardReader = cursor.getInt(cursor.getColumnIndex("send_cardReader")) == 1;

            userJobs[i].send_boarding = cursor.getInt(cursor.getColumnIndex("send_boarding")) == 1;

            userJobs[i].send_evening = cursor.getInt(cursor.getColumnIndex("send_evening")) == 1;

            userJobs[i].send_address = cursor.getString(cursor.getColumnIndex("send_address"));
            if (userJobs[i].send_address == null)
                userJobs[i].send_address = "";


            userJobs[i].totalVisited = cursor.getLong(cursor.getColumnIndex("totalvisited"));
            userJobs[i].day1Cnt = cursor.getLong(cursor.getColumnIndex("day1cnt"));
            userJobs[i].day2Cnt = cursor.getLong(cursor.getColumnIndex("day2cnt"));
            userJobs[i].day3Cnt = cursor.getLong(cursor.getColumnIndex("day3cnt"));
            userJobs[i].day4Cnt = cursor.getLong(cursor.getColumnIndex("day4cnt"));
//
//            userJob.noon = preferences.getBoolean("noon", false);//noon, evening, moorning, boarding, bike, cardReader;
//            userJob.evening = preferences.getBoolean("evening", false);//noon, evening, moorning, boarding, bike, cardReader;
//            userJob.moorning = preferences.getBoolean("moorning", false);//noon, evening, moorning, boarding, bike, cardReader;
//            userJob.boarding = preferences.getBoolean("boarding", false);//noon, evening, moorning, boarding, bike, cardReader;
//            userJob.bike = preferences.getBoolean("bike", false);//noon, evening, moorning, boarding, bike, cardReader;
//            userJob.cardReader = preferences.getBoolean("cardReader", false);//noon, evening, moorning, boarding, bike, cardReader;
//

            userJobs[i].myjob = cursor.getInt(cursor.getColumnIndex("myjob"));
            allJobs.put(userJobs[i].local_id, userJobs[i]);
            if (userJobs[i].myjob == 1) {
                userJobs[i].setLoaded(true);
                myJobs.put(userJobs[i].local_id, userJobs[i]);
                if (userJobs[i].remote_id != 0)
                    myJobsremote.put(userJobs[i].remote_id, userJobs[i]);
            } else if (userJobs[i].bookmark == 1)
                bookmarkJobs.put(userJobs[i].remote_id, userJobs[i]);
            else
                historyJobs.put(userJobs[i].remote_id, userJobs[i]);


            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        query = "select * from " + table_image_name;
        db = getWritableDatabase();
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            long local_id = cursor.getLong(cursor.getColumnIndex("local_id"));
            Property userJob = allJobs.get(local_id);
            if (userJob == null) {
                cursor.moveToNext();
                continue;
            }
            String localname = cursor.getString(cursor.getColumnIndex("local_name"));
            String remotename = cursor.getString(cursor.getColumnIndex("remote_name"));
            int main = cursor.getInt(cursor.getColumnIndex("main"));
            int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            userJob.addImage(id, (localname == null) ? localname : FileUtils.getInstance().getImageFile(localname).getAbsolutePath(), (remotename == null || remotename.equals("-1")) ? null : remotename, main, deleted);


            cursor.moveToNext();
        }
        cursor.close();

        query = "select * from " + table_payment_name;
        db = getWritableDatabase();
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            long local_id = cursor.getLong(cursor.getColumnIndex("job_id"));
            Property userJob = allJobs.get(local_id);
            Property.Payment payment = new Property.Payment();
            if (userJob == null) {
                cursor.moveToNext();
                continue;
            }
            try {
                payment.payDate = dateFormat.parse(cursor.getString(cursor.getColumnIndex("payDate")));
                payment.festivalDate = dateFormat2.parse(cursor.getString(cursor.getColumnIndex("festivalDate")));

            } catch (Exception e) {
                e.printStackTrace();
            }
            payment.type = cursor.getInt(cursor.getColumnIndex("type"));
            userJob.payments.add(payment);
            cursor.moveToNext();
        }
        cursor.close();

        db.close();


    }

    public long insertORUpdateProperty(Property userJob) {
        ContentValues contentValues = new ContentValues();
        if (userJob.mobile != null) contentValues.put("mobile", userJob.mobile);
        if (userJob.remote_id != 0) contentValues.put("remote_id", userJob.remote_id);

        if (userJob.tel != null) contentValues.put("tel", userJob.tel);
        if (userJob.address != null) contentValues.put("address", userJob.tel);
        if (userJob.email != null) contentValues.put("email", userJob.email);
        if (userJob.name != null) contentValues.put("name", userJob.name);
        if (userJob.region != 0) contentValues.put("region_id", userJob.region);
        if (userJob.address != null) contentValues.put("address", userJob.address);
        if (userJob.dateString != null) contentValues.put("created", userJob.dateString);
        if (userJob.title != null) contentValues.put("title", userJob.title);
        if (userJob.qr_code != null) contentValues.put("qr_code", userJob.qr_code);
        if (userJob.city != 0) contentValues.put("city", userJob.city);
        if (userJob.desc != null) contentValues.put("descritpion", userJob.desc);
        if (userJob.advers != null) contentValues.put("adversText", userJob.advers);
        if (userJob.nodeid != 0) contentValues.put("node_id", userJob.nodeid);
        if (userJob.telegram != null) contentValues.put("telegram", userJob.telegram);
        if (userJob.date != null) contentValues.put("created", dateFormat.format(userJob.date));


        if (userJob.send_mobile != null) contentValues.put("send_mobile", userJob.send_mobile);

        if (userJob.send_tel != null) contentValues.put("send_tel", userJob.send_tel);
        if (userJob.send_address != null) contentValues.put("send_address", userJob.send_tel);
        if (userJob.send_email != null) contentValues.put("send_email", userJob.send_email);
        if (userJob.send_name != null) contentValues.put("send_name", userJob.send_name);
        if (userJob.send_region != 0) contentValues.put("send_region_id", userJob.send_region);
        if (userJob.send_address != null) contentValues.put("send_address", userJob.send_address);
        if (userJob.send_title != null) contentValues.put("send_title", userJob.send_title);
        if (userJob.send_city != 0) contentValues.put("send_city", userJob.send_city);
        if (userJob.send_desc != null) contentValues.put("send_descritpion", userJob.send_desc);
        if (userJob.send_advers != null) contentValues.put("send_adversText", userJob.send_advers);
        if (userJob.send_nodeid != 0) contentValues.put("send_node_id", userJob.send_nodeid);
        if (userJob.send_telegram != null)
            contentValues.put("send_telegram", userJob.send_telegram);

        if (userJob.totalVisited != 0) contentValues.put("totalvisited", userJob.totalVisited);
        if (userJob.day1Cnt != 0) contentValues.put("day1Cnt", userJob.day1Cnt);
        if (userJob.day2Cnt != 0) contentValues.put("day2Cnt", userJob.day2Cnt);
        if (userJob.day3Cnt != 0) contentValues.put("day3Cnt", userJob.day3Cnt);
        if (userJob.day4Cnt != 0) contentValues.put("day4Cnt", userJob.day4Cnt);
        contentValues.put("bookmark", userJob.bookmark);
        contentValues.put("status", userJob.status);
        contentValues.put("moorning", (userJob.moorning) ? 1 : 0);
        contentValues.put("bike", (userJob.bike) ? 1 : 0);
        contentValues.put("noon", (userJob.noon) ? 1 : 0);
        contentValues.put("namevisible", (userJob.namevisible) ? 1 : 0);
        contentValues.put("cardReader", (userJob.cardReader) ? 1 : 0);
        contentValues.put("boarding", (userJob.boarding) ? 1 : 0);
        contentValues.put("evening", (userJob.evening) ? 1 : 0);

        contentValues.put("send_moorning", (userJob.send_moorning) ? 1 : 0);
        contentValues.put("send_bike", (userJob.send_bike) ? 1 : 0);
        contentValues.put("send_noon", (userJob.send_noon) ? 1 : 0);
        contentValues.put("send_namevisible", (userJob.send_namevisible) ? 1 : 0);
        contentValues.put("send_cardReader", (userJob.send_cardReader) ? 1 : 0);
        contentValues.put("send_boarding", (userJob.send_boarding) ? 1 : 0);
        contentValues.put("send_evening", (userJob.send_evening) ? 1 : 0);


        contentValues.put("myjob", userJob.myjob);

//        if (userJob.businesscardpathlocal != null)
//            contentValues.put("businesscardpathlocal", userJob.businesscardpathlocal);
//        if (userJob.logopath != null) contentValues.put("logopath", userJob.logopath.toString());
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (userJob.local_id == 0) {
            long insert = writableDatabase.insert(table_name, null, contentValues);
            userJob.local_id = insert;

        } else {
            writableDatabase.update(table_name, contentValues, "local_id=" + userJob.local_id, null);

        }
//        if (userJob.myjob == 1) {
        insertOrUpdateUserImages(userJob);
        insertOrUpdateUserPayment(userJob);
//        }


        writableDatabase.close();
        loadProperty();
        return userJob.local_id;
//        userJob.sync();
    }


    private void insertOrUpdateUserPayment(Property userJob) {
        if (userJob.images.size() == 0)
            return;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(table_payment_name, "job_id=" + userJob.local_id, null);
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < userJob.payments.size(); i++) {
            Property.Payment image = userJob.payments.get(i);
            if (image.payDate != null)
                contentValues.put("payDate", dateFormat.format(image.payDate));
            if (image.festivalDate != null)
                contentValues.put("festivalDate", dateFormat2.format(image.festivalDate));
            contentValues.put("type", image.type);
            contentValues.put("job_id", userJob.local_id);
            writableDatabase.insert(table_payment_name, null, contentValues);

        }
        writableDatabase.close();


    }

    private void insertOrUpdateUserImages(Property userJob) {
        if (userJob.payments.size() == 0)
            return;
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        boolean hasmain = false;
        for (int i = 0; i < userJob.images.size(); i++) {
            hasmain = userJob.images.get(i).main && !userJob.images.get(i).deleted;
        }
        if (!hasmain)
            userJob.images.get(0).main = true;


        boolean a;
        for (int i = 0; i < userJob.images.size(); i++) {
            a = false;
            Property.Image image = userJob.images.get(i);
            if (image.main) {
                contentValues.put("main", 1);
            } else {
                contentValues.put("main", 0);
            }
            if (image.remotename != null && !image.remotename.equals("null")) {
                contentValues.put("remote_name", Utils.getName(image.remotename));
                a = true;
            }
            if (image.localname != null && !image.localname.equals("null")) {
                if (userJob.myjob == 1 && !FileUtils.getInstance().existInDefaultFoder(image.localname) && !image.deleted) {
                    image.localname = FileUtils.getInstance().copyToDefaultFoder(image.localname);
                }
                contentValues.put("local_name", Utils.getName(image.localname));
                a = true;
            }
            if (a) {
                contentValues.put("local_id", userJob.local_id);
                contentValues.put("deleted", (image.deleted) ? 1 : 0);
                if (image.id == 0) {
                    long insert = writableDatabase.insert(table_image_name, null, contentValues);
                    image.id = insert;
                } else {
                    writableDatabase.update(table_image_name, contentValues, "id=" + image.id, null);
                }

            }
        }


        writableDatabase.close();


    }

//    public void updateUserJob(UserJob userJob) {
//        ContentValues contentValues = new ContentValues();
//        if (userJob.mobile != null) contentValues.put("mobile", userJob.mobile);
//        if (userJob.remote_id != 0) contentValues.put("remote_id", userJob.remote_id);
//        if (userJob.tel != null) contentValues.put("tel", userJob.tel);
//        if (userJob.email != null) contentValues.put("email", userJob.mobile);
//        if (userJob.name != null) contentValues.put("name", userJob.mobile);
//        if (userJob.region != 0) contentValues.put("region_id", userJob.mobile);
//        if (userJob.address != null) contentValues.put("address", userJob.mobile);
//        if (userJob.dateString != null) contentValues.put("created", userJob.dateString);
//        if (userJob.title != null) contentValues.put("title", userJob.mobile);
//        if (userJob.qr_code != null) contentValues.put("qr_code", userJob.qr_code);
//        if (userJob.city != 0) contentValues.put("city", userJob.mobile);
//        if (userJob.desc != null) contentValues.put("descritpion", userJob.mobile);
//        if (userJob.advers != null) contentValues.put("adversText", userJob.mobile);
////        if(userJob.dateString==null )
////        {
////            contentValues.put("created", new Date());
////        }else
////        {
////            contentValues.put("created", userJob.bookmark);
////        }
//        contentValues.put("bookmark", userJob.bookmark);
//        contentValues.put("status", userJob.status);
//        SQLiteDatabase writableDatabase = getWritableDatabase();
//
//        writableDatabase.update(table_name, contentValues, "local_id=" + userJob.local_id, null);
//
//        writableDatabase.close();
//
//
////        if (userJob.remote_id != 0)
////            remoteUserJob.put(userJob.remote_id, userJob);
//    }

    public void delete(Property userJob) {
        delete(userJob.local_id);
        historyJobs.remove(userJob.remote_id);
        allJobs.remove(userJob.local_id);
        myJobs.remove(userJob.local_id);

//        if (userJob.remote_id != 0)
//            remoteUserJob.remove(userJob.remote_id);


    }

    public void delete(long id) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(table_name, "local_id=" + id, null);
        writableDatabase.close();

    }

    public void deleteHistory() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(table_name, "(myjob is null or myjob<>1) and (bookmark is null or bookmark<>1)", null);
        writableDatabase.close();
        loadProperty();
    }

    public void deleteBookmark() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(table_name, " bookmark =1", null);
        writableDatabase.close();
        loadProperty();
    }
}
