
package com.example.barcode;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

@SuppressLint("SdCardPath")
public class MainActivity extends Activity {

    EditText userName;
    EditText userPhone;
    EditText userEmail;
    ImageView imgBarCode;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.txtFullName);
        userPhone = (EditText) findViewById(R.id.txtPhoneNumber);
        userEmail = (EditText) findViewById(R.id.txtEmail);
        imgBarCode = (ImageView) findViewById(R.id.imgBarcode);
    }

    @SuppressLint("NewApi")
    public void generatorBarCode(View v) {

        String info = "";
        info += userName.getText().toString() + "\n";
        info += userPhone.getText().toString() + "\n";
        info += userEmail.getText().toString();

        // Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        // Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(info,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            bitmap = qrCodeEncoder.encodeAsBitmap();

            imgBarCode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void saveToSDCard(View v) {
        try {
            Calendar c = Calendar.getInstance(); 
            SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
            String formattedDate = df.format(c.getTime());
            FileOutputStream out = new FileOutputStream("/sdcard/"+formattedDate+".png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Toast.makeText(MainActivity.this, "Save image successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnCapture:
                startActivity(new Intent(MainActivity.this,ScanQRCode.class));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
