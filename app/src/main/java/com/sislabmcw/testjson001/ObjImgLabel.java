package com.sislabmcw.testjson001;

import android.icu.text.SimpleDateFormat;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class AnObject {
    private String key;
    private String str;
    private int    iValue;
    private float  fValue;
    private Year   verYear;
    private Object object;

    public AnObject(String key, Object object) {
        this.key    = key;
        this.object = object;
    }
    public AnObject(String key, String str) {
        this.key = key;
        this.str = str;
    }

    public AnObject(String key, int iValue) {
        this.key    = key;
        this.iValue = iValue;
    }
    public AnObject(String key, float fValue) {
        this.key    = key;
        this.fValue = fValue;
    }
    public AnObject(String key, Year verYear) {
        this.key    = key;
        this.verYear= verYear;
    }
}

class ObjLicenses {
    @SerializedName("id")    private int    mId;
    @SerializedName("url")   private String mUrl;
    @SerializedName("name")  private String mName;
    public ObjLicenses(int id, String url, String name) {
        this.mId = id;
        this.mUrl = url;
        this.mName = name;
    }
    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }
}

class ObjInfo {
    @SerializedName("version")
    private String mVersion;
    @SerializedName("verYear")
    private String mVerYear;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("contributors")
    private String mContributors;
    @SerializedName("date")
    private String mDate;

    public ObjInfo(String version, String verYear, String description,
                                   String contributors, String date) {
        mVersion     = version;
        mVerYear     = verYear;
        mDescription = description;
        mContributors= contributors;
        mDate        = date;
    }
}

class ObjImages {
    @SerializedName("id")
    private int    mId;
    @SerializedName("width")
    private int    mWidth;
    @SerializedName("height")
    private int    mHeight;
    @SerializedName("imgFileName")
    private String mImgFileName;

    public ObjImages(int id, int width, int height, String imgFileName) {
        mId          = id;
        mWidth       = width;
        mHeight      = height;
        mImgFileName = imgFileName;
    }
}

class ObjAnnot {
    @SerializedName("id")
    private int mId;
    @SerializedName("iscrowd")
    private int mIscrowd;
    @SerializedName("image_id")
    private int mImage_id;
    @SerializedName("category_id")
    private int mCategory_id;
    @SerializedName("segmentation")
    private List<Float> mSegmentation;

    @SerializedName("bbox")
    private List<Float> mBbox = new ArrayList<>();
    @SerializedName("area")
    private float mArea;

    public ObjAnnot(int id, int iscrowd, int image_id, int category_id,
                              List segmentation, List bbox, float area) {
        this.mId = id;
        this.mIscrowd     = iscrowd;
        this.mImage_id    = image_id;
        this.mCategory_id = category_id;
        this.mSegmentation= segmentation;
        this.mBbox        = bbox;
        this.mArea        = area;
    }
}

class ObjCategory {
    @SerializedName("id")
    private int    mId;
    @SerializedName("name")
    private String mName;

    public ObjCategory(int id, String name) {
        mId   = id;
        mName = name;
    }
}

class ObjImgLabel {
    @SerializedName("info")           private ObjInfo mInfo;

    @SerializedName("licenses")       private ObjLicenses mLicenses;
    @SerializedName("images")         private List<ObjImages> mImages;
    @SerializedName("annotations")    private List<ObjAnnot> mAnnotation;
    @SerializedName("categories")     private List<ObjCategory> mCategories;

    public ObjImgLabel(ObjInfo info, ObjLicenses licenses, List images, List annotations, List categories){
        mInfo       = info;
        mLicenses   = licenses;
        mImages     = images;
        mAnnotation = annotations;
        mCategories = categories;
    }
}
