package com.app.soundequalizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import linc.com.library.AudioTool;
import linc.com.library.types.Echo;

public class MainActivity extends AppCompatActivity {

    private String fileUri;
    private Button saveOutPut,playButton;
    private MediaPlayer mediaPlayer;
    //-------------------------- Audio Speed
    private SeekBar audioSpeedSeekBar;
    private TextView audioSpeedText;
    private float audioSpeedValue;

    //---------------------- AUDIO BASS
    private SeekBar bassSeekBar,widthSeekBar,frequencySeekBar;
    private TextView bassText , widthText , frequencyText;
    private float bassValue,widthValue;
    private int frequencyValue;

    //----------------------- SHIFTER
    private SeekBar shifterTimeSeekBar,shifterWidthSeekBar;
    private TextView shiftTimeText,shiftWidthText;
    private int transitionTime;
    private float shifterWidthValue;

    //----------------------- Echo
    private Echo echoValue;
    private final Echo[] echos = new Echo[]{
            Echo.ECHO_FEW_MOUNTAINS,Echo.ECHO_METALLIC,Echo.ECHO_OPEN_AIR,Echo.ECHO_TWICE_INSTRUMENTS
    };

    private ProgressBar progressBar;
    String id = String.valueOf(System.currentTimeMillis());
    @SuppressLint({"SetTextI18n", "IntentReset"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // TODO : Reference all views needed
        saveOutPut = findViewById(R.id.saveOutPut);
        progressBar = findViewById(R.id.progressBar);
        audioSpeedSeekBar = findViewById(R.id.audioSpeedSeekBar);
        bassSeekBar = findViewById(R.id.bassSeekBar);
        widthSeekBar = findViewById(R.id.bassWidthSeekBar);
        frequencySeekBar = findViewById(R.id.frequencySeekBar);
        shifterTimeSeekBar = findViewById(R.id.shifterTimeSeekBar);
        shifterWidthSeekBar = findViewById(R.id.shifterWidthSeekBar);
        bassText = findViewById(R.id.bassText);
        widthText = findViewById(R.id.baseWidthText);
        frequencyText = findViewById(R.id.frequenyText);
        audioSpeedText = findViewById(R.id.audioSpeedText);
        shiftTimeText = findViewById(R.id.shifterTimeText);
        shiftWidthText = findViewById(R.id.shifterWidthText);
        playButton = findViewById(R.id.play);
        ///-------------------------- Spinner
        Spinner spinner = findViewById(R.id.spinner);

        // TODO : Audio Speed SeeKBar
        audioSpeedSeekBar.setProgress(100);
        audioSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               audioSpeedSeekBar.setProgress(progress);
               audioSpeedText.setText("" + progress + "%");
               audioSpeedValue = 100f;

                Log.d("TAG","Audio Speed Progress " + progress);
                createTrack();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // TODO : Apply Echo Effect
        ArrayAdapter<Echo>  arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, echos);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                echoValue = echos[position];
                Log.d("TAG","Selected Echo "  + echoValue);
                createTrack();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // TODO : Audio Bass Changer
        bassSeekBar.setProgress(100);
        widthSeekBar.setProgress(100);
        frequencySeekBar.setProgress(100);
        bassSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bassSeekBar.setProgress(progress);
                bassValue = progress;
                bassText.setText("" + progress + "%");
                createTrack();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widthSeekBar.setProgress(progress);
                widthValue = progress;
                widthText.setText("" + progress + "%");
                createTrack();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        frequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                frequencySeekBar.setProgress(progress);
                frequencyValue = progress;
                frequencyText.setText("" + progress + "%");
                createTrack();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // TODO : Audio Shifter
        shifterTimeSeekBar.setProgress(100);
        shifterWidthSeekBar.setProgress(100);
        shifterTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shifterTimeSeekBar.setProgress(progress);
                transitionTime = progress;
                shiftTimeText.setText("" + progress + "%");
                createTrack();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        shifterWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shifterWidthSeekBar.setProgress(progress);
                shifterWidthValue = progress;
                shiftWidthText.setText("" + progress + "%");
                createTrack();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        saveOutPut.setOnClickListener(v -> {
             Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
             activityResultLauncher.launch(intent);
        });

        // TODO : REQUEST PERMISSIONS
        ActivityCompat.requestPermissions(MainActivity.this, permissions(), 1);

        playButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            if(mediaPlayer != null){
                mediaPlayer.release();
                mediaPlayer = null;
            }

            // TODO : Play Audi After 2 seconds
            mediaPlayer = new MediaPlayer();
            new Handler().postDelayed(() -> {
                try {
                    mediaPlayer.setDataSource(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/new-"  + id + getAudioExtension(fileUri));
                    mediaPlayer.setLooping(true);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG","Media Player Exception " + e.getMessage());
                }
            },5000);
        });

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        // TODO : Generate random id for the saved and modified audio file
                        assert result.getData() != null;
                        fileUri = _getRealPathFromURI(result.getData().getData());
                        if(fileUri != null){
                            saveOutPut.setVisibility(View.GONE);
                            playButton.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("TAG","File Uri is null");
                        }
                    }
                }
            });

    public static String[] storage_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    private String getAudioExtension(String filePath){
        Log.d("TAG","FILE " + filePath);
        Log.d("TAG","FILE CROPPED" + filePath.substring(filePath.lastIndexOf(".")));
        return filePath.substring(filePath.lastIndexOf("."));
    }


    private void createTrack(){
         if(fileUri != null){
             Toast.makeText(MainActivity.this, "File is not null", Toast.LENGTH_SHORT).show();
             try {
                 AudioTool.getInstance(MainActivity.this)
                         .withAudio(fileUri)
                         .removeAudioNoise(output -> {

                         })
                         .changeAudioBass(bassValue, widthValue, frequencyValue, output -> {

                         })
                         .changeAudioSpeed(audioSpeedValue, output -> {

                         })
                         .applyShifterEffect(transitionTime, shifterWidthValue, output -> {

                         })
                         .applyEchoEffect(echoValue, output -> {

                         })
                         .saveCurrentTo(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/new-"  + id + getAudioExtension(fileUri)) // Audio file with echo and without vocal
                         .release();
             } catch (Exception e){
                 Log.d("TAG","Audio Tool Exception " + e.getMessage());
             }
         } else {
             Toast.makeText(this, "Please pick a file.. ", Toast.LENGTH_SHORT).show();
         }
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    private String _getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Audio.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
