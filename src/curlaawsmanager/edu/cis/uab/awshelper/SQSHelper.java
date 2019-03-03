package curlaawsmanager.edu.cis.uab.awshelper;

import cis.uab.edu.spamurlfetcher.util.AmazonCredentials;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import curlaawsmanager.edu.cis.uab.util.ZLogger;


public class SQSHelper {

	/**
	 * @param args
	 */

	private static SQSHelper SQSInstance;
	public static String FILE_ROOT = "";

	private AmazonSQS sqs;

	private SQSHelper() {
		sqs = new AmazonSQSClient(new AmazonCredentials());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		sqs.setRegion(usWest2);

	}

	public static SQSHelper SQSInstance() {
		if (SQSInstance == null)
			SQSInstance = new SQSHelper();
		return SQSInstance;
	}

	public static void main(String[] args) {

		SQSHelper sqsHelper = SQSHelper.SQSInstance();
	//	 sqsHelper.createQueues("url-fetcher-1");
		// sendRequest();
		sqsHelper.getFetcherQueueURLList();
		// sqsHelper.clearAllQueues();
	//	sqsHelper.listAllQues();
	//	sqsHelper.deleteAllQueues();
	}

	public void createQueues(String queueName) {

		CreateQueueRequest createQueueRequest = new CreateQueueRequest(
				queueName);
		sqs.createQueue(createQueueRequest);
		
	}

	public void deleteQueue(String queueUrl)
	{
		DeleteQueueRequest deleteReq = new DeleteQueueRequest(queueUrl);
		sqs.deleteQueue(deleteReq);
	}
	public void listAllQues() {
		for (String qurl : sqs.listQueues().getQueueUrls()) {
			System.out.println("Queur URL:" + qurl);
		}
	}

	public void sendMessageToQueueInRoundRobin(List<String> messageList)
	{
		try{
		List<String> queurUrlList = getFetcherQueueURLList();
		int size = queurUrlList.size();
		int i = 0;
		for(String message: messageList) {
			try{
				
			sqs.sendMessage(new SendMessageRequest(queurUrlList.get(i
					% size), message));
			//System.out.println("send url to:" + queurUrlList.get(i % size) + " -> " + message);
			}catch(Exception ex)
			{
                            ZLogger.e(ex.getLocalizedMessage());
			}
			i++;
			
		}
		}catch(Exception ex)
		{
                    ZLogger.e(ex.getLocalizedMessage());
		}
	}
	
	public void clearQueuByName(String queueName) {
		String queueURL = sqs.getQueueUrl(new GetQueueUrlRequest(queueName))
				.getQueueUrl();
		clearQueue(queueURL);
	}

        public void clearQueueList(List<String> queueUrlList)
        {
            for(String qurl : queueUrlList)
            {
                clearQueue(qurl);
            }
        }
        public void removeQueueList(List<String> queueUrlList)
        {
            for(String qurl : queueUrlList)
            {
                deleteQueue(qurl);
            }
        }
        public void deleteQueuByName(String queueName) {
		String queueURL = sqs.getQueueUrl(new GetQueueUrlRequest(queueName))
				.getQueueUrl();
		deleteQueue(queueURL);
	}
        
	public void clearQueue(String queueUrl) {
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(
				queueUrl);
		List<Message> messages = sqs.receiveMessage(receiveMessageRequest)
				.getMessages();
		while (messages.size() > 0) {
			for (Message message : messages) {
				System.out.println("Deleting Message:" + message.getBody());
				String messageRecieptHandle = message.getReceiptHandle();
				sqs.deleteMessage(new DeleteMessageRequest(queueUrl,
						messageRecieptHandle));
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
		}
	}

	public void clearAllQueues() {
		List<String> qnameList = getAllSQSQueueURL();
		for (String q : qnameList) {
			clearQueue(q);
		}
	}

	public void deleteAllQueues() {
		List<String> qnameList = getAllSQSQueueURL();
		for (String q : qnameList) {
			deleteQueue(q);
		}
	}

	public List<String> getAllSQSQueueURL() {
		List<String> qnameList = new ArrayList<String>();

		for (String qurl : sqs.listQueues().getQueueUrls()) {
			qnameList.add(qurl);
		}
		return qnameList;
	}

	public List<String> getFetcherQueueURLList() {
		List<String> queueUrlList = new ArrayList<String>();

		for (String qurl : sqs.listQueues().getQueueUrls()) {
			if (qurl.contains("url-fetcher")){
				queueUrlList.add(qurl);
                   	}
		}
		return queueUrlList;
	}

        public List<String> getUploaderQueueURLList() {
		List<String> queueUrlList = new ArrayList<String>();

		for (String qurl : sqs.listQueues().getQueueUrls()) {
			if (qurl.contains("content-uploader")){
				queueUrlList.add(qurl);
			}
		}
		return queueUrlList;
	}
}
