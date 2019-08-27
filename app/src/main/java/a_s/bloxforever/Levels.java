package a_s.bloxforever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import java.util.ArrayList;

/**
 * Created by Sami_Abishai on 18-May-15.
 */
public class Levels extends Activity implements View.OnClickListener {
    public static int LevelNum;
    private ImageButton [] levelsbtn;
    private LevelsDatabase levelsDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set layout and buttons
        setContentView(R.layout.levels);
        levelsbtn=new ImageButton[16];
        levelsbtn[0] = (ImageButton)findViewById(R.id.level1);
        levelsbtn[1] = (ImageButton)findViewById(R.id.level2);
        levelsbtn[2] = (ImageButton)findViewById(R.id.level3);
        levelsbtn[3] = (ImageButton)findViewById(R.id.level4);
        levelsbtn[4] = (ImageButton)findViewById(R.id.level5);
        levelsbtn[5] = (ImageButton)findViewById(R.id.level6);
        levelsbtn[6] = (ImageButton)findViewById(R.id.level7);
        levelsbtn[7] = (ImageButton)findViewById(R.id.level8);
        levelsbtn[8] = (ImageButton)findViewById(R.id.level9);
        levelsbtn[9] = (ImageButton)findViewById(R.id.level10);
        levelsbtn[10] = (ImageButton)findViewById(R.id.level11);
        levelsbtn[11] = (ImageButton)findViewById(R.id.level12);
        levelsbtn[12] = (ImageButton)findViewById(R.id.level13);
        levelsbtn[13] = (ImageButton)findViewById(R.id.level14);
        levelsbtn[14] = (ImageButton)findViewById(R.id.level15);
        levelsbtn[15] = (ImageButton)findViewById(R.id.level16);
        //connect to database
        levelsDatabase = new LevelsDatabase(getApplicationContext(),"db1",null,1);
        for (ImageButton level:levelsbtn)
            level.setOnClickListener(this);
        CheckLevels();
    }
    //check if you can play the level if not lock the button of level
    public void CheckLevels(){
        ArrayList<Maps> levels=levelsDatabase.GetAllMaps();
        for(int i=0;i<levels.size();i++) {
            if (!levels.get(i).Canplay()) {
                levelsbtn[i].setClickable(false);
                levelsbtn[i].setImageResource(R.drawable.levellock);
            }
        }
    }

    //when you click on level LevelNum equal to the level and start the gamePage
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.level1: LevelNum =1;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level2: LevelNum =2;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level3:LevelNum =3;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level4:LevelNum =4;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level5:LevelNum =5;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level6:LevelNum =6;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level7:LevelNum =7;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level8:LevelNum =8;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level9:LevelNum =9;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level10:LevelNum =10;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level11:LevelNum =11;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level12:LevelNum =12;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level13:LevelNum =13;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level14:LevelNum =14;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level15:LevelNum =15;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
            case R.id.level16:LevelNum =16;
                startActivity(new Intent("android.intent.action.GamePage"));
                break;
        }
    }

    //when you click on back go to mainMenu
    @Override
    public void onBackPressed() {
        Intent i=new Intent(this, MainMenu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}


