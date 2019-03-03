package curlaawsmanager.edu.cis.uab.dal;

import curlaawsmanager.edu.cis.uab.util.Config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ec2QueueRelationHandler {
	private Connection db;
	public static String[] getTableHeader(){ 
		String[] allColumns = { 
			  "Instance", "Queue Name"};
		return allColumns;
	}   
	public Ec2QueueRelationHandler()
	{
		db = DBConnection.DB(Config.Instanc().DBHost(), Config.Instanc().DBPort(),
                        Config.Instanc().DBName(), Config.Instanc().DBUserName(), Config.Instanc().DBPassword());
//		db = DBConnection.DB("localhost", Config.Instanc().DBPort(),
//                        Config.Instanc().DBName(), Config.Instanc().DBUserName(), Config.Instanc().DBPassword());
	}
	
        
	public void createFetcherQueue(String queueName) throws SQLException
	{
		Statement sql = db.createStatement();
                String query = "insert into fetcher_queue_map(instance_id, queue_name, is_assigned)"
				+"values (' ','"+queueName+"', 0);";
		sql.executeUpdate(query);
		sql.close();
	}
	public void createUploaderQueue(String queueName) throws SQLException
	{
		Statement sql = db.createStatement();
                String query = "insert into uploader_queue_map(instance_id, queue_name, is_assigned)"
				+"values (' ','"+queueName+"', 0);";
		sql.executeUpdate(query);
		sql.close();
	}
	public void removeFetcherQueue(String queueName) throws SQLException
	{
		Statement sql = db.createStatement();
                String query = "delete from fetcher_queue_map where queue_name = '"+queueName+"';";
		sql.executeUpdate(query);
		sql.close();
	}
        
	public void removeAllFetcherQueue() throws SQLException
	{
		Statement sql = db.createStatement();
                String query = "delete from fetcher_queue_map;";
		sql.executeUpdate(query);
		sql.close();
	}
	public void removeAllUploaderQueue() throws SQLException
	{
		Statement sql = db.createStatement();
                String query = "delete from uploader_queue_map;";
		sql.executeUpdate(query);
		sql.close();
	}
        
	public void createFetcherQueueRelation(String instanceId, String queueName) throws SQLException
	{
		Statement sql = db.createStatement();
                String query = "update fetcher_queue_map set instance_id = '"+instanceId+"', is_assigned = 1 where queue_name = '"+queueName+"';";
		sql.executeUpdate(query);
		sql.close();
	}
        public void createUploaderQueueRelation(String instanceId, String queueName) throws SQLException
	{
		Statement sql = db.createStatement();
                System.out.println("instanceId = " + instanceId);
                System.out.println("queueName = " + queueName);
                String query = "update uploader_queue_map set instance_id = '"+instanceId+"', is_assigned = 1 where queue_name = '"+queueName+"';";
                sql.executeUpdate(query);
		sql.close();
	}
	public HashMap<String, String> getFetcherQueueMap() throws SQLException {
	    
            HashMap<String,String> instanceQueueMap = new HashMap<String, String>();
	    Statement sql = db.createStatement();
	    String query = "select * from fetcher_queue_map where is_assigned = 1;";
	    ResultSet rs = sql.executeQuery(query);
	    while(rs.next())
	    {
	    	instanceQueueMap.put(rs.getString("instance_id"), rs.getString("queue_name"));
	    }
	    return instanceQueueMap;
	}
	
        public HashMap<String, String> getUploaderQueueMap() throws SQLException {
	    
            HashMap<String,String> instanceQueueMap = new HashMap<String, String>();
	    Statement sql = db.createStatement();
	    String query = "select * from uploader_queue_map;";
	    ResultSet rs = sql.executeQuery(query);
	    while(rs.next())
	    {
	    	instanceQueueMap.put(rs.getString("instance_id"), rs.getString("queue_name"));
	    }
	    return instanceQueueMap;
	}
        
        public List<String> getUnAssignedFetcherQueues() throws SQLException
        {
            List<String> queueList = new ArrayList<String>();
	    Statement sql = db.createStatement();
	    String query = "select * from fetcher_queue_map where is_assigned =0;";
	    ResultSet rs = sql.executeQuery(query);
	    while(rs.next())
	    {
	    	queueList.add(rs.getString("queue_name"));
	    }
	    return queueList;
        }
        public List<String> getUnAssignedUploaderQueues() throws SQLException
        {
            List<String> queueList = new ArrayList<String>();
	    Statement sql = db.createStatement();
	    String query = "select * from uploader_queue_map where is_assigned =0;";
	    ResultSet rs = sql.executeQuery(query);
	    while(rs.next())
	    {
	    	queueList.add(rs.getString("queue_name"));
	    }
	    return queueList;
        }
}
