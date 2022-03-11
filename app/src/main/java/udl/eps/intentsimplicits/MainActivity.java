package udl.eps.intentsimplicits;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import udl.eps.intentsimplicits.R;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23)
            if (! ckeckPermissions())
                requestPermissions();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick (View v) {
        Intent in;
        final String lat = getString(R.string.lat);
        final String lon = getString(R.string.lon);
        final String url = getString(R.string.url);
        final String address = getString(R.string.direccion);
        final String textToSearch = getString(R.string.textoABuscar);

        switch (v.getId()) {
            case R.id.button1:
                Toast.makeText(this, getString(R.string.opcion1), Toast.LENGTH_LONG).show();
                in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
                startActivity(in);
                break;
            case R.id.button2:
                Toast.makeText(this, getString(R.string.opcion2), Toast.LENGTH_LONG).show();
                in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
                startActivity(in);
                break;
            case R.id.button3:
                Toast.makeText(this, getString(R.string.opcion3), Toast.LENGTH_LONG).show();
                in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);
                break;
            case R.id.button4:
                Toast.makeText(this, getString(R.string.opcion4), Toast.LENGTH_LONG).show();
                //in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + textToSearch));
                in = new Intent(Intent.ACTION_WEB_SEARCH);
                in.putExtra(SearchManager.QUERY, textToSearch);
                startActivity(in);
                break;
            case R.id.button5:
                callPhoneIfPermissions();
                break;
            case R.id.button6:
                accessContactsIfPermissions();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (Build.VERSION.SDK_INT >= 23 && !ckeckPermissions())
                //requestPermissions();
    }

    private boolean ckeckPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            return ckeckPermissionsCallPhone() && ckeckPermissionsReadContacts();
        }
        else return true;
    }

    private boolean ckeckPermissionsCallPhone() {
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private boolean ckeckPermissionsReadContacts() {
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE},
                0);
    }

    private void requestPermissionsCallPhone() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                0);
    }

    private void requestPermissionsReadContacts() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS},
                0);
    }

    private void accessContactsIfPermissions() {
        if ((Build.VERSION.SDK_INT >= 23) && (!ckeckPermissionsReadContacts()))
            requestPermissionsReadContacts();
        else accessContactsAction();
    }

    private void accessContactsAction() {
        Intent intent;

        Toast.makeText(this, getString(R.string.opcion6), Toast.LENGTH_LONG).show();
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    private void callPhoneIfPermissions() {
        if ((Build.VERSION.SDK_INT >= 23) && (!ckeckPermissionsCallPhone()))
            requestPermissionsCallPhone();
        else callPhoneAction();
    }

    private void callPhoneAction() {
        Intent intent;

        Toast.makeText(this, getString(R.string.opcion5), Toast.LENGTH_LONG).show();
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
        startActivity(intent);
    }
}
