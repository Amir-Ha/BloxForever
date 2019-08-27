package a_s.bloxforever;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ameer on 4/25/2015.
 */
public class Help extends Activity implements View.OnClickListener {
    private AnimationDrawable helpAnimation;
    private LinearLayout helpLayout;
    private ImageView helpView;
    private TextView helpText;
    private int i;//i for type of animation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set view
        setContentView(R.layout.help);
        helpText = (TextView)findViewById(R.id.helpText);
        helpLayout = (LinearLayout)findViewById(R.id.helpLayout);
        helpView = (ImageView)findViewById(R.id.helpView);
        helpView.setBackgroundResource(R.drawable.help_animation1);
        helpAnimation = (AnimationDrawable)helpView.getBackground();
        helpAnimation.start();//start first animation
        i=1;//i equal to first animation
        helpLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.helpLayout){
            //according to i work the animation
            switch (i){
                case 1:
                    i=2;
                    helpView.setBackgroundResource(R.drawable.help_animation2);
                    helpAnimation = (AnimationDrawable)helpView.getBackground();
                    helpAnimation.start();
                    helpText.setText(R.string.Animation2details);
                    break;
                case 2:
                    i=3;
                    helpView.setBackgroundResource(R.drawable.help_animation3);
                    helpAnimation = (AnimationDrawable)helpView.getBackground();
                    helpAnimation.start();
                    helpText.setText(R.string.Animation3details);
                    break;
                case 3:
                    //if FirstTime true then its tutorial then start the levels
                    if(LevelsDatabase.FirstTime){
                        LevelsDatabase.FirstTime=false;
                        startActivity(new Intent("android.intent.action.Levels"));
                    }
                    //else return to first animation
                    else {
                        i = 1;
                        helpView.setBackgroundResource(R.drawable.help_animation1);
                        helpAnimation = (AnimationDrawable) helpView.getBackground();
                        helpAnimation.start();
                        helpText.setText(R.string.Animation1details);
                    }
                    break;
            }
        }
    }
}
