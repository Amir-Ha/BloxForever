package a_s.bloxforever;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Sami_Abishai on 25-Jul-15.
 */
public class Option extends Activity implements View.OnClickListener {
    private Button reset,sound;
    public static Boolean Sound=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen mode(without bar notifications)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //SET layout and button
        setContentView(R.layout.option);
        reset=(Button)findViewById(R.id.resetAllbtn);
        sound=(Button)findViewById(R.id.soundbtn);
        reset.setOnClickListener(this);
        sound.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.resetAllbtn){//reset the database and show toast
            LevelsDatabase levelsDatabase = new LevelsDatabase(getApplicationContext(),"db1",null,1);
            levelsDatabase.DeleteDataBase();
            levelsDatabase.AddLevels();
            levelsDatabase.close();
            Toast toast = Toast.makeText(getApplicationContext(), "Reset done", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(v.getId()==R.id.soundbtn){//turn sound on/off and show toast
            if(Sound) {
                Sound = false;
                Toast toast = Toast.makeText(getApplicationContext(), "Sound Off", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Sound = true;
                Toast toast = Toast.makeText(getApplicationContext(), "Sound On", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
