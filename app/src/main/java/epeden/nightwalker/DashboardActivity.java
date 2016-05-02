package epeden.nightwalker;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipul.hp_hp.timelineview.TimelineView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset;


    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private TextView dashboardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window (for compatibility with old android)
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);




        dashboardInfo = (TextView) findViewById(R.id.dashboard_info);

        Alarm a = Alarm.findWithQuery(Alarm.class, "SELECT * FROM alarm ORDERBY original_start_time DESC LIMIT 1").get(0);
        long alarm_id = a.getId();
        List<WakeEvent> wakeEvents = WakeEvent.find(WakeEvent.class, "alarm_id = ?", Long.toString(alarm_id));
        int count = wakeEvents.size();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("EEEE MM/dd");
        String formattedDate = df.format(c.getTime());

        getSupportActionBar().setTitle(formattedDate);
//        getSupportActionBar().setSubtitle(formattedDate);

        String s = "Pickup count: "+count+"";
        dashboardInfo.setText(s);

        initView();
    }


    private void initView() {
        Calendar c = Calendar.getInstance();
        long time = System.currentTimeMillis();
        c.setTimeInMillis(time);
        Alarm a = new Alarm(c.getTime(),0);

        for(int i = 0;i < 10;i++) {
            c.setTimeInMillis(System.currentTimeMillis());
            WakeEvent e = new WakeEvent(c.getTime(),c.getTime(), a);
            TimeLineModel model = new TimeLineModel();
            model.setName(e.getString());
            mDataList.add(model);
        }

        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }



    public void backButtonPushed(View v) {
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }


    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }

    }


    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.timeline_item, null);
        return new TimeLineViewHolder(view, viewType);
    }

}
