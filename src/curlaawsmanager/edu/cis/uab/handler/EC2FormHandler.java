/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curlaawsmanager.edu.cis.uab.handler;

import com.amazonaws.services.ec2.model.Instance;
import curlaawsmanager.edu.cis.uab.CurlaAWSManager;
import curlaawsmanager.edu.cis.uab.awshelper.EC2Helper;
import curlaawsmanager.edu.cis.uab.dal.Ec2QueueRelationHandler;
import curlaawsmanager.edu.cis.uab.util.Config;
import curlaawsmanager.edu.cis.uab.util.Utility;
import curlaawsmanager.edu.cis.uab.util.ZLogger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author zawoad
 */
public class EC2FormHandler {

//    private static final String RUN_CURLA_SCRIPT = "/home/zawoad/Documents/Dropbox/NetBeansWorkspace/CurlaAWSManager/run_command.sh";
//    private static final String STOP_CURLA_SCRIPT = "/home/zawoad/Documents/Dropbox/NetBeansWorkspace/CurlaAWSManager/stop_command.sh";
//    private static final String LOG_VIEW_SCRIPT = "/home/zawoad/Documents/Dropbox/NetBeansWorkspace/CurlaAWSManager/view_log.sh";
//    private static final String LOG_CLEAR_SCRIPT = "/home/zawoad/Documents/Dropbox/NetBeansWorkspace/CurlaAWSManager/clear_log.sh";
//    private static final String CHANGE_CONFIG_SCRIPT = "/home/zawoad/Documents/Dropbox/NetBeansWorkspace/CurlaAWSManager/change_analyzer_config.sh";

    public static String[] getInstanceTableHeader() {
        String[] columns = {
            "Instance Id", "Public IP", "State", "Fetcher Queue", "Uploader Queue"};
        return columns;
    }

