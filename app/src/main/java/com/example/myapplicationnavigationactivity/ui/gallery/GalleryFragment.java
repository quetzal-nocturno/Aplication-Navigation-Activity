package com.example.myapplicationnavigationactivity.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationnavigationactivity.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    TextView tv;
    Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv = binding.textView4;
        btn = binding.button2;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readWS();
            }
        });

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void readWS() {
        String url = "http://3.144.81.206/api";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String finalT = "Machine: " + jsonObject.getString("maquina") +
                            "\nHuman: " + jsonObject.getString("human") + "\n" +
                            "\nProximamente en la guerra de las API's buscala en tu mercado local favorito.\n" +
                            "\nEl Mercado de tu colonia tambien cuenta.";

                    tv.setText(finalT);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error", volleyError.getMessage());
            }
        });

        Volley.newRequestQueue(getContext()).add(request);
    }
}