package a_s.bloxforever;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Ameer on 4/9/2015.
 */
public class MainMenu extends Activity implements View.OnClickListener{
    private Thread thread;
    private boolean animIsFinish, isWorking;//animIsFinish for first animation ,isWorking to check its no Pause
    private RelativeLayout menuLayout;
    private AnimationDrawable animBackground;
    private ImageButton helpbtn,optionbtn,closebtn;
    private LevelsDatabase levelsDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen mode(without bar notifications)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.isWorking = true;
        this.animIsFinish = false;
        setContentView(R.layout.menu);// start menu xml
        //menu layout on xml
        menuLayout=(RelativeLayout)findViewById(R.id.menuLayout);
        //menu layout background equal to xml file(animation)
        menuLayout.setBackgroundResource(R.drawable.background_menu);
        //first animation
        animBackground=(AnimationDrawable)menuLayout.getBackground();
        animBackground.start();
        //start counting(the time till first animation finish)
        thread = new Thread(new StartAnim());
        thread.start();
        //if click on layout,go to onClick methods
        menuLayout.setOnClickListener(this);
        helpbtn=(ImageButton)findViewById(R.id.helpbtn);
        optionbtn=(ImageButton)findViewById(R.id.optionbtn);
        closebtn=(ImageButton)findViewById(R.id.closebtn);
        helpbtn.setOnClickListener(this);
        optionbtn.setOnClickListener(this);
        closebtn.setOnClickListener(this);
        //connect to database
        levelsDatabase = new LevelsDatabase(getApplicationContext(),"db1",null,1);
        levelsDatabase.AddLevels();
        //check if its first time open the game
        ArrayList<Maps> levels=levelsDatabase.GetAllMaps();
        if (!levels.get(1).Canplay())LevelsDatabase.FirstTime=true;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.menuLayout:
                //if its first animation start the second
                if(animIsFinish && !animBackground.isRunning()){
                    menuLayout.setBackgroundResource(R.drawable.background_menu_start);
                    animBackground=(AnimationDrawable)menuLayout.getBackground();
                    animBackground.start();
                    thread = new Thread(new StartAnim());
                    thread.start();
                }
                break;
            case R.id.helpbtn:
                startActivity(new Intent("android.intent.action.Help"));
                break;
            case R.id.optionbtn:startActivity(new Intent("android.intent.action.Option"));
                break;
            case R.id.closebtn:finish();
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        isWorking = false;//to stop the thread
        animBackground.stop();//stop the animation
    }

    @Override
    protected void onResume() {
        super.onResume();
        //to restart the animation
        if(!isWorking) {
            menuLayout.setBackgroundResource(R.drawable.background_menu);
            animBackground = (AnimationDrawable) menuLayout.getBackground();
            animBackground.start();
            thread = new Thread(new StartAnim());
            thread.start();
            isWorking = true;
            animIsFinish = false;
        }
    }
//class for thread animation
    class StartAnim implements Runnable{
        private int second;
        private int numOfSecond;
        public StartAnim(){
            this.second = 0;
            this.numOfSecond = 0;
            //get time of animation
            for(int i=0 ; i < animBackground.getNumberOfFrames() ; i++)
                this.numOfSecond += animBackground.getDuration(i);
            this.numOfSecond /= 100;//to convert to second

        }
        @Override
        public void run() {
            //count the animation
                while (second < numOfSecond && isWorking) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    second++;
                }
            //check if the first animation is not finish
                if (!animIsFinish) {
                    animIsFinish = true;
                    animBackground.stop();
                } else if(isWorking && animIsFinish) {//check if first animation finish and isWorking
                    if(!levelsDatabase.PlayerExists())//check if there is player if not start signUp Page
                       startActivity(new Intent("android.intent.action.SignUp"));
                    else if(!LevelsDatabase.FirstTime)//else check if is the first time if true start the help page
                        startActivity(new Intent("android.intent.action.Levels"));
                    else//else start levels page
                        startActivity(new Intent("android.intent.action.Help"));

                }
        }
    }
}