    public static List<Object[]> getAllAnalyzers() throws Exception {
        try {
            List<Object[]> locProofList = new ArrayList<Object[]>();

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy:HH:mm");

            Ec2QueueRelationHandler handler = new Ec2QueueRelationHandler();
            HashMap<String, String> ec2FQMap = handler.getFetcherQueueMap();
            HashMap<String, String> ec2UpQMap = handler.getUploaderQueueMap();
            List<String> notAnalyzer = Config.Instanc().NotAnalyzerList();

            for (Instance in : EC2Helper.EC2Instance().listInstance()) {
                String instanceId = in.getInstanceId();
                if (!notAnalyzer.contains(instanceId) && !(in.getState().getName().equals("terminated"))) {
                    locProofList.add(new Object[]{instanceId, in.getPublicIpAddress(), in.getState().getName(), ec2FQMap.get(instanceId), ec2UpQMap.get(instanceId), instanceId});
                }
            }
            return locProofList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void assignQueueToAnalyzer(List<Object[]> analyzerList) {
        try {
            Ec2QueueRelationHandler handler = new Ec2QueueRelationHandler();
            List<String> fetcherQueueList = handler.getUnAssignedFetcherQueues();
            int i = 0;
            for (Object[] row : analyzerList) {
                
                if (row[3] == null && row[2] != null && ((String) row[2]).equals("running")) {
                    if (i >= fetcherQueueList.size()) {
                        ZLogger.e("All the existing queues are assigned. Please create more Queues");
                        break;
                    }

                    handler.createFetcherQueueRelation((String) row[0], fetcherQueueList.get(i));
                    i++;
                }
            }
        } catch (Exception ex) {
            ZLogger.e(ex.getLocalizedMessage());
        }
    }
    public static void assignUploaderQueueToAnalyzer(List<Object[]> analyzerList) {
        try {
            Ec2QueueRelationHandler handler = new Ec2QueueRelationHandler();
            List<String> uploaderQueueList = handler.getUnAssignedUploaderQueues();
            System.out.println("uploaderQueueList.size() = " + uploaderQueueList.size());
            int i = 0;
            for (Object[] row : analyzerList) {
                
                if (row[4] == null && row[2] != null && ((String) row[2]).equals("running")) {
                    if (i >= uploaderQueueList.size()) {
                        ZLogger.e("All the existing queues are assigned. Please create more Queues");
                        break;
                    }

                    handler.createUploaderQueueRelation((String) row[0], uploaderQueueList.get(i));
                    i++;
                }
            }
        } catch (Exception ex) {
            ZLogger.e(ex.getLocalizedMessage());
        }
    }

    public static void runCurlaInAllInstances(List<Object[]> analyzerList) throws Exception{
        try {
            for (Object[] row : analyzerList) {
                if (row[2] != null && ((String) row[2]).equals("running")) //run curla on running instance 
                {
                    runCurla((String) row[1], (String) row[3]);
                }
            }
            ZLogger.d("CURlA is Running on All Instances!");
            
        } catch (Exception ex) {
            
                ZLogger.e(ex.getMessage());
                ex.printStackTrace();
        }
    }
    
    public static void clearLogAllInstances(List<Object[]> analyzerList) throws Exception{
        try {
            for (Object[] row : analyzerList) {
                if (row[2] != null && ((String) row[2]).equals("running")) //run curla on running instance 
                {
                    clearAnalyzerLog((String) row[1]);
                }
            }
            ZLogger.d("Log is Cleared on All Instances!");
            
        } catch (Exception ex) {
            
                ZLogger.e(ex.getMessage());
                ex.printStackTrace();
        }
    }
    
    public static void stopCurlaInAllInstances(List<Object[]> analyzerList) throws Exception{
        try {
            for (Object[] row : analyzerList) {
                if (row[2] != null && ((String) row[2]).equals("running")) //run curla on running instance 
                {
                    stopCurla((String) row[1]);
                }
            }
            ZLogger.d("CURlA is Stopped on All Instances!");
            
        } catch (Exception ex) {
            
                ZLogger.e(ex.getMessage());
                ex.printStackTrace();
        }
    }
    public static void changeConfigInAllInstances(List<Object[]> analyzerList, String dbHost, String dbPort, String dbUser, 
            String dbPassword, String dbName, String logLevel, String poolSize, String maxPool, String keepAlive,
            String blockingThread, String monitorThread) throws Exception{
        try {
            for (Object[] row : analyzerList) {
                if (row[2] != null && ((String) row[2]).equals("running")) //run curla on running instance 
                {
                    changeConfig((String) row[1], dbHost, dbPort, dbUser, dbPassword, dbName, logLevel, poolSize, maxPool, keepAlive, blockingThread, monitorThread);
                }
            }
            ZLogger.d("Configuration is changed in All Running Instances");
            
        } catch (Exception ex) {
            
                ZLogger.e(ex.getMessage());
                ex.printStackTrace();
        }
    }

    public static void runCurla(String ip, String queue) throws Exception{
        try {
            ProcessBuilder pb = new ProcessBuilder(Config.Instanc().RunCurlaScript(), ip, queue);
            Utility.runRemotScript(pb);
            ZLogger.d("CURlA is running on IP = " + ip + " with queue " + queue);

        } catch (Exception ex) {
            ZLogger.e(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    public static void changeConfig(String ip, String dbHost, String dbPort, String dbUser, 
            String dbPassword, String dbName, String logLevel, String poolSize, String maxPool, String keepAlive,
            String blockingThread, String monitorThread) throws Exception{
        try {
            ProcessBuilder pb = new ProcessBuilder(Config.Instanc().ChangeConfigScript(), ip, dbHost, dbPort, dbUser, 
                    dbPassword, dbName, logLevel, poolSize, maxPool, keepAlive, blockingThread, monitorThread );
            Utility.runRemotScript(pb);
            ZLogger.d("Configuration is changed for IP:"+ip);

        } catch (Exception ex) {
            ZLogger.e(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void stopCurla(String ip) {
        try {
            ProcessBuilder pb = new ProcessBuilder(Config.Instanc().StopCurlaScript(), ip);
            Utility.runRemotScript(pb);
           ZLogger.d("CURlA is Stopped on Instance with IP = " + ip);

        } catch (Exception ex) {

            ZLogger.e(ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void clearAnalyzerLog(String ip) {
        try {
            ProcessBuilder pb = new ProcessBuilder(Config.Instanc().LogClearScript(), ip);

            Utility.runRemotScript(pb);
           ZLogger.d("Log is cleared for IP = " + ip);

        } catch (Exception ex) {

            ZLogger.e(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void readAnalyzerLog(String ip) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(Config.Instanc().LogViewScript(), ip);
        try {

            Process proc = pb.start();
            BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            try {
                proc.waitFor();
            } catch (InterruptedException e) {

                System.out.println(e.getMessage());

            }

            while (read.ready()) {
                ZLogger.appendToPane(CurlaAWSManager.tpAnalyzer, read.readLine());
            }

        } catch (Exception ex) {
            ZLogger.e("Error while reading log from IP:"+ ip+ ". Cause:"+ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            runCurla("54.90.100.244", "url-fetcher-1");
        } catch (Exception ex) {

        }
    }
}
