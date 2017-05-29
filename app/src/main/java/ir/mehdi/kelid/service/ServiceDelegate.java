package ir.mehdi.kelid.service;

import ir.mehdi.kelid.model.Property;

/**
 * Created by Iman on 3/4/2017.
 */
public interface ServiceDelegate {
    public static int ERROR_CODE = -1;
    public static int DEFAULT_REQUEST_CODE = -1;
    public static int OK_CODE = 1;

    void onObjectReslut(int requestCode, int status, Object requestObject, Object responseObject);



}
