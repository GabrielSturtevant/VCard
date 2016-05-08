package coolcatmeow.org.welcomeanimation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResumeActivity extends AppCompatActivity {
    public  final static String FIRST_NAME = "coolcatmeow.org.welcomeanimation.FIRST_NAME";
    public  final static String LAST_NAME = "coolcatmeow.org.welcomeanimation.LAST_NAME";
    public  final static String EMAIL = "coolcatmeow.org.welcomeanimation.EMAIL";
    public  final static String PHONE = "coolcatmeow.org.welcomeanimation.PHONE";
    public  final static String SCHOOL_NAME = "coolcatmeow.org.welcomeanimation.SCHOOL_NAME";
    public  final static String MAJOR = "coolcatmeow.org.welcomeanimation.MAJOR";
    public  final static String COMPANY_NAME = "coolcatmeow.org.welcomeanimation.COMPANY_NAME";
    public  final static String COMPANY_POSITION = "coolcatmeow.org.welcomeanimation.COMPANY_POSITION";

    private myClass connectionTask = null;
    private ServerConnection serverConnection = null;
    private static String loginEmail = LogInActivity.USEREMAIL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(loginEmail == null)
        {
            String text = "Please log in or create an account";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        String text2 = "::getInfo%%" + loginEmail + ";";
        String userInfo = "";
        connectionTask = new myClass();
        try
        {
            userInfo = connectionTask.execute(text2).get();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        String[] infoArr = userInfo.split(";");
        if(!infoArr[0].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextFistName);
            editText.setText(infoArr[0]);
        }
        if(!infoArr[1].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextLastName);
            editText.setText(infoArr[1]);
        }
        if(!infoArr[2].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextEmail);
            editText.setText(infoArr[2]);
        }
        if(!infoArr[3].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextPhone);
            editText.setText(infoArr[3]);
        }
        if(!infoArr[4].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextSchoolName);
            editText.setText(infoArr[4]);
        }
        if(!infoArr[5].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextMajor);
            editText.setText(infoArr[5]);
        }
        if(!infoArr[6].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextWorkName);
            editText.setText(infoArr[6]);
        }
        if(!infoArr[7].toLowerCase().equals("no"))
        {
            EditText editText = (EditText)findViewById(R.id.editTextWorkTittle);
            editText.setText(infoArr[7]);
        }

        Button goToDisplay = (Button) findViewById(R.id.buttonGoToDisplay);
        goToDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText fn = (EditText) findViewById(R.id.editTextFistName);
                final String firstName = fn.getText().toString();

                EditText ln = (EditText) findViewById(R.id.editTextLastName);
                final String lastName = ln.getText().toString();

                EditText email = (EditText) findViewById(R.id.editTextEmail);
                final String eMail = email.getText().toString();

                EditText phone = (EditText) findViewById(R.id.editTextPhone);
                final String phtext = phone.getText().toString();

                EditText scName =(EditText) findViewById(R.id.editTextSchoolName);
                final String schoolName = scName.getText().toString();

                EditText majorText = (EditText) findViewById(R.id.editTextMajor);
                final String major = majorText.getText().toString();

                EditText companyText = (EditText) findViewById(R.id.editTextWorkName);
                final String companyName = companyText.getText().toString();

                EditText positionText = (EditText) findViewById(R.id.editTextWorkTittle);
                final String companyPosition = positionText.getText().toString();


                Intent save = new Intent(ResumeActivity.this, DisplayResumeActivity.class);
                save.putExtra(FIRST_NAME,firstName);
                save.putExtra(LAST_NAME,lastName);
                save.putExtra(EMAIL, eMail);
                save.putExtra(PHONE,phtext);
                save.putExtra(SCHOOL_NAME,schoolName);
                save.putExtra(MAJOR,major);
                save.putExtra(COMPANY_NAME,companyName);
                save.putExtra(COMPANY_POSITION, companyPosition);

                startActivity(save);

                String command = "::updateResume%%";
                String text= "";
                text += command;
                text += loginEmail +";";
                text += firstName +";";
                text += lastName +";";
                text += eMail +";";
                text += phtext +";";
                text += schoolName +";";
                text += major +";";
                text += companyName +";";
                text += companyPosition +";";
                connectionTask = new myClass();
                try
                {
                    connectionTask.execute(text).get();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * myClass:
     * the extension of AsyncTask<String, Void, String> allows for database queries to occur
     * in the background. The information retrieved can be fetched by instance.execute().get()
     */
    private class myClass extends AsyncTask<String, Void, String>
    {
        String information = "";

        @Override
        protected String doInBackground(String... foo)
        {
            String info;

            try
            {
                serverConnection = new ServerConnection(foo[0]);
                serverConnection.run();
                info = serverConnection.gMessage;
                information = info;

                Looper.prepare();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return information;
        }
    }
}
