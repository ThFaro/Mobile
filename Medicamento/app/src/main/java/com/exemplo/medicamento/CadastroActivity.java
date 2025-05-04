package com.exemplo.medicamento;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome, edtDescricao, edtHorario;
    Button btnSalvar;
    MedicamentoDBHelper dbHelper;
    int idMedicamento = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtHorario = findViewById(R.id.edtHorario);
        btnSalvar = findViewById(R.id.btnSalvar);
        dbHelper = new MedicamentoDBHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            idMedicamento = intent.getIntExtra("id", -1);
            edtNome.setText(intent.getStringExtra("nome"));
            edtDescricao.setText(intent.getStringExtra("descricao"));
            edtHorario.setText(intent.getStringExtra("horario"));
        }

        btnSalvar.setOnClickListener(v -> salvarMedicamento());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!am.canScheduleExactAlarms()) {
                Intent alarmIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(alarmIntent);
            }
        }
    }

    private void salvarMedicamento() {
        String nome = edtNome.getText().toString();
        String descricao = edtDescricao.getText().toString();
        String horario = edtHorario.getText().toString();

        if (nome.isEmpty() || horario.isEmpty()) {
            Toast.makeText(this, "Preencha nome e horário", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("descricao", descricao);
        valores.put("horario", horario);
        valores.put("tomado", 0);

        if (idMedicamento == -1) {
            long id = db.insert("medicamentos", null, valores);
            if (id != -1) {
                agendarNotificacao(nome, horario);
                Toast.makeText(this, "Medicamento salvo", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }
        } else {
            int linhas = db.update("medicamentos", valores, "id=?", new String[]{String.valueOf(idMedicamento)});
            if (linhas > 0) {
                agendarNotificacao(nome, horario);
                Toast.makeText(this, "Medicamento atualizado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void agendarNotificacao(String nome, String horario) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Calendar cal = Calendar.getInstance();
            Calendar agora = Calendar.getInstance();

            cal.setTime(Objects.requireNonNull(sdf.parse(horario)));
            cal.set(Calendar.YEAR, agora.get(Calendar.YEAR));
            cal.set(Calendar.MONTH, agora.get(Calendar.MONTH));
            cal.set(Calendar.DAY_OF_MONTH, agora.get(Calendar.DAY_OF_MONTH));

            if (cal.before(agora)) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }

            Intent intent = new Intent(this, AlarmeReceiver.class);
            intent.putExtra("nome", nome);

            PendingIntent pi = PendingIntent.getBroadcast(
                    this,
                    (int) System.currentTimeMillis(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(cal.getTimeInMillis(), pi);
            am.setAlarmClock(info, pi);

            Toast.makeText(this, "Notificação agendada para: " + sdf.format(cal.getTime()), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Erro ao agendar notificação", Toast.LENGTH_SHORT).show();
        }
    }
}
