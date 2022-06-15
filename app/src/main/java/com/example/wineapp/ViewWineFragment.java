package com.example.wineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wineapp.models.Wine;

public class ViewWineFragment extends Fragment {

    private static Wine wine;
    private Toolbar topToolbar;
    private ImageView closeViewWineFragmentButton;
    private TextView titleTextView, textViewAlcohol, textViewStartDate, textViewBottlingDate, textViewDescription, textViewVolume, textViewWater, textViewFruit, textViewSweetener, textViewYeastTolerance;

    public static ViewWineFragment newInstance(Wine wine) {
        Bundle args = new Bundle();
        args.putSerializable("wine",wine);
        ViewWineFragment viewWineFragment = new ViewWineFragment();
        viewWineFragment.setArguments(args);
        return viewWineFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((WineListActivity) requireActivity()).showFloatingActionButton();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_wine_fragment, container, false);

        if (getArguments() != null) {
            wine= (Wine) getArguments().getSerializable("wine");
        }
        topToolbar = rootView.findViewById(R.id.toolbarAddWineFragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(topToolbar);
        //todo Databinding???
        titleTextView = rootView.findViewById(R.id.titleTextView);
        assert getArguments() != null;
        titleTextView.setText(wine.getName());

        textViewAlcohol = rootView.findViewById(R.id.textViewAlcohol);
        textViewAlcohol.setText("Zawartość alkoholu: "+wine.getAlcohol()+" %");

        textViewStartDate = rootView.findViewById(R.id.textViewStartDate);
        textViewStartDate.setText("Data wstawienia: "+wine.getStartDate());//todo czemu jest puste??

        textViewBottlingDate = rootView.findViewById(R.id.textViewBottlingDate);
        if(!wine.getBottlingDate().equalsIgnoreCase(""))
        textViewBottlingDate.setText("Data butelkowania: "+wine.getBottlingDate());

        textViewDescription = rootView.findViewById(R.id.textViewDescription);
        textViewDescription.setText(wine.getDescription());

        textViewVolume = rootView.findViewById(R.id.textViewVolume);
        textViewVolume.setText("Sumaryczna objętość: "+wine.getVolume()+" L");

        textViewWater = rootView.findViewById(R.id.textViewWater);
        textViewWater.setText("Woda: "+wine.getWater()+" L");

        textViewFruit = rootView.findViewById(R.id.textViewFruit);
        textViewFruit.setText("Owoce: "+wine.getFruit()+" L");

        TextView textViewSugar = rootView.findViewById(R.id.textViewSugar);
        textViewSugar.setText("Cukier: "+wine.getSugar()+" g");

        textViewSweetener = rootView.findViewById(R.id.textViewSweetener);
        textViewSweetener.setText("Słodzik: "+wine.getSweetener()+" g");

        textViewYeastTolerance = rootView.findViewById(R.id.textViewYeastTolerance);
        textViewYeastTolerance.setText("Tolerancja drożdży: "+wine.getYeastTolerance()+" %");

        ImageView imageView = rootView.findViewById(R.id.imageView);
        if(wine.getPhoto()!=null){
            try {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(Uri.parse(wine.getPhoto()));
            }catch (Exception e){
                e.printStackTrace();
                imageView.setVisibility(View.GONE);
            }
        }else imageView.setVisibility(View.GONE);

        ((WineListActivity) requireActivity()).hideFloatingActionButton();
        closeViewWineFragmentButton = rootView.findViewById(R.id.closeViewWineFragmentButton);
        closeViewWineFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    ((WineListActivity) requireActivity()).showFloatingActionButton();
                    getFragmentManager().beginTransaction().remove(ViewWineFragment.this).commit();
                }
            }
        });


        return rootView;
    }

}
