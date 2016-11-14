package chen.kuanlin.aespeedtest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView_main;
    private TextView textView_time;
    private Button button_mode;
    private Button button_encrypt;
    private Button button_decrypt;

    // keyBytes同C語言中key
    private static byte[] key = new byte[] { 0x60, 0x3d, (byte) 0xeb,
            0x10, 0x15, (byte) 0xca, 0x71, (byte) 0xbe, 0x2b, 0x73,
            (byte) 0xae, (byte) 0xf0, (byte) 0x85, 0x7d, 0x77, (byte) 0x81,
            0x1f, 0x35, 0x2c, 0x07, 0x3b, 0x61, 0x08, (byte) 0xd7, 0x2d,
            (byte) 0x98, 0x10, (byte) 0xa3, 0x09, 0x14, (byte) 0xdf,
            (byte) 0xf4 };
    // iv同C語言中iv
    private static byte[] iv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04,
            0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

    private static String mode = null;
    private static List<String> mode_name = Arrays.asList("Openssl","BC","JNI");
    private static List<String> mode_list = new ArrayList<>();

    private FileInputStream fis = null;
    private FileOutputStream fos = null;
    private static byte[] plaindata;
    private static byte[] cipherdata;

    public static double total_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_main = (TextView)findViewById(R.id.textView_main);
        textView_time = (TextView)findViewById(R.id.textView_time);
        button_mode = (Button)findViewById(R.id.button_mode);
        button_encrypt = (Button)findViewById(R.id.button_encrypt);
        button_decrypt = (Button)findViewById(R.id.button_decrypt);

        mode_list.clear();
        mode_list.addAll(mode_name);

        selectmode(); //button_mode
        encrypt(); //button_encrypt
        decrypt(); //button_decrypt
    }

    public void selectmode(){
        button_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //選擇加密模式
                AlertDialog.Builder mode_select = new AlertDialog.Builder(MainActivity.this).
                        setItems(mode_list.toArray(new String[3]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mode = mode_list.get(which);
                                Toast.makeText(getApplicationContext(), mode, Toast.LENGTH_SHORT).show();
                            }
                        });
                mode_select.show();
            }
        });
    }

    public void encrypt() {
        button_encrypt.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View arg0){

                try{
                    //fis = new FileInputStream("sdcard/Luna.JPG");
                    fis = new FileInputStream("sdcard/Luna.pdf");
                    plaindata = new byte[fis.available()];
                    fis.read(plaindata);
                    fis.close();
                }catch (IOException e){}

                Context context;
                switch(mode){
                    case "Openssl":
                        context = new Context(new Openssl());
                        cipherdata = context.executeEncrypt
                                (key, iv, plaindata);
                        break;
                    case "BC":
                        context = new Context(new BC());
                        cipherdata = context.executeEncrypt
                                (key, iv, plaindata);
                        break;
			        case "JNI":
				        context = new Context(new JNI());
				        cipherdata = context.executeEncrypt
				    		    (key, iv, plaindata);
				        break;
                }

                try {
                    //fos = new FileOutputStream("sdcard/cipher.JPG");
                    fos = new FileOutputStream("sdcard/cipher.pdf");
                    fos.write(cipherdata);
                    fos.flush();
                    fos.close();
                }catch (IOException e){}

                textView_time.setText("Time:"+String.valueOf(total_time)+"Millisecond");
            }
        });
    }

    public void decrypt(){
        button_decrypt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View arg0){

                try{
                    //fis = new FileInputStream("sdcard/cipher.JPG");
                    fis = new FileInputStream("sdcard/cipher.pdf");
                    cipherdata = new byte[fis.available()];
                    fis.read(cipherdata);
                    fis.close();
                }catch (IOException e){}

                Context context;
                switch(mode){
                    case "Openssl":
                        context = new Context(new Openssl());
                        plaindata = context.executeDecrypt
                                (key, iv, cipherdata);
                        break;
                    case "BC":
                        context = new Context(new BC());
                        plaindata = context.executeDecrypt
                                (key, iv, cipherdata);
                        break;
                    case "JNI":
				        context = new Context(new JNI());
				        plaindata = context.executeDecrypt
				    		    (key, iv, cipherdata);
				        break;
                }

                try {
                    //fos = new FileOutputStream("sdcard/New.JPG");
                    fos = new FileOutputStream("sdcard/New.pdf");
                    fos.write(plaindata);
                    fos.flush();
                    fos.close();
                }catch (IOException e){}
            }
        });
    }
}
