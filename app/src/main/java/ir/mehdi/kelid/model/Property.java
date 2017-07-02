package ir.mehdi.kelid.model;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import ir.mehdi.kelid.Constant;
import ir.mehdi.kelid.service.VolleyService;
import ir.mehdi.kelid.ui.AddPropetyActivity;
import ir.mehdi.kelid.utils.FileUtils;
import ir.mehdi.kelid.utils.Utils;

/**
 * Created by Mahdi on 15/07/2016.
 */
public class Property
        implements Constant, Comparable<Property>, Serializable {
    public boolean sendignFirstPhoto = false;
    public Vector<PropertyDetail> details = new Vector<>();
    public String name, title, desc, email, avenue, street, address, tel, mobile, telegram;
    public int totalTabaghe, totalVahed, vahed, tabaghe, hashieh,
            rooms, tarakom, masahat, zirBana, arseZamin, omrSakhteman,      ab, gaz, bargh, region, city, nodeid;
    public LatLng location;

    public String send_avenue, send_street;
    public int send_totalTabaghe, send_totalVahed, send_vahed, send_tabaghe, send_hashieh,
            send_rooms, send_tarakom, send_metraj, send_zirBana, send_arseZamin, send_omrSakhteman, send_ab, send_gaz, send_bargh;


    public String send_title = "", send_name = "", send_desc = "", send_email = "", send_address = "";
    public String send_tel = "", send_mobile = "", send_telegram = "";
    public int send_region, send_city, send_nodeid;


    public int getImageCount() {
        int cnt = 0;
        for (int i = 0; i < images.size(); i++) {
            if (!images.get(i).deleted)
                cnt++;

        }
        return cnt;
    }

    public Vector<Image> getShowImage() {
        Vector<Image> images = new Vector<>();

        for (int i = 0; i < this.images.size(); i++) {
            if (!this.images.get(i).deleted)
                images.add(this.images.get(i));

        }
        return images;

    }

    public synchronized void sendPhotos()
    {

        if (remote_id != 0) {
            for (int i = 0; i < images.size(); i++) {
                Image image = images.get(i);
                if(!image.sending && image.remote_Id==0)
                    VolleyService.getInstance().sendPhoto(this, image);
            }
        }
        else if (!sendignFirstPhoto && images.size()>0) {
            VolleyService.getInstance().sendPhoto(AddPropetyActivity.property, images.get(0));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Property) {

            return remote_id == ((Property) obj).remote_id;
        } else
            return false;

    }

//    public String qr_code;

    public boolean validToSend() {
        return ((name != null && name.length() > 0) && (mobile != null && mobile.length() > 0) && (desc != null && desc.length() > 0) && (title != null && title.length() > 0)
                && nodeid != 0);
    }

    public boolean loaded = false;

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;

    }

    public boolean isLoaded() {
        return loaded;
    }

    public long local_id, remote_id;
    public long totalVisited, day1Cnt, day2Cnt, day3Cnt, day4Cnt;
    public String token;
    public int status = DRAFT_STATUS;


    public Vector<Image> images = new Vector<>();


    public String dateString;
    public Date date;

    public int bookmark;

    public int myproperty;//1 pishhnevis,2 taeed,-1 reject
    public int reportindex;
    public String reporttext;


    public Image addImage(int id, String localname, String url, int main, int deleted) {
        Image a = new Image();
        a.deleted = deleted == 1;
        a.id = id;
        a.localname = localname;
        if (url != null) {
            a.remotename = VolleyService.IMAGE_PATH + url;
            a.thumbnail = VolleyService.IMAGE_thumbnail_PATH + url;

        }

        a.main = main == 1;
        if (a.main) {
            for (int i = 0; i < images.size(); i++) {
                images.get(i).main = false;
            }
        }
        images.add(a);
        return a;
    }

    public void setServerData() {
        send_region = region;
        send_city = city;
        send_nodeid = nodeid;
        send_tel = tel;
        send_mobile = mobile;
        send_telegram = telegram;
        send_name = name;
        send_title = title;
        send_desc = desc;
        send_email = email;
        send_avenue = avenue;
        send_street = street;
        send_address = address;
        send_totalTabaghe = totalTabaghe;
        send_totalVahed = totalVahed;
        send_vahed = vahed;
        send_tabaghe = tabaghe;
        send_hashieh = hashieh;
        send_rooms = rooms;
        send_tarakom = tarakom;
        send_metraj = masahat;
        send_zirBana = zirBana;
        send_arseZamin = arseZamin;
        send_omrSakhteman = omrSakhteman;
//        send_samayeshi = samayeshi;
//        send_garmayeshi = garmayeshi;
//        send_kaf = kaf;
//        send_divar = divar;
//        send_nama = nama;
//        send_cabinet = cabinet;
        send_ab = ab;
        send_gaz = gaz;
        send_bargh = bargh;


    }

    public boolean isChanged() {

        boolean change = false;
        if (title != null && !title.trim().equals(send_title.trim())) {
            change = true;
        }
        if (avenue != null && !avenue.trim().equals(send_avenue.trim())) {
            change = true;
        }
        if (street != null && !street.trim().equals(send_street.trim())) {
            change = true;
        }
        if (name != null && !name.trim().equals(send_name.trim())) {
            change = true;
        }
        if (desc != null && !desc.trim().equals(send_desc.trim())) {
            change = true;
        }

        if (email != null && !email.trim().equals(send_email.trim())) {
            change = true;
        }
        if (address != null && !address.trim().equals(send_address.trim())) {
            change = true;
        }
        if (tel != null && !tel.trim().equals(send_tel.trim())) {
            change = true;
        }
        if (mobile != null && !mobile.trim().equals(send_mobile.trim())) {
            change = true;
        }
        if (telegram != null && !telegram.trim().equals(send_telegram.trim())) {
            change = true;
        }

        if (send_totalTabaghe != totalTabaghe) {
            change = true;
        }
        if (send_totalVahed != totalVahed) {
            change = true;
        }
        if (send_vahed != vahed) {
            change = true;
        }
        if (send_tabaghe != tabaghe) {
            change = true;
        }
        if (send_hashieh != hashieh) {
            change = true;
        }
        if (send_rooms != rooms) {
            change = true;
        }
        if (send_tarakom != tarakom) {
            change = true;
        }
        if (send_metraj != masahat) {
            change = true;
        }
        if (send_zirBana != zirBana) {
            change = true;
        }
        if (send_arseZamin != arseZamin) {
            change = true;
        }
        if (send_omrSakhteman != omrSakhteman) {
            change = true;
        }
//        if (send_samayeshi != samayeshi) {
//            change = true;
//        }
//        if (send_garmayeshi != garmayeshi) {
//            change = true;
//        }
//        if (send_kaf != kaf) {
//            change = true;
//        }
//        if (send_divar != divar) {
//            change = true;
//        }
//        if (send_nama != nama) {
//            change = true;
//        }
//        if (send_cabinet != cabinet) {
//            change = true;
//        }
        if (send_ab != ab) {
            change = true;
        }
        if (send_gaz != gaz) {
            change = true;
        }
        if (send_bargh != bargh) {
            change = true;
        }
        if (send_region != region) {
            change = true;
        }
        if (send_city != city) {
            change = true;
        }
        if (send_nodeid != nodeid) {
            change = true;
        }

        for (int i = 0; i < images.size(); i++) {
            if ((images.get(i).deleted && images.get(i).remotename != null) || images.get(i).remotename == null) {
                change = true;
            }
        }

        return change;
    }

    public void setPieNum(Vector<Utils.VisitedDate> visitedDates1) {
        Date currentDate = new Date();
        for (int i = 0; i < visitedDates1.size(); i++) {
            Utils.VisitedDate visitedDate = visitedDates1.get(i);
            long diff = Utils.getZeroTimeDate(currentDate).getTime() - Utils.getZeroTimeDate(visitedDate.date).getTime();

            float dayCount = (float) diff / (24 * 60 * 60 * 1000);
            if (dayCount == 0) {
                day1Cnt = visitedDate.visited;
            } else if (dayCount == 1) {
                day2Cnt = visitedDate.visited;
            } else if (dayCount == 2) {
                day3Cnt = visitedDate.visited;
            } else if (dayCount == 3) {
                day4Cnt = visitedDate.visited;
            }
        }

    }

    @Override
    public int compareTo(Property o) {
        if (date == null || o.date == null)
            return 1;
        return o.date.compareTo(date);
    }

