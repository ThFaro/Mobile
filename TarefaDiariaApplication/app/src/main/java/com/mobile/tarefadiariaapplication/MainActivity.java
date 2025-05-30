package com.mobile.tarefadiariaapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mobile.tarefadiariaapplication.adapters.TarefaAdapter;
import com.mobile.tarefadiariaapplication.models.Frase;
import com.mobile.tarefadiariaapplication.models.Tarefa;
import com.mobile.tarefadiariaapplication.services.FraseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser user;

    private EditText edtTarefa;
    private Button btnSalvar, btnLogout, btnFrase;
    private TextView txtFraseMotivacional;
    private RecyclerView recyclerTarefas;

    private List<Tarefa> listaTarefas = new ArrayList<>();
    private TarefaAdapter adapter;
    private Tarefa tarefaEditando = null;

    private Handler handler = new Handler();
    private Runnable tarefaNotificacao;
    private static final int INTERVALO = 10000;
    private static final String CHANNEL_ID = "tarefas_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        db = FirebaseFirestore.getInstance();

        edtTarefa = findViewById(R.id.edtTarefa);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnLogout = findViewById(R.id.btnLogout);
        btnFrase = findViewById(R.id.btnFrase);
        txtFraseMotivacional = findViewById(R.id.txtFraseMotivacional);
        recyclerTarefas = findViewById(R.id.recyclerTarefas);

        recyclerTarefas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TarefaAdapter(listaTarefas);
        recyclerTarefas.setAdapter(adapter);

        btnSalvar.setOnClickListener(v -> salvarTarefa());

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnFrase.setOnClickListener(v -> buscarFraseMotivacional());

        adapter.setOnItemClickListener(tarefa -> {
            edtTarefa.setText(tarefa.getTitulo());
            tarefaEditando = tarefa;
            btnSalvar.setText("Atualizar");
        });

        adapter.setOnItemLongClickListener(tarefa -> {
            db.collection("usuarios")
                    .document(user.getUid())
                    .collection("tarefas")
                    .document(tarefa.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Tarefa removida", Toast.LENGTH_SHORT).show();
                        carregarTarefas();
                    });
        });

        adapter.setOnCheckboxClickListener(tarefa -> {
            DocumentReference ref = db.collection("usuarios")
                    .document(user.getUid())
                    .collection("tarefas")
                    .document(tarefa.getId());

            ref.update("concluida", tarefa.isConcluida());
        });

        carregarTarefas();
        criarCanalDeNotificacao();
        iniciarNotificacoesPeriodicas();
    }

    private void salvarTarefa() {
        String titulo = edtTarefa.getText().toString().trim();

        if (titulo.isEmpty()) {
            Toast.makeText(this, "Digite o título da tarefa", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tarefaEditando == null) {
            Tarefa nova = new Tarefa(null, titulo, false);
            db.collection("usuarios")
                    .document(user.getUid())
                    .collection("tarefas")
                    .add(nova)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Tarefa adicionada", Toast.LENGTH_SHORT).show();
                        edtTarefa.setText("");
                        carregarTarefas();
                    });
        } else {
            tarefaEditando.setTitulo(titulo);
            db.collection("usuarios")
                    .document(user.getUid())
                    .collection("tarefas")
                    .document(tarefaEditando.getId())
                    .set(tarefaEditando)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Tarefa atualizada", Toast.LENGTH_SHORT).show();
                        edtTarefa.setText("");
                        tarefaEditando = null;
                        btnSalvar.setText("Salvar");
                        carregarTarefas();
                    });
        }
    }

    private void carregarTarefas() {
        db.collection("usuarios")
                .document(user.getUid())
                .collection("tarefas")
                .get()
                .addOnSuccessListener(query -> {
                    listaTarefas.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        Tarefa t = doc.toObject(Tarefa.class);
                        t.setId(doc.getId());
                        listaTarefas.add(t);
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void buscarFraseMotivacional() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zenquotes.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FraseService service = retrofit.create(FraseService.class);

        service.getFrases().enqueue(new Callback<List<Frase>>() {
            @Override
            public void onResponse(Call<List<Frase>> call, Response<List<Frase>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Frase frase = response.body().get(0);
                    txtFraseMotivacional.setText("\"" + frase.getQ() + "\"\n— " + frase.getA());
                } else {
                    txtFraseMotivacional.setText("Erro ao obter frase.");
                }
            }

            @Override
            public void onFailure(Call<List<Frase>> call, Throwable t) {
                txtFraseMotivacional.setText("Erro de conexão.");
                Log.e("FraseAPI", "Erro: ", t);
            }
        });
    }

    private void criarCanalDeNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lembrete de Tarefas";
            String description = "Canal para notificações de tarefas pendentes";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void iniciarNotificacoesPeriodicas() {
        tarefaNotificacao = new Runnable() {
            @Override
            public void run() {
                db.collection("usuarios")
                        .document(user.getUid())
                        .collection("tarefas")
                        .get()
                        .addOnSuccessListener(query -> {
                            int pendentes = 0;
                            for (QueryDocumentSnapshot doc : query) {
                                Boolean concluida = doc.getBoolean("concluida");
                                if (concluida == null || !concluida) {
                                    pendentes++;
                                }
                            }

                            if (pendentes > 0) {
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pendingIntent = PendingIntent.getActivity(
                                        MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE
                                );

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                                        .setContentTitle("Tarefas Pendentes")
                                        .setContentText("Você tem " + pendentes + " tarefa(s) para hoje.")
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                                        .setCategory(NotificationCompat.CATEGORY_REMINDER)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);

                                NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                                        checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                                    manager.notify(1, builder.build());
                                }
                            }
                        });

                handler.postDelayed(this, INTERVALO);
            }
        };

        handler.post(tarefaNotificacao);
    }
}
