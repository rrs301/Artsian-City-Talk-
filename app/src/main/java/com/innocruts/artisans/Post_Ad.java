package com.innocruts.artisans;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Post_Ad extends Activity implements View.OnClickListener {
public static final String UPLOAD_URL = "http://artisans.host/appBackend/AdsImageUploadWithData.php?";
public static final String UPLOAD_KEY = "image";
        String imageupload,link;
        String name;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
private int PICK_IMAGE_REQUEST = 1;
EditText Title,Price,Description,Category,Address;
    RadioGroup SellRent;
    Spinner spinner;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String UserName,UserPhone;
private Button buttonChoose;
private Button buttonUpload;
private Button buttonView;

private ImageView imageView;

private Bitmap bitmap;

private Uri filePath;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__ad);

    Title=(EditText)findViewById(R.id.NameEditBox);
    Price=(EditText)findViewById(R.id.PriceEditBox);
    Description=(EditText)findViewById(R.id.AdsDescription);
    Address=(EditText)findViewById(R.id.AdsAddressEditBox);
    pref=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    editor = pref.edit();
    try {
        UserName=pref.getString("UserName",null);
        UserPhone = pref.getString("MobileNumber", null);
        Log.i("UserName",UserName+UserPhone);
    }
    catch (Exception e)
    {

    }
//////////////////////////////////////////////////////////////////////////////////
    SellRent = (RadioGroup) findViewById(R.id.RentSell);

   // String Wt= String.valueOf(radioButton.getText());
 /////////////////////////////////////////////////////////////////////////////////////

     spinner=(Spinner)findViewById(R.id.spinner1);
  //  String category = spinner.getSelectedItem().toString();

 /////////////////////////////////////////////////////////////////////////////////////////////
   // Log.i("Category and SellRent",category+Wt);


//buttonChoose = (Button) findViewById(R.id.buttonChoose);
    buttonUpload = (Button) findViewById(R.id.submit);
    //  buttonView = (Button) findViewById(R.id.buttonViewImage);

  //  imageView = (ImageView) findViewById(R.id.imageupload);
    imageView = (ImageView) findViewById(R.id.AdsImage);

//        imageView.setOnClickListener(this);
    buttonUpload.setOnClickListener(this);
    // buttonChoose.setOnClickListener(this);
        }

        public void showFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

        filePath = data.getData();
        try {
        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
        }

public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
        }

private void uploadImage() {
class UploadImage extends AsyncTask<Bitmap, Void, String> {

    ProgressDialog loading;
    RequestHandler rh = new RequestHandler();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(Post_Ad.this, "Uploading...", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
      //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Post_Ad.this,ThankYouPage.class);
        startActivity(intent);
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        Bitmap bitmap = params[0];
        String uploadImage = getStringImage(bitmap);

        HashMap<String, String> data = new HashMap<String, String>();

        data.put(UPLOAD_KEY, uploadImage);
       String UPLOAD_url=UPLOAD_URL+"name="+Title.getText().toString().replace(" ","%20")+"&price="+Price.getText().toString().replace(" ","%20")+"&category="
                +spinner.getSelectedItem().toString()+"&description="+Description.getText().toString().replace(" ","%20")+
               "&address="+Address.getText().toString().replace(" ", "%20")
               +"&username="+UserName.replace(" ","%20")+"&userphone="+UserPhone.replace(" ","%20")+"&rentsell="+radioButton.getText().toString();
        Log.i("Radio button Url",radioButton.getText().toString());
        String result = rh.sendPostRequest(UPLOAD_url, data);

        return result;
    }
}

UploadImage ui = new UploadImage();
ui.execute(bitmap);
        }

@Override
public void onClick(View v) {


        if (v == buttonUpload) {
            int selectedId = SellRent.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);

//            if(Title.getText()==null||Price.getText()==null||Category.getText()==null||Description.getText()==null||Address.getText()==null)
//            {
//                Toast.makeText(getApplicationContext(),"Please Insert All Field",Toast.LENGTH_SHORT).show();
//            }
//            else {
                uploadImage();
           // }
        }

        if (v == buttonView) {
        viewImage();
        }
        }

private void viewImage() {
        //startActivity(new Intent(this, ImageListView.class));
        }
        }
