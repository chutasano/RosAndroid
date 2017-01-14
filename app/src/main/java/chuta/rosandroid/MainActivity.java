package chuta.rosandroid;

import android.os.Bundle;
import android.view.View;

import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
//import org.ros.rosjava_tutorial_pubsub.Talker;
import chuta.rosandroid.Talker;


public class MainActivity extends RosActivity {

    private RosTextView<std_msgs.String> rosTextView;
    private Talker talker;
    private BooleanTalker talker2;

    public MainActivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("Pubsub Tutorial", "Pubsub Tutorial");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (view == findViewById(R.id.button)) {
            talker2.enqueue(true);
        } else
        {
            talker2.enqueue(false);
        }
    }
}