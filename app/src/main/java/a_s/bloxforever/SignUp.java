package a_s.bloxforever;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sami_Abishai on 23-Aug-15.
 */
public class SignUp extends Activity implements View.OnClickListener{
    private EditText name;
    private Button save;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen mode(without bar notifications)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set layout and button and editText
        setContentView(R.layout.signup);
        name=(EditText)findViewById(R.id.nametxt);
        save=(Button)findViewById(R.id.savebtn);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.savebtn){
            if(!name.getText().toString().equals("")) {//check if name text not empty
                //start loading dialog
                pd = new ProgressDialog(this);
                pd.setTitle("Loading...");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
                //add to sqlLite
                LevelsDatabase levelsDatabase = new LevelsDatabase(getApplicationContext(),"db1",null,1);
                levelsDatabase.AddPlayer(name.getText().toString());
                pd.dismiss();
                startActivity(new Intent("android.intent.action.Help"));

            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Your name", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
