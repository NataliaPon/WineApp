package com.example.wineapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wineapp.models.OperationType;
import com.example.wineapp.models.Wine;
import com.example.wineapp.viewmodels.AddWineViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;

public class AddWineFragment extends Fragment {

    private AddWineViewModel mViewModel;
    private static Wine wine;
    private static OperationType operationType;
    private ImageView closeAddWineFragmentButton, imageViewPhoto;
    private Toolbar topToolbar;
    private EditText editTextName, editTextFruits, editTextFruitsSugar, editTextWater, editTextSugar, editTextYeastTolerance, editTextDescription, editTextSweetener;
    private TextView textViewAlcohol, textViewStartDate, textViewBottlingDate;
    Button buttonColor;
    private FloatingActionButton fabPhoto;
    private Double totalSugar=0d, totalVolume=0d, alcohol=0d;

    AlertDialog chooseColorDialog;
    Integer chosenColor;


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
        imageViewPhoto = rootView.findViewById(R.id.imageViewPhoto);
        buttonColor = rootView.findViewById(R.id.buttonColor);
        fabPhoto = rootView.findViewById(R.id.fabPhoto);

        ((WineListActivity) requireActivity()).hideFloatingActionButton();

        buttonColor.setOnClickListener(view -> openChooseColorDialog());

        if (operationType != null && wine != null) {
            if (operationType.equals(OperationType.Edit) || operationType.equals(OperationType.AddPhoto) || operationType.equals(OperationType.EditPhoto)) {
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
                if(wine.getPhoto()!=null){
                    try {
                        imageViewPhoto.setVisibility(View.VISIBLE);
                        imageViewPhoto.setImageURI(Uri.parse(wine.getPhoto()));
                    }catch (Exception e){
                        e.printStackTrace();
                        imageViewPhoto.setVisibility(View.GONE);
                    }
                }
                if(wine.getBackgroundColor()!= -1){
                    buttonColor.setBackgroundTintList(ContextCompat.getColorStateList(requireActivity(), wine.getBackgroundColor()));
                    chosenColor = wine.getBackgroundColor();
                }
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

        fabPhoto.setOnClickListener(v -> {
//            Log.e("fabPhoto","clicked");
            //todo show hide fragment ???
            if (getFragmentManager() != null) {
                if(operationType.equals(OperationType.Add) || operationType.equals(OperationType.AddPhoto))
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.wineListContainer, CaptureImageFragment.newInstance(getWine(),OperationType.AddPhoto), "CaptureImageFragment")
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                else if(operationType.equals(OperationType.Edit) || operationType.equals(OperationType.EditPhoto))
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.wineListContainer, CaptureImageFragment.newInstance(wine,OperationType.EditPhoto), "CaptureImageFragment")
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
            }
        });

        TextWatcher updateTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                getAlcohol();
            }
        };

        editTextWater.addTextChangedListener(updateTextWatcher);
        editTextFruits.addTextChangedListener(updateTextWatcher);
        editTextSugar.addTextChangedListener(updateTextWatcher);
        editTextFruitsSugar.addTextChangedListener(updateTextWatcher);

        textViewStartDate.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Wybierz datę nastawu")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build();
            datePicker.show(getFragmentManager(),"tag");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy/MM/dd");
                textViewStartDate.setText(simpleFormat.format(selection)+"");
            });
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

    private void openChooseColorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choose_color, null);
        view.findViewById(R.id.buttonBlack).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorBlack;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonBlue).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorBlue;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonGreen).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorGreen;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonGrey).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorGrey;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonOrange).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorOrange;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonPink).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorPink;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonViolet).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorViolet;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonYellow).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorYellow;
            setButtonColor(chooseColorDialog);
        });
        view.findViewById(R.id.buttonRed).setOnClickListener(view1 -> {
            chosenColor = R.color.labelColorRed;
            setButtonColor(chooseColorDialog);
        });
        builder.setView(view);
        chooseColorDialog = builder.create();
        chooseColorDialog.show();
    }

    void setButtonColor(AlertDialog chooseColorDialog){
        buttonColor.setBackgroundTintList(ContextCompat.getColorStateList(requireActivity(), chosenColor));
        if(chooseColorDialog != null && chooseColorDialog.isShowing())
            chooseColorDialog.dismiss();
    }

    private Wine getWine(){
        return new Wine(
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
                textViewBottlingDate.getText().toString(),
                chosenColor != null ? chosenColor : -1
        );
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
            Wine newWine = getWine();
            switch (operationType){
                case Add:
                    mViewModel.insert(newWine);
                    break;
                case AddPhoto:
                    newWine.setPhoto(wine.getPhoto());
                    mViewModel.insert(newWine);
                    break;
                case Edit:
                case EditPhoto:
                    newWine.setId(wine.getId());
                    if(wine.getPhoto()!=null)
                        newWine.setPhoto(wine.getPhoto());
                    mViewModel.update(newWine);
                    break;
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
