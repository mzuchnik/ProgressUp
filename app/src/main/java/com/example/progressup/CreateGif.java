package com.example.progressup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class CreateGif{

    private List<String> files;
    private String nameOutput;
    private Bitmap[] bitmaps;
    private AnimatedGifEncoder encoder;
    private int delay;
    private String gifPath;


    public CreateGif(List<String> files) {
        this.files = files;
        delay = 200;
    }

    public CreateGif(List<String> files, int delay) {
        this.files = files;
        this.delay = delay;
    }

    private byte[] getByteGif()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        encoder = new AnimatedGifEncoder();

        encoder.start(bos);

        encoder.setDelay(delay);

        for (String a: files) {
            File image = new File(a);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            encoder.addFrame(bitmap);
        }

        encoder.finish();

        return bos.toByteArray();
    }

    public String createGifAndGetPath() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory()+"/"+UUID.randomUUID());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(getByteGif());
        fileOutputStream.close();
        gifPath = file.getPath();
        return file.getPath();
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
