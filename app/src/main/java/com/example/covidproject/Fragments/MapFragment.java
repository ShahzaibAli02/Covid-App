package com.example.covidproject.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidproject.Interfaces.ApiCallBack;
import com.example.covidproject.Model.CovidAll;
import com.example.covidproject.Model.NearByPlaces.Location;
import com.example.covidproject.Model.NearByPlaces.NearByPlaces;
import com.example.covidproject.Model.NearByPlaces.Result;
import com.example.covidproject.R;
import com.example.covidproject.Utils.Constants;
import com.example.covidproject.Utils.CovidApi;
import com.example.covidproject.Utils.LocationProvider;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment implements ApiCallBack {

    MapView mMapView;
    private GoogleMap googleMap;

    List<Result> nearByHospitals=new ArrayList<>();
    LatLng mylatLng;
    CovidApi covidApi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Places.initialize(getActivity(), "AIzaSyDioYd868cHeeF5TgZEDYzDvSqD2gaMb6g", Locale.US);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
        initializaPlaces(view);

    }

    private void initializaPlaces(View v) {


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(getActivity(),"Success"+place.getName(),Toast.LENGTH_LONG).show();


                if(place.getLatLng()==null)
                {
                    Toast.makeText(getActivity(),"LatLng Not Found",Toast.LENGTH_LONG).show();
                    return;
                }
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(place.getLatLng()).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }


            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(getActivity(),"Error"+status,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initViews(View view, Bundle savedInstanceState) {

        covidApi=new CovidApi(getActivity(),this);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                getlocation();
                //doPlaces();

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker)
                    {

                        showData(marker.getTitle());
                        return false;
                    }
                });
            }
        });



    }

    private void showData(String title)
    {

        Result result=getClickedPlace(title);
        if(result!=null)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            View child = getLayoutInflater().inflate(R.layout.popup_2, null);

            builder1.setView(child);
            builder1.setCancelable(true);
            final AlertDialog dialog = builder1.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();


            TextView txtPlaceName=child.findViewById(R.id.txtPlaceName);
            TextView txtAddress=child.findViewById(R.id.txtaddress);
            TextView txttime=child.findViewById(R.id.txtOpeningHours);
            TextView txtRating=child.findViewById(R.id.txtRating);
            CardView parentDirections=child.findViewById(R.id.parentDirections);


            txtPlaceName.setText(title);
            txtAddress.setText(result.getVicinity());

            if(result.getOpeningHours()!=null)
                txttime.setText(result.getOpeningHours().getOpenNow() ?"Open":"Closed");

            if(result.getRating()!=null)
            txtRating.setText(result.getRating()+" Stars");


            parentDirections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {


                    Location location = result.getGeometry().getLocation();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+mylatLng.latitude+","+mylatLng.longitude+"&daddr="+location.getLat()+","+location.getLng()+""));
                    startActivity(intent);

                }
            });

        }

    }

    private Result getClickedPlace(String title)
    {
        for(Result result:nearByHospitals)
        {
            if(result.getName().equals(title))
                return result;

        }
        return null;

    }

    private void showOnMap()
    {
        googleMap.clear();
        for(Result result:nearByHospitals)
        {

            LatLng latLng =  new LatLng(result.getGeometry().getLocation().getLat(),result.getGeometry().getLocation().getLng());
            googleMap.addMarker(new MarkerOptions().position(latLng).title(result.getName()));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

    }


    private void getlocation()
    {


        LocationProvider locationProvider;


        locationProvider = new LocationProvider.Builder(getActivity())
                .setInterval(5000)
                .setFastestInterval(2000)
                .setListener(new LocationProvider.MLocationCallback()
                {

                    @Override
                    public void onGoogleAPIClient(GoogleApiClient googleApiClient, String message)
                    {

                    }

                    @Override
                    public void onLocationUpdated(double latitude, double longitude)
                    {
                        if(mylatLng==null)
                        {
                            LatLng latLng =mylatLng= new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Me"));

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15));

                            fetchNearByHospitals();
                        }
                    }

                    @Override
                    public void onLocationUpdateRemoved()
                    {

                    }
                }).build();

        getLifecycle().addObserver(locationProvider);
    }

    private void fetchNearByHospitals()
    {

        String URL= Constants.Hospitals_URL.replace("LAT",String.valueOf(mylatLng.latitude)).replace("LONG",String.valueOf(mylatLng.longitude));
        covidApi.getData(URL,0);
    }


    @Override
    public void onCovidResult(String response, int Code, int DataType)
    {

        if(Code==Constants.SUCCESS_CODE)
        {

            Type type = new TypeToken<NearByPlaces>() {}.getType();
            NearByPlaces nearByPlaces = new Gson().fromJson(response, type);
            nearByHospitals.clear();
            nearByHospitals.addAll(nearByPlaces.getResults());
            showOnMap();
        }

    }
}