//    @Override
//    public int compareTo(Userproperty o) {
//        if(date==null || o==null || o.date==null)
//            return 1;
//        return date.compareTo(o.date);
//    }

    public static class Image implements Serializable, Comparable<Image> {
        public long id;
        public boolean sending = false;
        public String orginalPath;
        public int remote_Id;
        public boolean deleted;
        public String localname;
        public String remotename;
        public String thumbnail;
        public boolean main;
        public File localImageFile;
        public ProgressBar uploadProgressBar;
        public View mainView;
        public ImageView failedImageView;
        public Bitmap bitmap;


        @Override
        public int compareTo(Image o) {
            return (main) ? -1 : (o.main) ? 1 : -1;
        }

        @Override
        public boolean equals(Object obj) {
            Image a = (Image) obj;
            return (orginalPath != null && a.orginalPath != null && a.orginalPath.equals(orginalPath)) || (a != null && a.localname != null && localname != null && localname.equals(a.localname)) || (a.remote_Id == remote_Id && a.remote_Id != 0);
        }
    }

    public static class Payment implements Serializable {
        public Date payDate;
        public int type;
        public Date festivalDate;


    }

    public void fillSummeryFromJsonObject(JSONObject propertyObject1) {

        try {
            remote_id = propertyObject1.getInt("id");
            title = propertyObject1.getString("title");
            mobile = propertyObject1.getString("mobile");

            try {
                region = propertyObject1.getInt("region_id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            String image = propertyObject1.getString("img");
            if (image != null && image.length() > 0 && !image.equals("null")) {
                addImage(0, null, image, 1, 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addImageFromJsonObject(JSONObject object, boolean saveImages) {
        try {
            int main = 0;

            String nameFile = object.getString("nameFile");
            if (nameFile != null && nameFile.equals("null"))
                nameFile = null;
            if (nameFile != null) {
                String photo = object.getString("photo_id");
                String idFile = null;
                try {
                    idFile = object.getString("idFile");
                } catch (Exception e) {
                    e.printStackTrace();

                }
                if (photo != null && (idFile == null || photo.equals(idFile))) {// if (photo != null && photo.equals(object.getString("idFile"))) {
                    main = 1;
                }
            }
            if (saveImages) {
                String local_name = object.getString("local_name");
                if (local_name != null && local_name.equals("null"))
                    local_name = nameFile;
                local_name = (local_name == null) ? null : FileUtils.getInstance().getImageFile(local_name).getAbsolutePath();
                addImage(0, local_name, (nameFile == null) ? null : nameFile, main, 0);
            } else if (nameFile != null)
                addImage(0, null, (nameFile == null) ? null : nameFile, main, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillFromJsonObject(JSONObject object, boolean saveImage) {
        try {
            String da = object.getString("created");

            String format = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(da);
            name = object.getString("name");
            status = object.getInt("status");
            if (name != null && name.toLowerCase().equals("null"))
                name = null;
            title = object.getString("title");
            if (title != null && title.toLowerCase().equals("null"))
                title = null;
            telegram = object.getString("telegram");
            if (telegram != null && telegram.toLowerCase().equals("null"))
                telegram = null;
            address = object.getString("address");
            if (address != null && address.toLowerCase().equals("null"))
                address = null;
            email = object.getString("email");
            if (email != null && email.toLowerCase().equals("null"))
                email = null;
//                    email = object.getString("worktime");
            desc = object.getString("description");
            if (desc != null && desc.toLowerCase().equals("null"))
                desc = null;
            tel = object.getString("tel");
            if (tel != null && tel.toLowerCase().equals("null"))
                tel = null;
            mobile = object.getString("mobile");
            if (mobile != null && mobile.toLowerCase().equals("null"))
                mobile = null;
//                    =object.getString("province");
            String a = object.getString("city");
            if (a != null && !a.equals("null"))
                city = Integer.parseInt(a);
            a = object.getString("region_id");
            if (a != null && !a.equals("null"))
                region = Integer.parseInt(a);
//                    name = object.getString("created");
            nodeid = object.getInt("level3");
            addImageFromJsonObject(object, saveImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillFromProperty(Property object) {
        if (object == null)
            return;
        try {
            name = object.name;
            title = object.title;
            telegram = object.telegram;
            address = object.address;
            email = object.email;
            desc = object.desc;
            tel = object.tel;
            mobile = object.mobile;
            city = object.city;
            region = object.region;
            nodeid = object.nodeid;
            dateString = object.dateString;
            date = object.date;
            images = object.images;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return name + " : " + email;
    }
}
