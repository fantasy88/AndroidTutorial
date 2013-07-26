
package com.example.filedemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileHandler extends Activity {

    public static final int EXTERNAL_SD = 1;
    public static final int INTERNAL_SD = 2;
    private Context myContext;

    public FileHandler(Context context) {
        this.myContext = context;
    }

    public boolean checkFileExists(int type, String filePath) {
        switch (type) {
            case EXTERNAL_SD:
                File extStore = Environment.getExternalStorageDirectory();
                File myFile = new File(extStore.getAbsolutePath() + filePath);
                if (myFile.exists())
                {
                    return true;
                }
                else {
                    return false;
                }
            case INTERNAL_SD:
                try {
                    InputStream in = myContext.openFileInput(filePath);
                    if (in != null)
                    {
                        return true;
                    }
                    else {
                        return false;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            default:
                return false;
        }
    }

    public void saveTextFile(int type, String filePath, String content) throws IOException {
        switch (type) {
            case EXTERNAL_SD:
                try {
//                    File myFile = new File(filePath);
//                    myFile.createNewFile();
                    FileOutputStream stream = new FileOutputStream(filePath,true);
                    OutputStreamWriter writer = new OutputStreamWriter(stream);
//                    BufferedWriter bf = new BufferedWriter(writer);
                    writer.append(content);
                    writer.flush();
                    writer.close();
                    Toast.makeText(myContext,
                            "Done writing SD '" + filePath + "'",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case INTERNAL_SD:
                OutputStreamWriter out = new OutputStreamWriter(myContext.openFileOutput(filePath, MODE_APPEND));
                out.write(content);
                out.flush();
                out.close();
                Toast.makeText(myContext,
                        "Done writing Internal '" + filePath + "'",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public String openTextFile(int type, String filePath) throws IOException {
        String results = "";
        switch (type) {
            case EXTERNAL_SD:
                File myFile = new File(filePath);
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
                }
                results = aBuffer.toString();
                myReader.close();
                return results;
            case INTERNAL_SD:
                InputStream in = myContext.openFileInput(filePath);
                if (in!=null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String str = "";
                StringBuffer buf=new StringBuffer();
                while ((str = reader.readLine()) != null) {
                buf.append(str+"\n");
                }
                in.close();
                results = buf.toString();
                }
                return results;
            default:
                return results;
        }
    }
}
