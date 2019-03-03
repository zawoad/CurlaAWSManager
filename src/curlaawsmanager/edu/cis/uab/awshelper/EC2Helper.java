
package curlaawsmanager.edu.cis.uab.awshelper;

import cis.uab.edu.spamurlfetcher.util.AmazonCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import curlaawsmanager.edu.cis.uab.util.Config;
import curlaawsmanager.edu.cis.uab.util.ZLogger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EC2Helper {

    private static EC2Helper ec2Instance;

    private AmazonEC2Client ec2Clinet;

    private  List<String> notAnalyzer;
    private EC2Helper() {
        ec2Clinet = new AmazonEC2Client(new AmazonCredentials());
        Region useast1 = Region.getRegion(Regions.US_EAST_1);
        //ec2Clinet.setEndpoint("us-east-1c");
        ec2Clinet.setRegion(useast1);        
    }

   public List<String> getOtherInstance()
   {
       return notAnalyzer;
   }
    public static EC2Helper EC2Instance() {
        if (ec2Instance == null) {
            ec2Instance = new EC2Helper();
        }
        return ec2Instance;
    }

    public Set<Instance> listInstance() {
        DescribeInstancesResult describeInstancesRequest = ec2Clinet.describeInstances();
        List<Reservation> listEC2Reservations = describeInstancesRequest.getReservations();

        Set<Instance> instances = new HashSet<Instance>();
        for (Reservation reservation : listEC2Reservations) {
            instances.addAll(reservation.getInstances());
        }

        return instances;
    }

    public void stopInstance(String instanceId) throws Exception
    {
            List<String> instanceList = new ArrayList<String>();
        instanceList.add(instanceId);
        StopInstancesResult result = ec2Clinet.stopInstances(new StopInstancesRequest(instanceList));
        List<InstanceStateChange> stateChangeList = result.getStoppingInstances();

    }

    public void startInstance(String instanceId) throws Exception{
        List<String> instanceList = new ArrayList<String>();
        instanceList.add(instanceId);
        StartInstancesResult result;
        StartInstancesRequest startReq = new StartInstancesRequest(instanceList);
        result = ec2Clinet.startInstances(startReq);
        List<InstanceStateChange> stateChangeList = result.getStartingInstances();
    }

    public void runInstance(int min, int max, String instanceType) throws Exception{

        RunInstancesRequest runInstancesRequest
                = new RunInstancesRequest();

        runInstancesRequest.withImageId(Config.Instanc().ImageID())
                .withInstanceType(instanceType)
                .withMinCount(min)
                .withMaxCount(max)
                .withKeyName(Config.Instanc().EC2KeyName())
                .withSecurityGroups(Config.Instanc().Ec2SecGroupName());

        RunInstancesResult runInstancesResult
                = ec2Clinet.runInstances(runInstancesRequest);
    }

    public static void main(String args[]) {
//        EC2Helper.EC2Instance().stopInstance("i-c5dcb32e");
    }
}
