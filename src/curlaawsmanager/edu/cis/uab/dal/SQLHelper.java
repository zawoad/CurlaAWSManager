package curlaawsmanager.edu.cis.uab.dal;



public class SQLHelper {

	
	  public static final String TABLE_URL = "url";
	  public static final String TABLE_DUPLICATE_URL = "duplicate_url";
	  public static final String TABLE_URL_FILE = "url_file";
	  
	  public static final String COLUMN_URL_ID = "url_id";
	  public static final String COLUMN_URL = "url_str";
	  public static final String COLUMN_URL_HASH = "url_hash";
	  public static final String COLUMN_FILE_COUNT = "file_count";
	  public static final String COLUMN_TOTAL_FILE_SIZE = "total_size";
	  public static final String COLUMN_URL_BLOOM = "bloom_filter";
	  public static final String COLUMN_FULL_DOMAIN = "full_domain";
	  public static final String COLUMN_TOP_DOMAIN = "top_domain";
	  public static final String COLUMN_FOLDER_LOCATION = "folder_location";

	  public static final String COLUMN_DUPP_URL_ID = "dup_url_id";
	  public static final String COLUMN_COMMON_URL	 = "common_url_id";
	  
	  public static final String COLUMN_FILE_ID = "file_id";
	  public static final String COLUMN_FILE_HASH = "file_hash";
}
