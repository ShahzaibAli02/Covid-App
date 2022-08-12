package com.omnitech.covidproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omnitech.covidproject.Fragments.HomeFragment;
import com.omnitech.covidproject.Fragments.MapFragment;
import com.omnitech.covidproject.Fragments.NewsFragment;
import com.omnitech.covidproject.Fragments.StatisticsFragment;
import com.omnitech.covidproject.Fragments.WebViewFragment;
import com.omnitech.covidproject.R;

public class MainActivity extends AppCompatActivity {


    ImageView btnCurrentlySelected=null;
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        txtTitle=findViewById(R.id.txtTitle);
        navigateTO(new HomeFragment(),"Home");
        btnCurrentlySelected= findViewById(R.id.btn_home);




    }



    public  void  navigateToWebView()
    {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frame,new WebViewFragment(), null)
                .commit();

    }
    public void bottomItemSelected(View view)
    {



        if(view.getId()==R.id.btn_gps)
        {
             if(!isGpsOn())
                {
                    return;

                }
                if(!hasPermissions())
                {
                    return;
                }
        }

        ImageView imgSelected=(ImageView) view;
        if(btnCurrentlySelected==null)
            btnCurrentlySelected= imgSelected;

        btnCurrentlySelected.setColorFilter(ContextCompat.getColor(this, R.color.grey));
        btnCurrentlySelected.setBackground(null);
        imgSelected.setColorFilter(ContextCompat.getColor(this, R.color.white));
        imgSelected.setBackgroundResource(R.drawable.selected_button);
        btnCurrentlySelected= imgSelected;


        switch (imgSelected.getId())
        {

            case R.id.btn_home:
                navigateTO(new HomeFragment(),"Home");
                return;
            case R.id.btn_statics:
                navigateTO(new StatisticsFragment(),"Statistics");
                return;
            case R.id.btn_news:
                navigateTO(new NewsFragment(),"News");
                return;
            case R.id.btn_gps:
                navigateTO(new MapFragment(),"GPS");
                return;

        }



    }

    public  void  navigateTO(Fragment targetFragmenet,String title)
    {




        txtTitle.setText(title);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frame,targetFragmenet, null)
                .commit();
    }

    private boolean hasPermissions()
    {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
            return false;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    //this method calls when user gives permisson or deny
    {
        switch (requestCode)
        {
            case 11: //case 11 is our location request code
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    bottomItemSelected(findViewById(R.id.btn_gps));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Permission Required To Open This Tab",Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private boolean isGpsOn()
    {

        // get Location Manager Object
        final LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );


        // checking if user gps is On Or Not
        if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
        {

            // Ask User To Open GPS
            Toast.makeText(MainActivity.this,"Please Enable GPS",Toast.LENGTH_LONG).show();


            //This intent will take user to Enable Gps Screen
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            return false;
        }

        return true;
    }

    public void onClickHamBurgIcon(View view)
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        View child = getLayoutInflater().inflate(R.layout.popup, null);

        builder1.setView(child);
        builder1.setCancelable(true);
        final AlertDialog dialog = builder1.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        initPopup(child,dialog);

    }

    private void initPopup(View child, AlertDialog dialog)
    {

        LinearLayout lytHome=child.findViewById(R.id.lytHome);
        LinearLayout lytStat=child.findViewById(R.id.lytStat);
        LinearLayout lytNews=child.findViewById(R.id.lytNews);
        LinearLayout lytMap=child.findViewById(R.id.lytMap);
        LinearLayout lytExit=child.findViewById(R.id.lytExit);


        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

                switch (v.getId())
                {
                    case R.id.lytHome:
                        bottomItemSelected(findViewById(R.id.btn_home));
                        break;
                    case R.id.lytStat:
                        bottomItemSelected(findViewById(R.id.btn_statics));
                        break;
                    case R.id.lytNews:
                        bottomItemSelected(findViewById(R.id.btn_news));
                        break;
                    case R.id.lytMap:
                        bottomItemSelected(findViewById(R.id.btn_gps));
                        break;
                }

            }
        };


        lytHome.setOnClickListener(onClickListener);
        lytStat.setOnClickListener(onClickListener);
        lytNews.setOnClickListener(onClickListener);
        lytMap.setOnClickListener(onClickListener);
        lytExit.setOnClickListener(onClickListener);

    }
}