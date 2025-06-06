# Resumo do Aplicativo - Controle de Medicamentos

Este aplicativo Android permite cadastrar medicamentos, registrar horários de consumo e receber notificações automáticas.  
Foi desenvolvido com foco na simplicidade, utilizando **SQLite** para armazenar os dados e **AlarmManager** para agendar as notificações.

---

## O que cada classe faz

- **MainActivity.java**: Tela principal. Lista os medicamentos, permite marcar como 'tomado', editar (clique duplo) e excluir (clique longo).
- **CadastroActivity.java**: Tela de cadastro/edição. Permite inserir nome, descrição e horário. Agenda a notificação.
- **AlarmeReceiver.java**: Classe que é chamada na hora agendada. Mostra uma notificação e um Toast.
- **Medicamento.java**: Classe modelo que representa um medicamento.
- **MedicamentoDBHelper.java**: Classe que cria e gerencia o banco SQLite.
- **MedicamentoAdapter.java**: Adapter que exibe os medicamentos na lista com nome e horário, riscando os tomados.

---

## Recursos e Tecnologias Usadas

- **SQLite** (*MedicamentoDBHelper.java*): banco de dados local para armazenar os medicamentos.
- **AlarmManager** (*CadastroActivity.java*): agenda o horário do alarme.
- **NotificationManager** (*AlarmeReceiver.java*): exibe a notificação ao usuário.
- **BroadcastReceiver** (*AlarmeReceiver.java*): executa a ação quando o alarme dispara.
- **ListView + Adapter** (*MainActivity.java + MedicamentoAdapter.java*): exibe os medicamentos na tela.
- **Toast** (*CadastroActivity.java, AlarmeReceiver.java*): mensagens curtas de confirmação ao usuário.
- **Permissões** (*CadastroActivity.java*): solicita as permissões de notificação e alarme exato no Android 12+.
