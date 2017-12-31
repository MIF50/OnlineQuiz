package mif50.com.onlinequizapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import mif50.com.onlinequizapp.broadcast.AlarmReceiver;
import mif50.com.onlinequizapp.common.Common;
import mif50.com.onlinequizapp.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // init view in sign_up_layout.xml
    MaterialEditText username,password,email;
    // init view in main_activity.xml
    MaterialEditText user,pass;
    Button btnSignIn,btnSignUp;
    // init firebase
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find Object of Firebase object Json or create it
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        // find View in main_activity.xml
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        btnSignIn=findViewById(R.id.btn_sign_in);
        btnSignUp=findViewById(R.id.btn_sign_up);
        // set up action to btn in main_activity.xml
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        registerAlarm();//
    }
    /**/
    private void registerAlarm() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,27);
        calendar.set(Calendar.SECOND,0);
        Intent intent=new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId(); // get id if view (btn sign in or sign up)
        switch (id){
            // if action btn sign up
            case R.id.btn_sign_up:
                showSignUpDialog(); // register (username,password,email) in firebase (Users)
                break;

            // if action btn sign in
            case R.id.btn_sign_in:
                String txtUser=user.getText().toString(); // get username for login
                String txtPass=pass.getText().toString(); // get password for login
                signIn(txtUser,txtPass); // sign in with username , password
                break;
        }
    }

    /*this method sign in with username and password that check they found In Firebase json obj Users */
    private void signIn(final String txtUser, final String txtPass) {
        users.addListenerForSingleValueEvent(new ValueEventListener() { // used to listener data in obj users in firebase
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // listener in change data (read/write) dataSnapshot contain data
                if (dataSnapshot.child(txtUser).exists()){ // check if user exists in obj users in firebase
                    if(! txtUser.isEmpty()){ // if txtUser contain value
                        // get data dataSnapshot and set it in obj of user in model
                        User login=dataSnapshot.child(txtUser).getValue(User.class);
                        if (login.getPassword().equals(txtPass)){ // if pass of obj login == pass that user has written in View
                            // login success
                            Common.currentUser=login; // make currentUser = user that login success (username ->ahmed ,password-> 12345)
                            startActivity(Home.newIntent(MainActivity.this)); // move to Home Activity
                            finish();
                        }else{
                            // login failed pass is wrong
                            Toast.makeText(MainActivity.this, "Wrong Password !", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        // username field is empty pls enter it
                        Toast.makeText(MainActivity.this, "pls Enter Username !", Toast.LENGTH_SHORT).show();
                    }
                }else{ // user not exists in obj users in firebase
                    Toast.makeText(MainActivity.this, "User is Not Exists pls Sign Up first !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /*this method to sign up new User to firebase json Obj user*/
    private void showSignUpDialog() {
        // create dialog to get username,password,email from user
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Sign Up"); // set title to alert
        alert.setMessage("pls fill full information"); // set message to alert
        // get obj of LayoutInflater that user to inflate layout in alert dialog
        LayoutInflater inflater = this.getLayoutInflater();
        // inflate layout sign up in inflater and pass it to obj of view use this view to get content of layout sign up
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout,null);
        // find view in sign_up_layout.xml
        username=sign_up_layout.findViewById(R.id.username);
        password=sign_up_layout.findViewById(R.id.password);
        email=sign_up_layout.findViewById(R.id.email);
        alert.setView(sign_up_layout); // set view of sign up in alert dialog
        alert.setIcon(R.drawable.ic_person); // set icon to alert
        // btn of cancel No
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // when click in it close alert obj
                dialog.dismiss();
            }
        });
        // btn of positive Yes
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // get data from alert and save in firebase json obj (Users)
                // get data username,password,email
                final String txtUsername=username.getText().toString();
                final String txtPassword=password.getText().toString();
                final String txtEmail=email.getText().toString();
                // set data in obj of model class  User to add this obj in firebase object json
                final User user= new User(txtUsername,txtPassword,txtEmail);
                users.addListenerForSingleValueEvent(new ValueEventListener() { // used to  listener data in obj users in firebase
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { // listener in change data (read/write)
                        if (dataSnapshot.child(user.getUsername()).exists()){ // check if username exists in obj users
                            Toast.makeText(MainActivity.this, "user already exists", Toast.LENGTH_SHORT).show();
                        }else {
                            // save obj user to firebase json obj Users
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(MainActivity.this, "user registration success !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
