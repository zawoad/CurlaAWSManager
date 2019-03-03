/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curlaawsmanager.edu.cis.uab.handler;

import curlaawsmanager.edu.cis.uab.awshelper.SQSHelper;
import curlaawsmanager.edu.cis.uab.dal.Ec2QueueRelationHandler;
import curlaawsmanager.edu.cis.uab.util.ZLogger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zawoad
 */
public class SQSFormHandler {

    public static void createFetcherQueuesWithRange(int start, int end) {
        SQSHelper sqsH = SQSHelper.SQSInstance();
        try {
            for (int j = start; j <= end; j++) {
                sqsH.createQueues("url-fetcher-" + j);
                Ec2QueueRelationHandler queueHandler = new Ec2QueueRelationHandler();
                queueHandler.createFetcherQueue("url-fetcher-" + j);

            }
            ZLogger.d("Fetcher Queue created successfully.");
        }
        catch (SQLException ex) {
            ZLogger.e(ex.getLocalizedMessage());
        }
    }

    public static void createUploaderQueuesWithRange(int start, int end) {
        SQSHelper sqsH = SQSHelper.SQSInstance();
        try {
            for (int j = start; j <= end; j++) {
                sqsH.createQueues("content-uploader-" + j);
                Ec2QueueRelationHandler queueHandler = new Ec2QueueRelationHandler();
                queueHandler.createUploaderQueue("content-uploader-" + j);
            }
            ZLogger.d("Uploader Queue created successfully.");
        }
        catch (SQLException ex) {
            ZLogger.e(ex.getLocalizedMessage());
        }
    }

    public static String[] getQueueTableHeader() {
        String[] columns = {
            "Queue Name"};
        return columns;
    }

    public static List<Object[]> getAllFetcherQueue() {
        List<Object[]> fqList = new ArrayList<Object[]>();

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy:HH:mm");

        for (String queue : SQSHelper.SQSInstance().getFetcherQueueURLList()) {
            String arr[] = queue.split("/");
            fqList.add(new Object[]{arr[arr.length - 1], 1});
        }
        return fqList;
    }

    public static List<Object[]> getAllUploaderQueue() {
        List<Object[]> uqList = new ArrayList<Object[]>();

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy:HH:mm");

        for (String queue : SQSHelper.SQSInstance().getUploaderQueueURLList()) {
            String arr[] = queue.split("/");
            uqList.add(new Object[]{arr[arr.length - 1], 1});
        }
        return uqList;
    }
}
