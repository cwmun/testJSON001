package com.sislabmcw.testjson001;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sislabmcw.testjson001.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

//----------------------------Main-------------------
public class MainActivity extends AppCompatActivity {
    public String jsonFileName, imageFileName;
    Button btnSave, btnLoadJSON, btnAddImageItems, btnInfo;
    ImageButton btnFinish;
    public EditText edtJsFileName, edtInfo, edtImageID, edtImageWidth, edtImageHeight, edtImageFileName;
    public TextView tvAnnotation;
    public ObjInfo           infoObj;
    public ObjLicenses       licenseObj;
    public List<ObjImages>   imageObj    = new ArrayList<>();
    public List<ObjAnnot>    annotObj    = new ArrayList<>();
    public List<ObjCategory> categoryObj = new ArrayList<>();
    public ObjImgLabel       imageLabelObj;

    public Gson              gson = new GsonBuilder().setPrettyPrinting().create();
    public Gson              gson1= new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnSave = binding.btnSave;
        btnLoadJSON = binding.btnLoadJSON;
        btnAddImageItems = binding.btnAddImageItems;
        btnInfo = binding.btnInfo;
        btnFinish = binding.btnFinish;
        edtJsFileName = binding.edtJsFileName;
        edtInfo = binding.edtInfo;
        edtImageID = binding.edtImageID;
        edtImageWidth = binding.edtImageWidth;
        edtImageHeight = binding.edtImageHeight;
        edtImageFileName = binding.edtImageFileName;
        tvAnnotation = binding.tvAnnotation;

        //==== Object creation of the SIS-COCO-Labeling Annotation for dental images
        //-- Common virtual parameters
        int numImage =   1;   // Two-image data set
        int numSeg   =   2;   // Two segmentations for all images
        int numPnt   =   5;   // Six points for all segmentation
        //   Common image sizes
        int width    = 256;
        int height   = 256;

        //-- info object--
        /*
        ObjInfo(String version, Year verYear, String description,
                                   String contributors, String date)
         */
        String verYear = Year.of(2024).toString();
        String version     = "0.0.0.1";
        String description = "[SIS Lab] COCO Labeling-data set";
        String contributors= "Chi-Woong Mun, School of BME, Inje University";
        LocalDate nowDate = LocalDate.now();
//        @SuppressLint("SimpleDateFormat") String date;
//        date = new SimpleDateFormat("yyyy년 MM월 dd일").format(nowDate);
        String date = nowDate.toString();
        Log.d("SIS-info1", "verYear = "+verYear);
        infoObj = new ObjInfo(version, verYear, description, contributors, date);

        //-- licenses object --
        //   ObjLicenses(int id, String url, String name)
        int id = 1;
        String url  = "http://bme.inje.ac.kr";
        String name =   "ALL RIGHTS RESERVED";
        licenseObj = new ObjLicenses(id, url, name);

        //-- images and annotations objects --
        /*
            1.   ObjImages(int id, int width, int height, String imgFileName)
            2.   public ObjAnnot(int id, int iscrowd, int image_id, int category_id,
                                 ArrayList segmentation, ArrayList bbox, float area)
        */
        int   iscrowd;                             //*****
        int   image_id;                            //*****
        int   category_id;                         //*****
        int   severity_id;                         //*****
        List<List>    seg = new ArrayList<>();     //*****
        List<Float>  bbox = new ArrayList<>();     //*****
        float  area;                               //*****

        List<Float> pathX = new ArrayList<>();
        List<Float> pathY = new ArrayList<>();
        List<Float> path = new ArrayList<>();
        Random random = new Random();
        float randX, randY;
        for (int i=0; i<numImage; i++) {         //-- The i-th image
            id          = i;
            iscrowd     = 999;
            image_id    = i+1;
            category_id = 999;
            severity_id = 999;

            for (int iSeg=0; iSeg<numSeg; iSeg++) {   //---- The iseg-th segmentation
                pathX.clear();
                pathY.clear();
                path.clear();
                bbox.clear();
                seg.clear();
                for (int ip=0; ip<numPnt; ip++) {            //------ The ip-th point of polygon
                    // The points are randomly generated
                    randX = (float)(random.nextFloat()*100.0);
                    randY = (float)(random.nextFloat()*100.0);
                    pathX.add(randX); // x-coordinate list of segmentation
                    pathY.add(randY); // y-coordinate list of segmentation
                    path.add(randX);  // point-coordinate list of segmentation ("x",y)
                    path.add(randY);  // point-coordinate list of segmentation (x,"y")
                }
                seg.add( path );
                float minPathX = Collections.min(pathX);   bbox.add(minPathX);
                float minPathY = Collections.min(pathY);   bbox.add(minPathY);
                float maxPathX = Collections.max(pathX);   bbox.add(maxPathX);
                float maxPathY = Collections.max(pathY);   bbox.add(maxPathY);
                area = 999.9f;
                annotObj.add( new ObjAnnot(iSeg, iscrowd, image_id, category_id, seg, bbox, area));
            }
            String imageFileName = String.format("%06d", image_id)+".jpg";
            //imageObj.add( new ObjImages(id, width, height, imgFileName) );
            imageObj.add( new ObjImages(image_id, width, height, imageFileName) );
        }

        //-- categories object--
        // ObjCategory(int id, String name)
        //     array_categories.xml 리소스 파일 --> categories array 추출
        String[] categories = getResources().getStringArray(R.array.Symptoms);
        int lenCategories = categories.length;
        for (int i=0; i<lenCategories; i++) {
            categoryObj.add(new ObjCategory(i, categories[i]));
        }

        imageLabelObj = new ObjImgLabel(infoObj, licenseObj, imageObj, annotObj, categoryObj);

        String strJson = gson.toJson(imageLabelObj);
        String strJson1= gson1.toJson(imageLabelObj);

        tvAnnotation.setMovementMethod(new ScrollingMovementMethod());  // Scroll function of textView
        tvAnnotation.setText(strJson);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        // JSON 파일명 입력
        edtJsFileName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged", "No-operation.");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("beforeTextChanged", "No-operation.");
            }
            @Override
            public void afterTextChanged(Editable s) {
                jsonFileName = s.toString();
                tvAnnotation.setText(s);
            }
        });
    }

}