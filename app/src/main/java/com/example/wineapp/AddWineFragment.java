package com.example.wineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wineapp.models.OperationType;
import com.example.wineapp.models.Wine;
import com.example.wineapp.viewmodels.AddWineViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddWineFragment extends Fragment {

    private AddWineViewModel mViewModel;
    private static Wine wine;
    private static OperationType operationType;
    private ImageView closeAddWineFragmentButton;
    private Toolbar topToolbar;
    private EditText editTextName, editTextFruits, editTextFruitsSugar, editTextWater, editTextSugar, editTextYeastTolerance, editTextDescription, editTextSweetener;
    private TextView textViewAlcohol, textViewStartDate, textViewBottlingDate;
    private Double totalSugar=0d, totalVolume=0d, alcohol=0d;

    public static AddWineFragment newInstance(OperationType operationType, Wine wine) {
        Bundle args = new Bundle();
        args.putSerializable("wine",wine);
        args.putSerializable("operationType",operationType);
        AddWineFragment viewWineFragment = new AddWineFragment();
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
        View rootView = inflater.inflate(R.layout.add_wine_fragment, container, false);

        if (getArguments() != null) {
            wine= (Wine) getArguments().getSerializable("wine");
            operationType = (OperationType) getArguments().getSerializable("operationType");
        }

        setHasOptionsMenu(true);

        topToolbar = rootView.findViewById(R.id.toolbarAddWineFragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(topToolbar);
        editTextName = rootView.findViewById(R.id.editTextName);
        editTextFruits = rootView.findViewById(R.id.editTextFruits);
        editTextFruitsSugar = rootView.findViewById(R.id.editTextFruitsSugar);
        editTextWater = rootView.findViewById(R.id.editTextWater);
        editTextSugar = rootView.findViewById(R.id.editTextSugar);
        editTextYeastTolerance = rootView.findViewById(R.id.editTextYeastTolerance);
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
        textViewAlcohol = rootView.findViewById(R.id.textViewAlcohol);
        textViewStartDate = rootView.findViewById(R.id.textViewStartDate);
        textViewBottlingDate = rootView.findViewById(R.id.textViewBottlingDate);
        editTextSweetener = rootView.findViewById(R.id.editTextSweetener);

        ((WineListActivity) requireActivity()).hideFloatingActionButton();

        if (operationType != null && wine != null) {
            if (operationType.equals(OperationType.Edit)) {
                editTextName.setText(wine.getName());
                editTextFruits.setText(wine.getFruit()+"");
                editTextSweetener.setText(wine.getSweetener()+"");
                editTextWater.setText(wine.getWater()+"");
                editTextSugar.setText(wine.getSugar()+"");
                editTextFruitsSugar.setText(wine.getFruitSugar()+"");
                editTextYeastTolerance.setText(wine.getYeastTolerance()+"");
                editTextDescription.setText(wine.getDescription());
                textViewAlcohol.setText(wine.getAlcohol()+"");
                textViewStartDate.setText(wine.getStartDate());
                textViewBottlingDate.setText(wine.getBottlingDate());
            }
        }

        closeAddWineFragmentButton = rootView.findViewById(R.id.closeAddWineFragmentButton);
        closeAddWineFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    ((WineListActivity) requireActivity()).showFloatingActionButton();
                    getFragmentManager().beginTransaction().remove(AddWineFragment.this).commit();
                }
            }
        });

        TextWatcher volumeChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAlcohol();
            }
        };

        TextWatcher sugarChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAlcohol();
            }
        };

        editTextWater.addTextChangedListener(volumeChange);
        editTextFruits.addTextChangedListener(volumeChange);
        editTextSugar.addTextChangedListener(sugarChange);
        editTextFruitsSugar.addTextChangedListener(sugarChange);

        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> datePicker =
                        MaterialDatePicker.Builder.datePicker()
                                .setTitleText("Wybierz datę nastawu")
                                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                .build();
                datePicker.show(getFragmentManager(),"tag");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy/MM/dd");
                        textViewStartDate.setText(simpleFormat.format(selection)+"");
                    }

                });
            }
        });

        textViewBottlingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> datePicker =
                        MaterialDatePicker.Builder.datePicker()
                                .setTitleText("Wybierz datę butelkowania")
                                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                .build();
                datePicker.show(getFragmentManager(),"tag");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy/MM/dd");
                        textViewBottlingDate.setText(simpleFormat.format(selection)+"");
                    }
                });
            }
        });

        return rootView;
    }

    Double getAlcohol(){
        totalVolume = mViewModel.countTotalVolume(editTextWater.getText().toString(), editTextFruits.getText().toString(), editTextSugar.getText().toString());
        totalSugar = mViewModel.countTotalSugar(editTextSugar.getText().toString(), editTextFruitsSugar.getText().toString(), editTextFruits.getText().toString());

        if(totalVolume>0 && totalSugar>0){
            alcohol = mViewModel.countAlcohol(totalVolume,totalSugar);
            textViewAlcohol.setText(alcohol+"%");
        }
        return alcohol;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.saveMenuButton){
            //zapisać dane
            Wine newWine = new Wine(
                    editTextName.getText().toString(),
                    editTextDescription.getText().toString(),
                    getAlcohol(),
                    mViewModel.editTextToDouble(editTextSugar.getText().toString()),
                    totalVolume,
                    mViewModel.editTextToDouble(editTextWater.getText().toString()),
                    mViewModel.editTextToDouble(editTextFruits.getText().toString()),
                    mViewModel.editTextToDouble(editTextFruitsSugar.getText().toString()),
                    mViewModel.editTextToDouble(editTextSweetener.getText().toString()),
                    mViewModel.editTextToDouble(editTextYeastTolerance.getText().toString()),
                    textViewStartDate.getText().toString(),
                    textViewBottlingDate.getText().toString()
            );
            if (operationType.equals(OperationType.Edit)) {
                newWine.setId(wine.getId());
                mViewModel.update(newWine);
            }else {
                mViewModel.insert(newWine);
            }

//            Toast.makeText(getActivity(),"Dodano",Toast.LENGTH_SHORT).show();
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().remove(AddWineFragment.this).commit();
            }
            return true;
        }else {
            return false;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(AddWineViewModel.class);//this
    }

}
