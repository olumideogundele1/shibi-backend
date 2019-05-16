package com.shibi.app.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by User on 06/06/2018.
 */
@Service
public class CommonUtil {


//    public static String getAuthorizationStatus(Set<Authorizers> authorizers, String moduleName){
//        if(authorizers.size() > 0){
//            for(Authorizers authorizer : authorizers){
//                if(authorizer.getApprovalStatus() == ApprovalStatus.APPROVED || authorizer.getApprovalStatus() == ApprovalStatus.NOT_APPROVED){
//                    String message = "{} cannot be updated anymore since approval chain has already commenced.";
//                    message = message.replace(Constants.PLACE_HOLDER, moduleName);
//                    return message;
//                }
//            }
//        }
//
//        return Constants.EMPTY;
//    }

    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months); //minus number would decrement the months
        return cal.getTime();
    }

    public String validateFileSize(MultipartFile jobDescDoc){
        if (jobDescDoc != null && jobDescDoc.getSize() > Long.valueOf("128000")){
            return "Please maximum acceptable image size is 128KB. ";
        }

        return null;
    }
}
