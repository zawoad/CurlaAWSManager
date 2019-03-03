package curlaawsmanager.edu.cis.uab.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {

    private List<String> notAnalyzer;
    private static Config configInstance;
    private Properties systemProperties;

    private Config() {
        // TODO Auto-generated method stub
         systemProperties = new Properties();
        notAnalyzer = new ArrayList<String>();
        try {
            File userHome = new File(System.getProperty("user.home"));
            File propertiesFile = new File(userHome, "CurlaAwsManager.properties");
            systemProperties.load(new FileInputStream(propertiesFile));

            
            String notAnalyzerStr = systemProperties.getProperty("not_analyzer");
            for (String s : notAnalyzerStr.split(",")) {
                notAnalyzer.add(s);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static void forceToReloadPropery() {
        configInstance = null;
    }
    public static Config Instanc()
    {
        if (configInstance == null) {
            configInstance = new Config();
        }
        return configInstance;
    }
    public String ImageID() {
        
        return systemProperties.getProperty("analyzer_image", "");
    }

    public String EC2KeyName() {
        return systemProperties.getProperty("ec2_key");
    }

    public String Ec2SecGroupName() {
        
        return systemProperties.getProperty("ec2_sec_group");
    }

    public List<String> NotAnalyzerList() {
        return notAnalyzer;
    }

    public String DBName() {
        return systemProperties.getProperty("dbname");
    }

    public  String DBHost() {
        return systemProperties.getProperty("dbhost","");
    }

    public String DBUserName() {
        return systemProperties.getProperty("dbuser","");
    }

    public String DBPassword() {
        return systemProperties.getProperty("dbpassword","");
    }

    public  String DBPort() {
        return  systemProperties.getProperty("dbport","");
    }

    public String LogLevel() {
        
        return systemProperties.getProperty("loglevel");
    }
    public String RunCurlaScript() {
        
            return systemProperties.getProperty("script.run_curla");
    }
    public String StopCurlaScript() {
        
        return systemProperties.getProperty("script.stop_curla");
    }
    public String LogViewScript() {
        
        return systemProperties.getProperty("script.log_view");
    }
    
    public String LogClearScript() {
        
        return systemProperties.getProperty("script.log_clear");
    }
    public String ChangeConfigScript() {
        
        return systemProperties.getProperty("script.change_config");
    }
    
    public String URLDBName() {
        return systemProperties.getProperty("urldb.dbname");
    }

    public  String URLDBHost() {
        return systemProperties.getProperty("urlhostdb","");
    }

    public String URLDBUserName() {
        return systemProperties.getProperty("urldb.dbuser","");
    }

    public String URLDBPassword() {
        return systemProperties.getProperty("urldb.dbpassword","");
    }

    public  String URLDBPort() {
        return  systemProperties.getProperty("urldb.dbport","");
    }
}