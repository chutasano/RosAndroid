package chuta.rosandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
//import org.ros.rosjava_tutorial_pubsub.Talker;
import java.util.ArrayList;

import chuta.rosandroid.Talker;


public class MainActivity extends RosActivity {

    private RosTextView<std_msgs.String> rosTextView;
    private Talker talker;
    private BooleanTalker talker2;

    private ArrayList<ArrayList<BooleanTalker> > talkerCollection;
    private final String TAG = "RosTester";
    private final String[] robotNames = {"Dirtdog", "Bender", "500", "Deado", "Discovery"};

    private final String[] commands =
            {"Robot/Looking", "Robot/Touched", "Robot/Pickedup", "Robot/OnCharger", //0-3
             "Dirtbin/Removed", "Dirtbin/Emptied", "Dirtbin/Replaced", //4-6
             "Gamezone/Entered", "Gamezone/Left", //7-8
             "Button/Power", "Button/Spot", "Button/Clean", "Button/Max", "Button/Dock", "Button/Menu", "Button/Other", //9-15
             "Report/Phonelink", "Report/Gamezone", "Report/Robot" //16-18
    };

    private int selectedRobot;

    public MainActivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("ros android", "ros android");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        talkerCollection = new ArrayList<>();
        //list will contain a list of all commands for each robot
        for (int i=0; i<robotNames.length; i++)
        {
            talkerCollection.add(generateAllPublishers(robotNames[i]));
        }
        setContentView(R.layout.activity_main);
        rosTextView = (RosTextView<std_msgs.String>) findViewById(R.id.text);
        rosTextView.setTopicName("chatter");
        rosTextView.setMessageType(std_msgs.String._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, std_msgs.String>() {
            @Override
            public String call(std_msgs.String message) {
                return message.getData();
            }
        });
    }

    private ArrayList<BooleanTalker> generateAllPublishers(String robotName) {
        ArrayList<BooleanTalker> btalkers = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            btalkers.add(new BooleanTalker(robotName + "/" + commands[i]));
        }
        return btalkers;
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        talker = new Talker();
        talker2 = new BooleanTalker("hipub");
        // At this point, the user has already been prompted to either enter the URI
        // of a master to use or to start a master locally.

        // The user can easily use the selected ROS Hostname in the master chooser
        // activity.
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());
        nodeMainExecutor.execute(talker, nodeConfiguration);
        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        nodeMainExecutor.execute(rosTextView, nodeConfiguration);
        nodeMainExecutor.execute(talker2, nodeConfiguration);


    }

    public void addQueue(View view)
    {
        if (view == findViewById(R.id.buttonButton1)) {
            talker2.enqueue(true);
        } else
        {
            talker2.enqueue(false);
        }
    }

    public void handleSendButton(View view)
    {
        if (view == findViewById(R.id.buttonRobot1)){
            talkerCollection.get(selectedRobot).get(0).enqueue(true);
        } else if (view == findViewById(R.id.buttonRobot2)){
            talkerCollection.get(selectedRobot).get(1).enqueue(true);
        } else if (view == findViewById(R.id.buttonRobot3)){
            talkerCollection.get(selectedRobot).get(2).enqueue(true);
        } else if (view == findViewById(R.id.buttonRobot4)){
            talkerCollection.get(selectedRobot).get(3).enqueue(true);
        } else if (view == findViewById(R.id.buttonDirtbin1)){
            talkerCollection.get(selectedRobot).get(4).enqueue(true);
        } else if (view == findViewById(R.id.buttonDirtbin2)){
            talkerCollection.get(selectedRobot).get(5).enqueue(true);
        } else if (view == findViewById(R.id.buttonDirtbin3)){
            talkerCollection.get(selectedRobot).get(6).enqueue(true);
        } else if (view == findViewById(R.id.buttonGamezone1)) {
            talkerCollection.get(selectedRobot).get(7).enqueue(true);
        } else if (view == findViewById(R.id.buttonGamezone2)) {
            talkerCollection.get(selectedRobot).get(8).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton1)) {
            talkerCollection.get(selectedRobot).get(9).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton2)) {
            talkerCollection.get(selectedRobot).get(10).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton3)) {
            talkerCollection.get(selectedRobot).get(11).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton4)) {
            talkerCollection.get(selectedRobot).get(12).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton5)) {
            talkerCollection.get(selectedRobot).get(13).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton6)) {
            talkerCollection.get(selectedRobot).get(14).enqueue(true);
        } else if (view == findViewById(R.id.buttonButton7)) {
        talkerCollection.get(selectedRobot).get(15).enqueue(true);
        } else if (view == findViewById(R.id.buttonReport1)) {
        talkerCollection.get(selectedRobot).get(16).enqueue(true);
        } else if (view == findViewById(R.id.buttonReport2)) {
            talkerCollection.get(selectedRobot).get(17).enqueue(true);
        } else if (view == findViewById(R.id.buttonReport3)) {
            talkerCollection.get(selectedRobot).get(18).enqueue(true);
        } else {
            Log.d(TAG, "Unknown bututon");
        }
    }




    public void handleRobotSelection(View view) {

        Button b1 = (Button)findViewById(R.id.buttonRobot1Select);
        Button b2 = (Button)findViewById(R.id.buttonRobot2Select);
        Button b3 = (Button)findViewById(R.id.buttonRobot3Select);
        Button b4 = (Button)findViewById(R.id.buttonRobot4Select);
        Button b5 = (Button)findViewById(R.id.buttonRobot5Select);
        b1.setBackgroundColor(Color.WHITE);
        b2.setBackgroundColor(Color.WHITE);
        b3.setBackgroundColor(Color.WHITE);
        b4.setBackgroundColor(Color.WHITE);
        b5.setBackgroundColor(Color.WHITE);

        if (view == findViewById(R.id.buttonRobot1Select))
        {
            ((Button)view).setBackgroundColor(Color.RED);
            selectedRobot = 0;
        }
        else if (view == findViewById(R.id.buttonRobot2Select))
        {
            ((Button)view).setBackgroundColor(Color.RED);
            selectedRobot = 1;
        }
        else if (view == findViewById(R.id.buttonRobot3Select))
        {
            ((Button)view).setBackgroundColor(Color.RED);
            selectedRobot = 2;
        }
        else if (view == findViewById(R.id.buttonRobot4Select))
        {
            ((Button)view).setBackgroundColor(Color.RED);
            selectedRobot = 3;
        }
        else if (view == findViewById(R.id.buttonRobot5Select))
        {
            ((Button)view).setBackgroundColor(Color.RED);
            selectedRobot = 4;
        }
    }
}