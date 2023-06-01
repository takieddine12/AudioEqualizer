package com.app.soundequalizer;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import linc.com.library.AudioTool;
import linc.com.library.callback.OnFileComplete;
import linc.com.library.types.Echo;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Button saveOutPut,playFile;

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


    ///-------------------------- Spinner
    private Spinner spinner;
    private Echo echoValue;
    private Echo[] echos = new Echo[]{
            Echo.ECHO_FEW_MOUNTAINS,Echo.ECHO_METALLIC,Echo.ECHO_OPEN_AIR,Echo.ECHO_TWICE_INSTRUMENTS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = String.valueOf(System.currentTimeMillis());
        saveOutPut = findViewById(R.id.saveOutPut);
        playFile = findViewById(R.id.playTrack);
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
        spinner = findViewById(R.id.spinner);

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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // TODO : Apply Echo Effect
        ArrayAdapter<Echo>  arrayAdapter = new ArrayAdapter<Echo>(this, android.R.layout.simple_spinner_item,echos);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                echoValue = echos[position];
                Log.d("TAG","Selected Echo "  + echoValue);
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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        saveOutPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                  AudioTool.getInstance(MainActivity.this)
                            .withAudio(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "file.wav"))
                            .removeAudioNoise(new OnFileComplete() {
                              @Override
                              public void onComplete(File output) {

                              }
                          })
                            .changeAudioBass(bassValue, widthValue, frequencyValue, new OnFileComplete() {
                              @Override
                              public void onComplete(File output) {

                              }
                          })
                            .changeAudioSpeed(audioSpeedValue, new OnFileComplete() {
                              @Override
                              public void onComplete(File output) {

                              }
                          })
                            .applyShifterEffect(transitionTime, shifterWidthValue, new OnFileComplete() {
                              @Override
                              public void onComplete(File output) {

                              }
                          })
                            .applyEchoEffect(echoValue, new OnFileComplete() {
                              @Override
                              public void onComplete(File output) {

                              }
                          })
                            .saveCurrentTo(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + (id + ".wav")) // Audio file with echo and without vocal
                            .release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        playFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + (id + ".wav"));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // TODO : REQUEST PERMISSIONS
        ActivityCompat.requestPermissions(MainActivity.this, permissions(), 1);


    }

    public static String[] storge_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }
}
