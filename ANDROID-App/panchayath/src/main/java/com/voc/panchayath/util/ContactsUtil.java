package com.voc.panchayath.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

public class ContactsUtil {

    public static void openWhatsApp(Activity activity, String contactNo) {
        activity.startActivity(getWhatsAppIntent(contactNo));
    }

    public static void openWhatsApp(Fragment fragment, String contactNo) {
        fragment.startActivity(getWhatsAppIntent(contactNo));
    }

    private static Intent getWhatsAppIntent(String contactNo) {
        if (!contactNo.equals("") && !contactNo.startsWith("+91") && contactNo.length() <= 10) {
            contactNo = "+91" + contactNo;
        }

        Uri uri = Uri.parse("smsto:" + contactNo);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
        sendIntent.setPackage("com.whatsapp");
        return sendIntent;
    }

    public static void openMessenger(Activity activity, String contactNo) {
        activity.startActivity(getSmsIntent(contactNo));
    }

    public static void openMessenger(Fragment fragment, String contactNo) {
        fragment.startActivity(getSmsIntent(contactNo));
    }

    private static Intent getSmsIntent(String contactNo) {
        if (!contactNo.equals("") && !contactNo.startsWith("+91") && contactNo.length() <= 10) {
            contactNo = "+91" + contactNo;
        }

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + contactNo));
        return sendIntent;
    }

    public static void makeCall(Activity activity, String contactNo) {
        activity.startActivity(getCallIntent(contactNo));
    }

    public static void makeCall(Fragment fragment, String contactNo) {
        fragment.startActivity(getCallIntent(contactNo));
    }

    private static Intent getCallIntent(String contactNo) {
        if (!contactNo.equals("") && !contactNo.startsWith("+91") && contactNo.length() <= 10) {
            contactNo = "+91" + contactNo;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + contactNo));
        return intent;
    }
}
