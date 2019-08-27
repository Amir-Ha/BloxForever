package a_s.bloxforever;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by Ameer on 7/14/2015.
 */
public class GamePage extends Activity implements View.OnClickListener{
    private int levelNum = Levels.LevelNum;//level Number
    private LevelsDatabase levelsDatabase;
    private LinearLayout screen;
    private GameView gameView;
    private Score score;
    private  MediaPlayer SoundEffect=new MediaPlayer();
    private boolean IsNewGame=false;
    private ArrayList<Score> scores=new ArrayList<Score>();
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen mode(without bar notifications)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set Progress Dialog
        pd = new ProgressDialog(this);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait.");
        pd.setCancelable(true);
        pd.setIndeterminate(true);
        //connect to database
        levelsDatabase = new LevelsDatabase(getApplicationContext(),"db1",null,1);
        //start the level
        StartTheGame(levelNum);
    }
    //method to reset the level (get the original map from database and start again)
    public void ResetButton(){
        gameView=new GameView(this,levelsDatabase.GetLevelMap(levelNum),this);
        setContentView(gameView);
    }
    //method to play sound on game according to type(0=move block,1=falling block ,2 = remove block,3 won the level)
    public void Sounds(int type){
        if(Option.Sound) {
            try {
                if (SoundEffect.isPlaying())
                    SoundEffect.release();
            }catch (IllegalStateException e){}
            switch (type) {
                case 0://move
                    SoundEffect = MediaPlayer.create(GamePage.this, R.raw.move);
                    break;
                case 1://falling
                    SoundEffect = MediaPlayer.create(GamePage.this, R.raw.boom);
                    break;
                case 2://remove
                    SoundEffect = MediaPlayer.create(GamePage.this, R.raw.removeblock);
                    break;
                case 3://won
                    SoundEffect = MediaPlayer.create(GamePage.this, R.raw.won);
                    break;
            }
            SoundEffect.start();
        }
    }
    //method to start the game according to level
    public void StartTheGame(int level){
        IsNewGame=true;//for add score on parse one time
        scores.clear();//clear scores array to add new scores level
        //start the new level
        gameView=new GameView(this,levelsDatabase.GetLevelMap(level),this);
        setContentView(gameView);
    }
    //Method work when you won the level
    public void NextLevel(){
        setContentView(R.layout.next_level);
        //add score to parse
        if(IsNewGame) {
            score = new Score(levelsDatabase.GetPlayer(), gameView.getNumberOfMoves());
            IsNewGame=false;
        }
        //edit the scores array and print all scores on screen
        SetScores();
        Sounds(3);//play won sound
        //set screen layout to click
        screen=(LinearLayout)findViewById(R.id.nextLevel);
        screen.setOnClickListener(this);
        TextView nextlevel=(TextView)findViewById(R.id.nextLeveltxt);
        nextlevel.setText("You Finished level " + levelNum + "\n" + nextlevel.getText());
        if(this.levelNum<16)//if you bellow 16 (number of level) then update on database(canPlay value)
            levelsDatabase.UpdateLevel(this.levelNum+1);
        else//else you finish all level
            nextlevel.setText("You've Finished All Levels, Touch on Screen to Return to Levels Page");
    }

    //method onPause work when you pause the game
    @Override
    protected void onPause() {
        super.onPause();
        //set pause layout
        SoundEffect.release();
        setContentView(R.layout.pause);
        Button resume,sound,help,levels,quit;
        resume=(Button)findViewById(R.id.resumebtn);
        help=(Button)findViewById(R.id.helpbtn);
        sound=(Button)findViewById(R.id.soundPausebtn);
        levels=(Button)findViewById(R.id.levelsbtn);
        quit=(Button)findViewById(R.id.quitbtn);
        resume.setOnClickListener(this);
        sound.setOnClickListener(this);
        help.setOnClickListener(this);
        levels.setOnClickListener(this);
        quit.setOnClickListener(this);
    }
    //method work when you click on button or screen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextLevel:
                if ( this.levelNum < 16){
                    StartTheGame(++levelNum);
                    SoundEffect.release();
                }
                else startActivity(new Intent("android.intent.action.Levels"));
                break;
            case R.id.soundPausebtn:
                if(Option.Sound) {
                    Option.Sound = false;
                    Toast toast = Toast.makeText(getApplicationContext(), "Sound Off", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Option.Sound = true;
                    Toast toast = Toast.makeText(getApplicationContext(), "Sound On", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.resumebtn:setContentView(gameView);
                break;
            case R.id.helpbtn:startActivity(new Intent("android.intent.action.Help"));
                break;
            case R.id.levelsbtn:startActivity(new Intent("android.intent.action.Levels"));
                break;
            case R.id.quitbtn:
                Intent i=new Intent(this, MainMenu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }
    }
    //method to set scores list and print it on screen
    public void SetScores(){
        //set textView
        TextView numberscoretxt=(TextView)findViewById(R.id.numberScoretxt);
        TextView namescoretxt=(TextView)findViewById(R.id.nameScoretxt);
        TextView scoretxt=(TextView)findViewById(R.id.Scoretxt);
        numberscoretxt.setText("\n");
        namescoretxt.setText("Names\n");
        scoretxt.setText("Scores\n");
        //sort the scores list from smallest to biggest
        for (int i=0;i<scores.size()-1;i++){
            for(int j=i+1;j<scores.size();j++){
                if(scores.get(i).getScore()>scores.get(j).getScore()){
                    Score temp=scores.get(i);
                    scores.set(i,scores.get(j));
                    scores.set(j,temp);
                }
            }
        }
        //add scores list to textView
        for(int i=0;i<scores.size();i++){
            numberscoretxt.setText(numberscoretxt.getText()+"  "+(i+1)+"-  \n");
            if(scores.get(i).getName().equals(levelsDatabase.GetPlayer()))
                namescoretxt.setText(namescoretxt.getText()+"  You"+"\n");
            else
                namescoretxt.setText(namescoretxt.getText()+"  "+scores.get(i).getName()+"\n");
            scoretxt.setText(scoretxt.getText()+"  "+scores.get(i).getScore()+"\n");
        }
    }

    //if click on back calls onPause method
    @Override
    public void onBackPressed() {
        onPause();
    }


}
