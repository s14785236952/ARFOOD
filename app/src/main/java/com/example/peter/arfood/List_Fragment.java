package com.example.peter.arfood;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;
import static com.example.peter.arfood.R.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class List_Fragment extends Fragment {

    private List<String> All_List = new ArrayList<String>(Arrays.asList("Tea's", "俗又大碗", "鴨肉羹", "芋園", "阿寬", "8鍋", "布格", "好小子", "橘屋"));
    private List<String> Spinner_List = new ArrayList<String>(Arrays.asList("全部"));
    private boolean isChecked[];
    private ArrayAdapter<String> listAdapter;
    private ArrayAdapter<String> SpinnerAdapter;
    private Spinner spinner;
    private ListView listView;
    private String[] Type;
    View v;

    public List_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(layout.fragment_list_,container,false);
        listView = (ListView) v.findViewById(id.List_View1);
        listAdapter = new ArrayAdapter<String>(getActivity(),simple_list_item_1,All_List);
        listView.setAdapter(listAdapter);

        spinner = (Spinner)v.findViewById(id.List_Spanner);
        SpinnerAdapter = new ArrayAdapter<String>(getActivity(),simple_spinner_dropdown_item,Spinner_List);
        spinner.setAdapter(SpinnerAdapter);

        v.findViewById(id.Type_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditText();
            }
        });
        return v;
    }
    private void showEditText(){
        AlertDialog.Builder Dialog_EditText = new AlertDialog.Builder(getActivity());

        final EditText editText = new EditText(getActivity());
        Dialog_EditText.setView(editText);

        Dialog_EditText.setTitle("類別")
                .setMessage("輸入類型")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Spinner_List.add(editText.getText().toString());
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface,int arg1){

                    }
                })
                .create()
                .show();

        showMultiChoiceItems();
    }
    private void showMultiChoiceItems(){
        isChecked = new boolean[All_List.size()];
        AlertDialog.Builder Dialog_List = new AlertDialog.Builder(getActivity());
        Dialog_List.setTitle("餐廳");
        Dialog_List.setMultiChoiceItems(All_List.toArray(new CharSequence[All_List.size()]),isChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                isChecked[i] = b;
            }
        });
        Dialog_List.setNegativeButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String result = "";

                for(int j=0;j< All_List.size();j++){
                    if(isChecked[j]==true){
                        result = All_List.get(j)+","+result;
                    }
                }
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }).setNeutralButton("取消",null)
                .create()
                .show();
    }
}