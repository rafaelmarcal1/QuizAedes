package rafaelmarcal.ifsp.edu.quizaedes.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import rafaelmarcal.ifsp.edu.quizaedes.data.repository.UserRepository;

/*Esta classe UserViewModel estende a classe ViewModel, usada no padrão
    MVVM (Model-View-ViewModel). A ViewModel serve como intermediária entre a
     View (interface de usuário) e o Model (dados, neste caso, gerenciados pelo
      UserRepository). Ela é responsável por gerenciar dados e lógica de negócios
      de forma a sobreviver a mudanças de configuração, como rotações de tela.*/
public class UserViewModel extends ViewModel {


    /*Define um atributo privado do tipo UserRepository. O UserRepository é responsável
     por lidar com operações relacionadas ao banco de dados (neste caso, o Firebase), como
     adicionar e recuperar dados de usuários.*/
    private UserRepository userRepository;


    /*Construtor da classe UserViewModel. Quando uma instância da UserViewModel é criada,
    o repositório (UserRepository) também é instanciado para que possa ser utilizado em
    operações relacionadas ao Firebase.*/
    public UserViewModel(){

        /*Cria uma nova instância da classe UserRepository, estabelecendo a conexão com a
         camada de dados que será usada para realizar operações com o banco.*/
        userRepository = new UserRepository();
    }

    public void addUser(String name, String email, String password) {
        userRepository.addUser(name, email, password, new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.v("FB", "Documento adicionado ID: " + task.getResult().getId());
                } else {
                    Log.v("FB", "Erro ao inserir novo documento: ", task.getException());
                }
            }
        });
    }
}
