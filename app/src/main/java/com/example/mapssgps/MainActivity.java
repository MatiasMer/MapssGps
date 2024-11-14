package com.example.mapssgps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Declaración de variables
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText searchEditText;
    private Button searchButton;
    private TextView distanceText;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LatLng currentLocation;
    private LatLng searchedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de componentes de la UI
        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.search_button);
        distanceText = findViewById(R.id.distance_text);

        // Inicialización del cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Inicialización del fragmento de mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inicialización de la API de Places
        Places.initialize(getApplicationContext(), "TU_API_KEY");

        // Configuración del botón de búsqueda
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUbicacion();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Verificar y solicitar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            obtenerUbicacionActual();
        }
    }

    // Método para obtener la ubicación actual
    private void obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = new LatLng(location.getLatitude(),
                                        location.getLongitude());

                                // Añadir marcador en la ubicación actual
                                mMap.addMarker(new MarkerOptions()
                                        .position(currentLocation)
                                        .title("Mi ubicación actual"));

                                // Mover la cámara a la ubicación actual
                                mMap.moveCamera(CameraUpdateFactory
                                        .newLatLngZoom(currentLocation, 15));
                            }
                        }
                    });
        }
    }

    // Método para buscar una ubicación específica
    private void buscarUbicacion() {
        String locationName = searchEditText.getText().toString();

        // Aquí deberías implementar la búsqueda usando la API de Geocoding
        // Este es un ejemplo simplificado. En una implementación real,
        // deberías usar Geocoder o Places API para obtener las coordenadas

        // Por ejemplo, usando Places API:
        PlacesClient placesClient = Places.createClient(this);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);

        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();

        // Cuando se encuentra la ubicación:
        searchedLocation = currentLocation;// resultado de la búsqueda

        if (searchedLocation != null && currentLocation != null) {
            // Añadir marcador en la ubicación buscada
            mMap.addMarker(new MarkerOptions()
                    .position(searchedLocation)
                    .title("Ubicación buscada"));

            // Calcular y mostrar la distancia
            calcularDistancia();
        }
    }

    // Método para calcular la distancia entre dos puntos
    private void calcularDistancia() {
        if (currentLocation != null && searchedLocation != null) {
            Location locationA = new Location("point A");
            locationA.setLatitude(currentLocation.latitude);
            locationA.setLongitude(currentLocation.longitude);

            Location locationB = new Location("point B");
            locationB.setLatitude(searchedLocation.latitude);
            locationB.setLongitude(searchedLocation.longitude);

            float distance = locationA.distanceTo(locationB);

            // Mostrar la distancia en kilómetros
            distanceText.setText(String.format("Distancia: %.2f km", distance/1000));
        }
    }

    // Manejo de la respuesta de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacionActual();
            }
        }
    }
}