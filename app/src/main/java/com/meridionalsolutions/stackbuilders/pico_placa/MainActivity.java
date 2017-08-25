package com.meridionalsolutions.stackbuilders.pico_placa;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.GREEN;

public class MainActivity extends AppCompatActivity {

    //Variable Declaration

    EditText placa, fecha, hora;
    int verde, amarillo, rojo;
    TextView mensaje;
    Button  consulta;
    int[] meses;
    String[] semana, diaPicoPlaca;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Color Variables

        verde = Color.rgb(140,250,80);
        amarillo = Color.rgb(250,250,150);
        rojo = Color.rgb (255,100,100);

        //Indexing variables with view resources from .xml
        placa = (EditText) findViewById(R.id.etPlaca);
        fecha = (EditText) findViewById(R.id.etFecha);
        hora = (EditText) findViewById(R.id.etHora);
        consulta = (Button) findViewById(R.id.bConsulta);
        mensaje = (TextView) findViewById(R.id.mensaje);

        //Text Changed Listener for autoprompting "-" in between letters and numbers
        placa.addTextChangedListener(new TextWatcher() {
            int prevLength=0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevLength = fecha.getText().length(); //This variable is for enabling backspace
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int placaLength = placa.length();
                if (prevLength < placaLength)
                {
                    if (placaLength<3)
                    {
                        placa.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); //text characters for the 3 first positions
                    }
                    else if(placaLength==3){
                        placa.append("-");      //autoprompt "-" in the 3rd position
                    }
                    else if (placaLength>3 && placaLength<6){
                        placa.setInputType(InputType.TYPE_CLASS_NUMBER);//numeric characters for the last positions
                    }
                }




            }
        });

        fecha.addTextChangedListener(new TextWatcher() {
            int prevLength = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevLength = fecha.length(); //Variable for enabling backspace
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int fechaLength = fecha.length();
                String valorFecha = fecha.getText().toString(); //variable for extracting the day, month and year variable

                int[] meses = {3,0,3,2,3,2,3,3,2,3,2,3}; //values over 28 days of each month of the year
                if (prevLength < fechaLength)
                {
                    if(fechaLength== 2 || fechaLength== 5 ){
                        fecha.append("/"); //autopromp of "/" character in the 2nd and 5th postition
                    }
                }
                if (fechaLength >= 10){
                    //integer values for day, month and year values
                    int diaCons = Integer.parseInt(valorFecha.substring(0,valorFecha.indexOf("/")));
                    int mesCons = Integer.parseInt(valorFecha.substring(valorFecha.indexOf("/")+1, valorFecha.lastIndexOf("/")));
                    int yearCons= Integer.parseInt(valorFecha.substring(valorFecha.lastIndexOf("/")+1 ,valorFecha.length()));

                    //dates starting from the current year
                    if (yearCons < 2017){
                        fecha.setText("");
                        Toast.makeText(MainActivity.this, R.string.year_warning, Toast.LENGTH_SHORT).show();

                    }
                    //excluding negative values and values over 12 for month variable
                    if (mesCons < 0 || mesCons > 12){
                        fecha.setText("");
                        Toast.makeText(MainActivity.this, R.string.month_warning, Toast.LENGTH_SHORT).show();

                    }
                    else {
                        //looking for valid values for day variable
                        if (diaCons > 28){
                            if ((diaCons-28)>(meses[mesCons-1])){
                                fecha.setText("");
                                Toast.makeText(MainActivity.this, R.string.day_warning, Toast.LENGTH_SHORT).show();
                            }
                        }
                        //excluding negative values
                        else if (diaCons < 0){
                            Toast.makeText(MainActivity.this, R.string.day_warning, Toast.LENGTH_SHORT).show();
                            fecha.setText("");

                        }
                    }

                }
            }
        });

        hora.addTextChangedListener(new TextWatcher() {
            int prevLength = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevLength = hora.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int horaLength = hora.length();
                String valorHora = hora.getText().toString();

                if (prevLength<horaLength){
                    if (horaLength == 2)
                    {
                        hora.append(":"); //autoprompt of ":" character in the 2nd position
                    }
                }
                if (horaLength>= 5){
                    int hour = Integer.parseInt(valorHora.substring(0,valorHora.indexOf(":")));
                    int min = Integer.parseInt(valorHora.substring(valorHora.indexOf(":")+1,valorHora.length()));
                    //Excluding invalid values for hour variable
                    if (hour > 23 || hour < 0){
                        hora.setText("");
                        Toast.makeText(MainActivity.this, R.string.hour_warning, Toast.LENGTH_SHORT).show();

                    }
                    //Excluding invalid values for minute variable
                    if (min > 59 || min <0){
                        Toast.makeText(MainActivity.this, R.string.minute_warning, Toast.LENGTH_SHORT).show();
                        hora.setText("");                    }

                }


            }
        });

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (placa.length() >= 7 && fecha.length()>=10 && hora.length()>= 5 ){


                    int picoPlaca = Integer.parseInt((placa.getText().toString()).substring(placa.length()-1));

                    String valorFecha = fecha.getText().toString();
                    int diaCons = Integer.parseInt(valorFecha.substring(0,valorFecha.indexOf("/")));
                    int mesCons = Integer.parseInt(valorFecha.substring(valorFecha.indexOf("/")+1, valorFecha.lastIndexOf("/")));
                    int yearCons= Integer.parseInt(valorFecha.substring(valorFecha.lastIndexOf("/")+1 ,valorFecha.length()));

                    String valorHora = hora.getText().toString();
                    int horaCons = Integer.parseInt(valorHora.substring(0,valorHora.indexOf(":")));
                    int minCons = Integer.parseInt(valorHora.substring(valorHora.lastIndexOf(":")+1,valorHora.length()));


                    meses = new int[]{3, 0, 3, 2, 3, 2, 3, 3, 2, 3, 2, 3}; // values over 28 for each month
                    semana = new String[]{"DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB"}; //day values
                    //values of days for indexing with the last digit of car number
                    diaPicoPlaca = new String[]{"VIE", "LUN", "LUN", "MAR", "MAR", "MIE", "MIE", "JUE", "JUE", "VIE"};

                    //Variables for doing the math
                    int mesRef = 1;                int yearRef = 2017;                int diaRef = 1; //taking SUNDAY,1st Jan 2017 for reference
                    int difMes = mesCons - mesRef; int difYear= yearCons - yearRef;   int difDia = diaCons - diaRef;

                   int diaPasados = difDia + (difMes*28) + (difYear*365); //Days passed from the reference date

                    if (difYear > 0) {

                        if ((difYear%4) == 1 && mesCons > 2){
                                diaPasados += difYear/4 + 1; //adding a day in a leap-year
                        }
                    }


                    if (difMes > 0){
                        for (int i=0 ;i<difMes; i++){
                            diaPasados += meses[i];     //adding the values over 28 of each month
                        }
                    }


                    String diaSemana = semana[(diaPasados%7)];  //indexing the date with the day of the week


                    if (diaSemana == "SAB" || diaSemana == "DOM"){  //comparing day with weekends
                        mensaje.setText(R.string.noProblem);
                        mensaje.setBackgroundColor(verde);
                    }
                    else if (diaSemana == diaPicoPlaca[picoPlaca]){ //comparing if the date have restriction
                        if ((horaCons >=7 && horaCons <9) || (horaCons >= 16 && horaCons<19) ){ //if time is within the restriction hours
                            mensaje.setText(R.string.pico);
                            mensaje.setBackgroundColor(rojo);
                        }
                        else if(horaCons == 9||horaCons ==19){ //if time is in the restriction half of the 9th and 19th hours
                            if(minCons<=30){
                                mensaje.setText(R.string.pico);
                                mensaje.setBackgroundColor(rojo);
                            }
                            else{
                                mensaje.setText(R.string.picoNotHour);
                                mensaje.setBackgroundColor(amarillo);
                            }
                        }
                        else {
                            mensaje.setText(R.string.picoNotHour);
                            mensaje.setBackgroundColor(amarillo);
                        }
                    }
                    else { //when the date have no restrictions
                        mensaje.setText(R.string.noProblem);
                        mensaje.setBackgroundColor(verde);

                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "ERROR: Campos no completados", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
